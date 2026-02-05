package thevagrantmod.powers;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import thevagrantmod.TheVagrantMod;

public class GalvanizePower extends BasePower {
    public static final String ID = TheVagrantMod.makeID("GalvanizePower");

    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public GalvanizePower(AbstractCreature owner, int amount) {
        super(ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        } 
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && amount > 0) {
            flash();
            amount--;
            if (amount <= 0) {
                addToTop(new RemoveSpecificPowerAction(owner, owner, this));
            }
        }
    }

    @SpirePatch2(clz = AbstractCard.class, method = "freeToPlay")
    public static class MakeAnyCardFree {
        @SpirePostfixPatch
        public static boolean patch(AbstractCard __instance, boolean __result) {
            if (AbstractDungeon.player != null && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT && AbstractDungeon.player.hasPower(ID)) {
                return true;
            }

            return __result;
        }
    }
}
