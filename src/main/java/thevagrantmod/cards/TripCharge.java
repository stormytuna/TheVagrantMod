package thevagrantmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.actions.PlayTrapAction;
import thevagrantmod.util.CardStats;

public class TripCharge extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("TripCharge");

    private static final CardStats INFO = new CardStats(
        CardColor.COLORLESS,
        CardType.SKILL,
        CardRarity.SPECIAL,
        CardTarget.NONE,
        -2
    );

    private static final String cantPlayCardString = CardCrawlGame.languagePack.getCardStrings("Tactician").EXTENDED_DESCRIPTION[0];

    public TripCharge() {
        super(ID, INFO);
        setMagic(6, 2);
    }

    @Override
    public AbstractCard makeCopy() {
        return new TripCharge();
    }

    @Override
    public void triggerWhenDrawn() {
        addToBot(new PlayTrapAction(this, magicNumber));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = cantPlayCardString;
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        triggerWhenDrawn();
    }
}

