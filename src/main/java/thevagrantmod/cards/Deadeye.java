package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.powers.DeadeyePower;
import thevagrantmod.util.CardStats;

public class Deadeye extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("Deadeye");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.SKILL,
        CardRarity.RARE,
        CardTarget.SELF,
        1
    );

    public Deadeye() {
        super(ID, INFO);
        setCostUpgrade(0);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Deadeye();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DeadeyePower(p)));
    }
}

