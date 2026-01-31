package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.powers.MasterTrapperPower;
import thevagrantmod.util.CardStats;

public class MasterTrapper extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("MasterTrapper");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.POWER,
        CardRarity.RARE,
        CardTarget.SELF,
        2
    );

    public MasterTrapper() {
        super(ID, INFO);
        setMagic(2, 1);
    }

    @Override
    public AbstractCard makeCopy() {
        return new MasterTrapper();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInDrawPileAction(new TripCharge(), magicNumber, true, true));
        addToBot(new ApplyPowerAction(p, p, new MasterTrapperPower(p, 1)));
    }
}

