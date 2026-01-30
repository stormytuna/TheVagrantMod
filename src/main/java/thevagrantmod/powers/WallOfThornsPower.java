package thevagrantmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import thevagrantmod.TheVagrantMod;

public class WallOfThornsPower extends BasePower {
    public static final String ID = TheVagrantMod.makeID("WallOfThornsPower");

    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public WallOfThornsPower(AbstractCreature owner, int amount) {
        super(ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != null &&  info.type != DamageType.THORNS && info.type != DamageType.HP_LOSS && info.owner != owner){
            flash();
            addToBot(new ApplyPowerAction(info.owner, owner, new BlightPower(info.owner, amount)));
        }

        return damageAmount;
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
