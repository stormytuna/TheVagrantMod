package thevagrantmod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import thevagrantmod.TheVagrantMod;

public class ParasitizePower extends BasePower {
    public static final String ID = TheVagrantMod.makeID("ParasitizePower");

    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public ParasitizePower(AbstractCreature owner, int amount) {
        super(ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID == BlightPower.ID && source == owner && target != owner && !target.hasPower(ArtifactPower.POWER_ID)) {
            flash();
            addToBot(new GainBlockAction(owner, amount));
        }
    }
}
