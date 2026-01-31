package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.util.CardStats;

public class PrimeCharges extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("PrimeCharges");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.SKILL,
        CardRarity.UNCOMMON,
        CardTarget.SELF,
        1
    );

    public PrimeCharges() {
        super(ID, INFO);
        setMagic(3);
        cardsToPreview = new TripCharge();
        setExhaust(true, false);
    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
        keywords.add(TheVagrantMod.makeID("trap"));
        TheVagrantMod.logger.info(keywords.toString());
    }

    @Override
    public AbstractCard makeCopy() {
        return new PrimeCharges();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInDrawPileAction(new TripCharge(), magicNumber, true, true));
    }
}

