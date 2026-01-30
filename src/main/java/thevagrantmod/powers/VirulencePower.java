package thevagrantmod.powers;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import thevagrantmod.TheVagrantMod;

public class VirulencePower extends BasePower {
    public static final String ID = TheVagrantMod.makeID("VirulencePower");

    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public VirulencePower(AbstractCreature owner, int amount) {
        super(ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @SpirePatch2(clz = ApplyPowerAction.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCreature.class, AbstractCreature.class, AbstractPower.class, int.class, boolean.class, AttackEffect.class})
    public static class DoVirulenceEffect {
        @SpirePrefixPatch
        public static void patch(AbstractPower powerToApply, @ByRef int[] stackAmount) {
            if (powerToApply.ID == BlightPower.ID && AbstractDungeon.player.hasPower(ID)) {
                AbstractPower virulencePower = AbstractDungeon.player.getPower(ID);
                virulencePower.flash();
                powerToApply.amount += virulencePower.amount;
                stackAmount[0] += virulencePower.amount;
            }
        }
    }
}
