package thevagrantmod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;

import thevagrantmod.TheVagrantMod;

public class FlamingBoltPower extends BasePower {
    public static final String ID = TheVagrantMod.makeID("FlamingBoltPower");

    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;

    public FlamingBoltPower(AbstractCreature owner, int amount) {
        super(ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        flash();
        DamageInfo info = new DamageInfo(source, amount, DamageType.THORNS);
        addToBot(new DamageAction(owner, info, AttackEffect.FIRE));
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
