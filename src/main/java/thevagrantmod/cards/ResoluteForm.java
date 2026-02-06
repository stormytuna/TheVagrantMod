package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.powers.ResoluteFormPower;
import thevagrantmod.util.CardStats;

public class ResoluteForm extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("ResoluteForm");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.POWER,
        CardRarity.RARE,
        CardTarget.SELF,
        3
    );

    public ResoluteForm() {
        super(ID, INFO);
        setMagic(2);
        setEthereal(true, false);
    }

    @Override
    public AbstractCard makeCopy() {
        return new ResoluteForm();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ResoluteFormPower(p, magicNumber)));
    }
}

