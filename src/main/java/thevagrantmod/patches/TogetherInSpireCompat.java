package thevagrantmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;

import spireTogether.util.SpireHelp;
import thevagrantmod.powers.BlightPower;
import thevagrantmod.powers.FlamingBoltPower;

public class TogetherInSpireCompat {
    @SpirePatch2(clz = BlightPower.class, method = "doBlightHpLoss", requiredModId = "spireTogether")
    public static class BlightHpLossHostOnly {
        @SpirePrefixPatch
        public static SpireReturn<Void> patch(BlightPower __instance) {
            if (SpireHelp.Multiplayer.IsHost()) {
                return SpireReturn.Continue();
            }

            __instance.amount -= BlightPower.STACKS_FOR_HEALTH_LOSS;
            return SpireReturn.Return();
        }
    }

    @SpirePatch2(clz = FlamingBoltPower.class, method = "atEndOfTurn", requiredModId = "spireTogether")
    public static class FlamingBoltDamageHostOnly {
        @SpirePrefixPatch
        public static SpireReturn<Void> patch() {
            if (SpireHelp.Multiplayer.IsHost()) {
                return SpireReturn.Continue();
            }

            return SpireReturn.Return();
        }
    }
}
