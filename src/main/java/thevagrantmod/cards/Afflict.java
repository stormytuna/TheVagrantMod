package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.powers.BlightPower;
import thevagrantmod.util.CardStats;

public class Afflict extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("Afflict");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.SKILL,
        CardRarity.COMMON,
        CardTarget.ENEMY,
        1
    );

    public Afflict() {
        super(ID, INFO);
        setMagic(5, 1);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Afflict();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new BlightPower(m, magicNumber)));
    }
}

