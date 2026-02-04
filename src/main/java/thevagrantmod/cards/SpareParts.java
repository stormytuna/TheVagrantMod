package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.powers.SparePartsPower;
import thevagrantmod.util.CardStats;

public class SpareParts extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("SpareParts");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.POWER,
        CardRarity.UNCOMMON,
        CardTarget.SELF,
        1
    );

    public SpareParts() {
        super(ID, INFO);
        setCostUpgrade(0);
        setMagic(1);
    }

    @Override
    public AbstractCard makeCopy() {
        return new SpareParts();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new SparePartsPower(p, magicNumber)));
    }
}

