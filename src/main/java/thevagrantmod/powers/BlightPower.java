package thevagrantmod.powers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;

import thevagrantmod.TheVagrantMod;

public class BlightPower extends BasePower {
    public static final String ID = TheVagrantMod.makeID("BlightPower");
    public static final int STACKS_FOR_HEALTH_LOSS = 20;
    public static final int HEALTH_LOSS = 50;

    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public BlightPower(AbstractCreature owner, int amount) {
        super(ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    private void doBlightHpLoss() {
        flash();
        addToBot(new DamageAction(owner, new DamageInfo(owner, HEALTH_LOSS, DamageType.HP_LOSS)));
        amount -= STACKS_FOR_HEALTH_LOSS;
        if (amount <= 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }

    @Override
    public void onInitialApplication() {
        if (amount >= STACKS_FOR_HEALTH_LOSS) {
            doBlightHpLoss();
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);

        if (amount >= STACKS_FOR_HEALTH_LOSS) {
            doBlightHpLoss();
        }
    }

    @SpirePatch2(clz = AbstractCreature.class, method = "renderRedHealthBar")
    public static class BlightHealthBar {
        @SpirePostfixPatch
        public static void patch(AbstractCreature __instance, SpriteBatch sb, float x, float y, float ___targetHealthBarWidth, int ___currentHealth) {
            if (__instance.hasPower(ID)) {
                AbstractPower blight = __instance.getPower(ID);
                float blightness = 1f - (float)(STACKS_FOR_HEALTH_LOSS - blight.amount) / (float)(STACKS_FOR_HEALTH_LOSS);
                // lerp = start + (end - start) * t
                float a = 0.5f + (0.95f - 0.6f) * blightness;
                sb.setColor(49f/255f, 42f/255f, 54f/255f, a);

                int healthLoss = HEALTH_LOSS;
                if (__instance.hasPower(IntangiblePower.POWER_ID)) {
                    healthLoss = 1;
                }

                float blightBarWidth = (float)(___currentHealth - healthLoss) / (float)(___currentHealth);
                if (blightBarWidth < 0f) {
                    blightBarWidth = 0f;
                }
                blightBarWidth *= ___targetHealthBarWidth;

                // Maths is slightly different here than in vanilla bars, plus some +1 offsets below
                // Changed it so the discolouration overlaps better with the vanilla bar
                // Without it, a pixel was peaking out around the edge, made it look strange
                float healthBarHeight = 22f * Settings.scale; 
                float healthBarOffsetY = -29f * Settings.scale;

                if (__instance.currentHealth > 0) {
                    sb.draw(ImageMaster.HEALTH_BAR_L, x - healthBarHeight + blightBarWidth, y + healthBarOffsetY, healthBarHeight, healthBarHeight);
                }

                sb.draw(ImageMaster.HEALTH_BAR_B, x + blightBarWidth, y + healthBarOffsetY, ___targetHealthBarWidth - blightBarWidth + 1, healthBarHeight);
                sb.draw(ImageMaster.HEALTH_BAR_R, x + ___targetHealthBarWidth + 1f, y + healthBarOffsetY, healthBarHeight, healthBarHeight);
            }
        }
    }
}
