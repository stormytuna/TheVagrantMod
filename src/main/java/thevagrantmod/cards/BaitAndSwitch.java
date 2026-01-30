package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.util.CardStats;

public class BaitAndSwitch extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("BaitAndSwitch");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.SKILL,
        CardRarity.COMMON,
        CardTarget.SELF,
        1
    );

    public BaitAndSwitch() {
        super(ID, INFO);
        setBlock(6, 2);
        setMagic(1);
        cardsToPreview = new TripCharge();
    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
        keywords.add(TheVagrantMod.makeID("trap"));
        TheVagrantMod.logger.info(keywords.toString());
    }

    @Override
    public AbstractCard makeCopy() {
        return new BaitAndSwitch();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new MakeTempCardInDrawPileAction(new TripCharge(), magicNumber, true, true));
    }
}

