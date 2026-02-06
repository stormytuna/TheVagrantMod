package thevagrantmod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import thevagrantmod.TheVagrantMod;

public class PracticeShotPower extends BasePower {
    public static final String ID = TheVagrantMod.makeID("PracticeShotPower");

    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public PracticeShotPower(AbstractCreature owner) {
        super(ID, TYPE, TURN_BASED, owner, 1);
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0];
        } else {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
