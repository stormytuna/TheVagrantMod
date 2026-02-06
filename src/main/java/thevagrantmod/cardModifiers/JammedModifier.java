package thevagrantmod.cardModifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ShaderHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import thevagrantmod.TheVagrantMod;
import thevagrantmod.actions.UnjamSpecificCardAction;
import thevagrantmod.patches.CustomCardFields;
import thevagrantmod.shaders.BetterGrayscaleShader;

public class JammedModifier extends AbstractCardModifier {
  public static final String ID = TheVagrantMod.makeID("JammedModifier");

  private static final UIStrings STRINGS = CardCrawlGame.languagePack.getUIString(ID);

  public static boolean canJam(AbstractCard card) {
    if (CardModifierManager.hasModifier(card, ID)
        || card.type == CardType.STATUS
        || card.type == CardType.CURSE
        || card.cost == -2) {
      return false;
    }

    return true;
  }

  @Override
  public String identifier(AbstractCard card) {
    return ID;
  }

  @Override
  public boolean removeOnCardPlayed(AbstractCard card) {
    return true;
  }

  @Override
  public AbstractCardModifier makeCopy() {
    return new JammedModifier();
  }

  @Override
  public String modifyDescription(String rawDescription, AbstractCard card) {
    return STRINGS.TEXT[0] + rawDescription;
  }

  @SpirePatch2(clz = AbstractPlayer.class, method = "useCard")
  public static class JammedCardDoNoEffect {
    @SpireInstrumentPatch
    public static ExprEditor patch() {
      return new ExprEditor() {
        @Override
        public void edit(MethodCall m) throws CannotCompileException {
          if (m.getClassName().equals(AbstractCard.class.getName())
              && m.getMethodName().equals("use")) {
            m.replace(
                "if ("
                    + JammedCardDoNoEffect.class.getName()
                    + ".isJammed(c)) {"
                    + "  "
                    + JammedCardDoNoEffect.class.getName()
                    + ".unjam(c);"
                    + "} else {"
                    + "  $proceed($$);"
                    + "}");
          }
        }
      };
    }

    public static boolean isJammed(AbstractCard c) {
      return CardModifierManager.hasModifier(c, ID);
    }

    public static void unjam(AbstractCard c) {
      AbstractDungeon.actionManager.addToBottom(new UnjamSpecificCardAction(c));
    }
  }

  @SpirePatch2(
      clz = UseCardAction.class,
      method = SpirePatch.CONSTRUCTOR,
      paramtypez = {AbstractCard.class, AbstractCreature.class})
  public static class JammedCardNoExhaust {
    @SpirePostfixPatch
    public static void patch(AbstractCard ___targetCard, @ByRef boolean[] ___exhaustCard) {
      if (CardModifierManager.hasModifier(___targetCard, ID)) {
        ___exhaustCard[0] = false;
      }
    }
  }

  // Making it a skill in ctor as UseCardAction is in the action queue *after* our
  // UnjamSpecificCardAction,
  //   so would require weird hacks to remember it was jammed
  @SpirePatch2(
      clz = UseCardAction.class,
      method = SpirePatch.CONSTRUCTOR,
      paramtypez = {AbstractCard.class, AbstractCreature.class})
  public static class JammedPowerToSkillSoItDoesntGetEaten {
    @SpirePostfixPatch
    public static void patch(AbstractCard ___targetCard) {
      if (CardModifierManager.hasModifier(___targetCard, ID)
          && ___targetCard.type == CardType.POWER) {
        ___targetCard.type = CardType.SKILL;
        JammedPowerBePowerAgain.undoUnpowering = true;
      }
    }
  }

  @SpirePatch2(clz = UseCardAction.class, method = "update")
  public static class JammedPowerBePowerAgain {
    private static boolean undoUnpowering = false;

    @SpirePostfixPatch
    public static void patch2(AbstractCard ___targetCard, boolean ___isDone) {
      if (undoUnpowering) {
        ___targetCard.type = CardType.POWER;
        undoUnpowering = false;
      }
    }
  }

  @SpirePatch2(clz = AbstractCard.class, method = "renderPortrait")
  @SpirePatch2(clz = AbstractCard.class, method = "renderJokePortrait")
  public static class JammedGrayscalePortrait {
    private static boolean undoGrayShader = false;

    @SpirePrefixPatch
    public static void applyShader(AbstractCard __instance, SpriteBatch sb) {
      if (CustomCardFields.Fields.grayscaleStrength.get(__instance) > 0) {
        BetterGrayscaleShader.apply(sb, CustomCardFields.Fields.grayscaleStrength.get(__instance));
        undoGrayShader = true;
      }
    }

    @SpirePostfixPatch
    public static void unapplyShader(AbstractCard __instance, SpriteBatch sb) {
      if (undoGrayShader) {
        ShaderHelper.setShader(sb, ShaderHelper.Shader.DEFAULT);
        undoGrayShader = false;
      }
    }
  }

  @SpirePatch2(clz = AbstractCard.class, method = "renderCardBg")
  @SpirePatch2(clz = AbstractCard.class, method = "renderPortraitFrame")
  @SpirePatch2(clz = AbstractCard.class, method = "renderBannerImage")
  public static class JammedGrayscaleCardBits {
    private static boolean undoGrayShader = false;

    @SpirePrefixPatch
    public static void applyShader(AbstractCard __instance, SpriteBatch sb) {
      if (CustomCardFields.Fields.grayscaleStrength.get(__instance) > 0) {
        BetterGrayscaleShader.apply(sb, CustomCardFields.Fields.grayscaleStrength.get(__instance) * 0.5f);
        undoGrayShader = true;
      }
    }

    @SpirePostfixPatch
    public static void unapplyShader(AbstractCard __instance, SpriteBatch sb) {
      if (undoGrayShader) {
        ShaderHelper.setShader(sb, ShaderHelper.Shader.DEFAULT);
        undoGrayShader = false;
      }
    }
  }

  public static class JammedStrikethroughLocator extends SpireInsertLocator {
    @Override
    public int[] Locate(CtBehavior ctBehavior) throws Exception {
      Matcher matcher = new Matcher.FieldAccessMatcher(GlyphLayout.class, "width");
      int[] lines = LineFinder.findAllInOrder(ctBehavior, matcher);
      return new int[]{lines[1], lines[lines.length - 1]}; // Only 2nd and last occurrence
    }
  }

  @SpirePatch2(clz = AbstractCard.class, method = "renderDescription")
  public static class JammedDescriptionDarkeningSetup {
    public static class Locator extends SpireInsertLocator {
      @Override
      public int[] Locate(CtBehavior ctBehavior) throws Exception {
        Matcher matcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "description");
        int[] lines = LineFinder.findAllInOrder(ctBehavior, matcher);
        return new int[] {lines[2]}; // Third instance
      }
    }

    @SpireInsertPatch(
      locator = Locator.class,
      localvars = {"i"}
    )
    public static void patch(AbstractCard __instance, SpriteBatch sb, int i) {
      if (CardModifierManager.hasModifier(__instance, ID) && i == 1) {
        JammedActuallyDoDescriptionDarknening.changeColor = true;
      }
    }

    @SpirePostfixPatch
    public static void patch(SpriteBatch sb) {
        JammedActuallyDoDescriptionDarknening.changeColor = false;
    }
  }

  @SpirePatch2(clz = FontHelper.class, method = "renderRotatedText")
  public static class JammedActuallyDoDescriptionDarknening {
    public static boolean changeColor = false;

    private static Color oldColor = null;

    public static class Locator extends SpireInsertLocator {
      @Override
      public int[] Locate(CtBehavior ctBehavior) throws Exception {
        Matcher matcher = new Matcher.MethodCallMatcher(SpriteBatch.class, "begin");
        int[] lines = LineFinder.findAllInOrder(ctBehavior, matcher);
        return lines;
      }
    }

    @SpireInsertPatch(
      locator = Locator.class
    )
    public static void patch(SpriteBatch sb, Color c) {
      if (!changeColor) {
        return;
      }

      if (oldColor == null) {
        oldColor = new Color(c);

        c.r *= 0.7f;
        c.g *= 0.7f;
        c.b *= 0.7f;
        c.a = 0.6f;

        return;
      }

      c.r = oldColor.r;
      c.g = oldColor.g;
      c.b = oldColor.b;
      c.a = oldColor.a;

      oldColor = null;
    }
  }
}
