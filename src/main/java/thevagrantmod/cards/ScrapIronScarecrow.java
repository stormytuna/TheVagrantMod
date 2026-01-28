package thevagrantmod.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.util.CardStats;

public class ScrapIronScarecrow extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("ScrapIronScarecrow");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.SKILL,
        CardRarity.COMMON,
        CardTarget.SELF,
        1
    );

    public ScrapIronScarecrow() {
        super(ID, INFO);
        setBlock(7, 3);
    }

    @Override
    public AbstractCard makeCopy() {
        return new ScrapIronScarecrow();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
    }

    @SpirePatch2(clz = AbstractPlayer.class, method = "applyStartOfTurnRelics")
    public static class AddBackScrapIronScarecrow {
        @SpirePostfixPatch
        public static void patch(AbstractPlayer __instance) {
            for (AbstractCard c : __instance.discardPile.group) {
                if (c instanceof ScrapIronScarecrow) {
                    AbstractDungeon.actionManager.addToBottom(new DiscardToHandAction(c));
                }
            }
        }
    }
}

