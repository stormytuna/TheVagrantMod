package thevagrantmod.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.util.CardStats;

public class Replicate extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("Replicate");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.SKILL,
        CardRarity.UNCOMMON,
        CardTarget.SELF,
        1
    );

    public Replicate() {
        super(ID, INFO);
        setCostUpgrade(0);
        setExhaust(true);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Replicate();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(1, "TODO: Localisation!", card -> {
            if (card.size() < 1) {
                return;
            }

            addToBot(new MakeTempCardInHandAction(card.get(0).makeStatEquivalentCopy()));
        }));
    }
}

