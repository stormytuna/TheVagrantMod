package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.util.CardStats;

public class HonedShot extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("HonedShot");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.ATTACK,
        CardRarity.COMMON,
        CardTarget.ENEMY,
        1
    );

    public HonedShot() {
        this(0);
    }

    public HonedShot(int upgrades) {
        super(ID, INFO);
        setDamage(9, 3);
        timesUpgraded = upgrades;
    }

    @Override
    public AbstractCard makeCopy() {
        return new HonedShot(timesUpgraded);
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void upgrade() {
        upgradeDamage(3 + timesUpgraded);
        timesUpgraded++;
        upgraded = true;
        name = cardStrings.NAME + "+" + this.timesUpgraded;
        initializeTitle();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageType.NORMAL), AttackEffect.BLUNT_LIGHT));
    }
}

