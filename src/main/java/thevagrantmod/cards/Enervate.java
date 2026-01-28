package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.util.CardStats;

public class Enervate extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("Enervate");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.SKILL,
        CardRarity.UNCOMMON,
        CardTarget.ENEMY,
        1
    );

    private static final int WEAK_AMOUNT = 99;

    public Enervate() {
        super(ID, INFO);
        setCostUpgrade(0);
        setMagic(WEAK_AMOUNT);
        setExhaust(true);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Enervate();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new WeakPower(m, magicNumber, false)));
    }
}

