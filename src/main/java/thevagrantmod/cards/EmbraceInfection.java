package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.powers.BlightPower;
import thevagrantmod.util.CardStats;

public class EmbraceInfection extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("EmbraceInfection");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.SKILL,
        CardRarity.UNCOMMON,
        CardTarget.ENEMY,
        0
    );

    public EmbraceInfection() {
        super(ID, INFO);
        setMagic(6, 2);
        setInnate(true);
        setExhaust(true);
    }

    @Override
    public AbstractCard makeCopy() {
        return new EmbraceInfection();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.hasPower(BlightPower.ID)) {
            return;
        }

        addToBot(new ApplyPowerAction(m, p, new BlightPower(m, magicNumber)));
    }
}

