package thevagrantmod.powers;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

import thevagrantmod.TheVagrantMod;

public class ResoluteFormPower extends BasePower {
    public static final String ID = TheVagrantMod.makeID("ResoluteFormPower");

    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private int cardsToMakeFreeEachTurn;

    public ResoluteFormPower(AbstractCreature owner, int amount) {
        super(ID, TYPE, TURN_BASED, owner, owner, amount, false);
        cardsToMakeFreeEachTurn = amount;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + cardsToMakeFreeEachTurn + DESCRIPTIONS[1];
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        cardsToMakeFreeEachTurn += stackAmount;
    }

    @Override
    public void atStartOfTurn() {
        amount = cardsToMakeFreeEachTurn;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && amount > 0) {
            amount--;
            flash();
        }
    }

    @SpirePatch2(clz = AbstractCard.class, method = "freeToPlay")
    public static class MakeCardsFree {
        @SpirePostfixPatch
        public static boolean patch(AbstractCard __instance, boolean __result) {
            if (AbstractDungeon.player != null && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT && AbstractDungeon.player.hasPower(ID)) {
                ResoluteFormPower power = (ResoluteFormPower)AbstractDungeon.player.getPower(ID);
                if (power.amount > 0) {
                    return true;
                }
            }

            return __result;
        }
    }
}
