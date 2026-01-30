package thevagrantmod.powers;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;

import thevagrantmod.TheVagrantMod;

public class BountyHuntingPower extends BasePower {
    public static final String ID = TheVagrantMod.makeID("BountyHuntingPower");

    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public BountyHuntingPower(AbstractCreature owner, int amount) {
        super(ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0];
        } else {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }

    @SpirePatch2(clz = CombatRewardScreen.class, method = "setupItemReward")
    public static class GrantAdditionalCardRewards {
        public static int bountiesClaimedThisFight = 0;

        @SpireInsertPatch(rloc = 16)
        public static void patch(ArrayList<RewardItem> ___rewards) {
            if (bountiesClaimedThisFight > 0) {
                for (int i = 0; i < bountiesClaimedThisFight; i++) {
                    RewardItem reward = new RewardItem();
                    if (reward.cards.size() > 0) {
                        ___rewards.add(reward);
                    }
                }
            }

            bountiesClaimedThisFight = 0;
        }
    }
}
