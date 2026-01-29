package thevagrantmod.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.util.CardStats;

public class Blueprint extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("Blueprint");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.SKILL,
        CardRarity.UNCOMMON,
        CardTarget.SELF,
        1
    );

    public Blueprint() {
        super(ID, INFO);
        setMagic(2, 1);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Blueprint();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Doing draw pile shuffling for the DrawCardAction so our postfix further down works properly
        if (magicNumber > p.drawPile.size()) {
            addToBot(new EmptyDeckShuffleAction());
        }

        addToBot(new DrawCardAction(magicNumber));
        UpgradeBlueprintDraws.upgrade = true;
    }

    @SpirePatch2(clz = AbstractPlayer.class, method = "draw", paramtypez = {int.class})
    public static class UpgradeBlueprintDraws {
        public static boolean upgrade = false;

        @SpireInsertPatch(rloc = 12, localvars = "c")
        public static void patch(AbstractCard c) {
            if (upgrade && c.canUpgrade()) {
                c.upgrade();
                c.superFlash();
                c.applyPowers();
            }
        }
    }

    // Setting to false once entire action is finished as we can draw multiple and we want to upgrade all, not just the first
    @SpirePatch2(clz = DrawCardAction.class, method = "update")
    public static class ResetAmbrosiaUpgradeBool {
        @SpirePostfixPatch
        public static void patch(DrawCardAction __instance) {
            if (__instance.isDone) {
                UpgradeBlueprintDraws.upgrade = false;
            }
        }
    }
}

