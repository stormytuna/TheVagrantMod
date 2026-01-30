package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.powers.WallOfThornsPower;
import thevagrantmod.util.CardStats;

public class WallOfThorns extends BaseCard{
    public static final String ID = TheVagrantMod.makeID("WallOfThorns");

    private static final CardStats INFO = new CardStats(
            TheVagrant.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public WallOfThorns() {
        super(ID, INFO);
        setBlock(10, 3);
        setMagic(3, 1);
    }

    @Override
    public AbstractCard makeCopy() { return new WallOfThorns(); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new ApplyPowerAction(p, p, new WallOfThornsPower(p, magicNumber)));
    }
}
