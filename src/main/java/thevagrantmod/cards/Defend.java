package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.util.CardStats;

public class Defend extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("Defend");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.SKILL,
        CardRarity.BASIC,
        CardTarget.SELF,
        1
    );

    public Defend() {
        super(ID, INFO);
        setBlock(5, 3);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Defend();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
    }
}

