package thevagrantmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import thevagrantmod.TheVagrantMod;

public class MasterTrapperPower extends BasePower {
    public static final String ID = TheVagrantMod.makeID("MasterTrapperPower");

    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public MasterTrapperPower(AbstractCreature owner, int amount) {
        super(ID, TYPE, TURN_BASED, owner, -1);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}
