package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.actions.UnjamAllCardsInHandAction;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.util.CardStats;

public class FieldRepairs extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("FieldRepairs");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.SKILL,
        CardRarity.UNCOMMON,
        CardTarget.SELF,
        2
    );

    public FieldRepairs() {
        super(ID, INFO);
        setBlock(8, 12);
    }

    @Override
    public AbstractCard makeCopy() {
        return new FieldRepairs();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new UnjamAllCardsInHandAction());
    }
}

