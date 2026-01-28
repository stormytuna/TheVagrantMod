package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.util.CardStats;

public class FleetFooted extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("FleetFooted");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.SKILL,
        CardRarity.COMMON,
        CardTarget.SELF,
        2
    );

    public FleetFooted() {
        this(0);
    }

    public FleetFooted(int upgrades) {
        super(ID, INFO);
        setBlock(12);
        timesUpgraded = upgrades;
    }

    @Override
    public AbstractCard makeCopy() {
        return new FleetFooted(timesUpgraded);
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void upgrade() {
        upgradeBlock(4 + timesUpgraded);
        timesUpgraded++;
        upgraded = true;
        name = cardStrings.NAME + "+" + this.timesUpgraded;
        initializeTitle();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
    }
}

