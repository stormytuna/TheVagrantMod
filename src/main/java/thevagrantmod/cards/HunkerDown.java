package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import thevagrantmod.TheVagrantMod;
import thevagrantmod.actions.JamSpecificCardAction;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.util.CardStats;

public class HunkerDown extends BaseCard{
    public static final String ID = TheVagrantMod.makeID("HunkerDown");

    private static final CardStats INFO = new CardStats(
            TheVagrant.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    public HunkerDown() {
        super(ID, INFO);
        setBlock(15, 5);
        setMagic(2);
    }

    @Override
    public AbstractCard makeCopy() { return new HunkerDown(); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new ApplyPowerAction(p, p, new BlurPower(p, magicNumber)));
        addToBot(new JamSpecificCardAction(this));
    }
}
