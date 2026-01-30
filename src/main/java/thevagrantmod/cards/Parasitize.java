package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.powers.ParasitizePower;
import thevagrantmod.util.CardStats;

public class Parasitize extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("Parasitize");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.POWER,
        CardRarity.UNCOMMON,
        CardTarget.SELF,
        1
    );

    public Parasitize() {
        super(ID, INFO);
        setMagic(3, 1);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Parasitize();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ParasitizePower(p, magicNumber)));
    }
}

