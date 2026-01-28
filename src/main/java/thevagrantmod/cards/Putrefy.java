package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.powers.BlightPower;
import thevagrantmod.util.CardStats;

public class Putrefy extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("Putrefy");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.SKILL,
        CardRarity.RARE,
        CardTarget.ENEMY,
        4
    );

    private static final int BLIGHT_AMOUNT = 20;

    public Putrefy() {
        super(ID, INFO);
        setCostUpgrade(3);
        setMagic(BLIGHT_AMOUNT);
        setExhaust(true);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Putrefy();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new BlightPower(m, magicNumber)));
    }
}

