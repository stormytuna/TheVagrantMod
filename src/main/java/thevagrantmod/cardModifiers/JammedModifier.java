package thevagrantmod.cardModifiers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
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

    @SpirePatch2(clz = AbstractCard.class, method = "renderPortrait")
    @SpirePatch2(clz = AbstractCard.class, method = "renderJokePortrait")
    public static class JammedGrayscale {
        @SpirePrefixPatch
        public static void applyShader(AbstractCard __instance, SpriteBatch sb) {
            if (CardModifierManager.hasModifier(__instance, ID)) {
                ShaderHelper.setShader(sb, ShaderHelper.Shader.GRAYSCALE);
            }
        }

        @SpirePostfixPatch
        public static void unapplyShader(AbstractCard __instance, SpriteBatch sb) {
            if (CardModifierManager.hasModifier(__instance, ID)) {
                ShaderHelper.setShader(sb, ShaderHelper.Shader.DEFAULT);
            }
        }
    }
}
