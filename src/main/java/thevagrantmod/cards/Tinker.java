package thevagrantmod.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.UpgradeRandomCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.util.CardStats;

public class Tinker extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("Tinker");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.SKILL,
        CardRarity.BASIC,
        CardTarget.SELF,
        0
    );

    public Tinker() {
        super(ID, INFO);
        setBlock(3);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Tinker();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        if (upgraded) {
            // TODO: Localise, can steal from Armaments
            addToBot(new SelectCardsInHandAction(1, "Upgrade", x -> x.canUpgrade(), cards -> {
                for (AbstractCard card : cards) {
                    if (card.canUpgrade()) {
                        card.upgrade();
                    }
                }
            }));
        } else {
            addToBot(new UpgradeRandomCardAction());
        }
    }
}

