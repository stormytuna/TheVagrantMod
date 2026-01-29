package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.powers.BlightPower;
import thevagrantmod.powers.CreepingInfectionPower;
import thevagrantmod.util.CardStats;

public class CreepingInfection extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("CreepingInfection");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.SKILL,
        CardRarity.RARE,
        CardTarget.ENEMY,
        1
    );

    public CreepingInfection() {
        super(ID, INFO);
        setMagic(4, 1);
        setExhaust(true);
    }

    @Override
    public AbstractCard makeCopy() {
        return new CreepingInfection();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new BlightPower(m, magicNumber)));
        addToBot(new ApplyPowerAction(m, p, new CreepingInfectionPower(m, magicNumber)));
    }
}

