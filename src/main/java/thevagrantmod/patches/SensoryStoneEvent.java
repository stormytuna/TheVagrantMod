package thevagrantmod.patches;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.events.beyond.SensoryStone;

import thevagrantmod.TheVagrantMod;

public class SensoryStoneEvent {
    @SpirePatch2(clz = SensoryStone.class, method = "getRandomMemory")
    public static class AddVagrantMemory {
        private static final String VAGRANT_MEMORY_TEXT = CardCrawlGame.languagePack.getUIString(TheVagrantMod.makeID("SensoryStone")).TEXT[0];

        @SpireInsertPatch(rloc = 3, localvars = {"memories"}) 
        public static void patch(ArrayList<String> memories) {
            memories.add(VAGRANT_MEMORY_TEXT);
        }

        @SpirePostfixPatch()
        public static void testPatch(SensoryStone __instance) {
            // Only here to allow easier testing if we tweak the string later
            if (true) {
                return;
            }

            __instance.imageEventText.updateBodyText(VAGRANT_MEMORY_TEXT);
        }
    }
}
