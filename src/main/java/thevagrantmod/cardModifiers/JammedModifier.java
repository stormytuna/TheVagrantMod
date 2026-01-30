package thevagrantmod.cardModifiers;

import java.time.Duration;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.unique.UndoAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ShaderHelper;
import com.megacrit.cardcrawl.localization.UIStrings;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import thevagrantmod.TheVagrantMod;
import thevagrantmod.actions.UnjamSpecificCardAction;
import thevagrantmod.effects.JamCardEffect;
import thevagrantmod.shaders.BetterGrayscaleShader;

public class JammedModifier extends AbstractCardModifier {
    public static final String ID = TheVagrantMod.makeID("JammedModifier");

    private static final UIStrings STRINGS = CardCrawlGame.languagePack.getUIString(ID);

    public static boolean canJam(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID) || card.type == CardType.STATUS || card.type == CardType.CURSE || card.cost == -2) {
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
                    if (m.getClassName().equals(AbstractCard.class.getName()) && m.getMethodName().equals("use")) {
                        m.replace(
                                "if (" + JammedCardDoNoEffect.class.getName() + ".isJammed(c)) {" +
                                "  " + JammedCardDoNoEffect.class.getName() + ".unjam(c);" +
                                "} else {" +
                                "  $proceed($$);" +
                                "}"
                            ); 
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

    @SpirePatch2(clz = UseCardAction.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class, AbstractCreature.class})
    public static class JammedCardNoExhaust {
        @SpirePostfixPatch
        public static void patch(AbstractCard ___targetCard, @ByRef boolean[] ___exhaustCard) {
            if (CardModifierManager.hasModifier(___targetCard, ID)) {
                ___exhaustCard[0] = false;
            }
        }
    }

    @SpirePatch2(clz = UseCardAction.class, method = "update")
    public static class JammedPowerNoRemove {
        private static boolean undoUnpowering = false;

        @SpirePrefixPatch
        public static void patch1(AbstractCard ___targetCard, float ___duration) {
            if (CardModifierManager.hasModifier(___targetCard, ID) && ___duration == 0.15f && ___targetCard.type == CardType.POWER) {
                ___targetCard.type = CardType.SKILL;
                undoUnpowering =  true;
            }
        }

        @SpirePostfixPatch
        public static void patch2(AbstractCard ___targetCard) {
            if (undoUnpowering) {
                ___targetCard.type = CardType.POWER;
                undoUnpowering = false;
            }
        }
    }

    @SpirePatch2(clz = AbstractCard.class, method = "renderPortrait")
    @SpirePatch2(clz = AbstractCard.class, method = "renderJokePortrait")
    public static class JammedGrayscale {
        private static boolean undoGrayShader = false;

        @SpirePrefixPatch
        public static void applyShader(AbstractCard __instance, SpriteBatch sb) {
            if (JamCardEffect.Fields.grayscaleStrength.get(__instance) > 0) {
                BetterGrayscaleShader.apply(sb, JamCardEffect.Fields.grayscaleStrength.get(__instance));
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
}
