package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.util.CardStats;

public class ScavengeCells extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("ScavengeCells");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.SKILL,
        CardRarity.UNCOMMON,
        CardTarget.SELF,
        0
    );

    public ScavengeCells() {
        this(0);
    }

    public ScavengeCells(int upgrades) {
        super(ID, INFO);
        setMagic(1);
        timesUpgraded = upgrades;
    }

    @Override
    public AbstractCard makeCopy() {
        return new ScavengeCells(timesUpgraded);
    }

    @Override
    public void upgrade() {
        upgradeMagicNumber(1);
        timesUpgraded++;
        upgraded = true;

        name = cardStrings.NAME + "+" + timesUpgraded;
        initializeTitle();
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainEnergyAction(magicNumber));
    }
}

