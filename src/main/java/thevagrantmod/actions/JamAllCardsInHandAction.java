package thevagrantmod.actions;

import java.util.Collections;
import java.util.Stack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.helpers.CardModifierManager;
import thevagrantmod.cardModifiers.JammedModifier;
import thevagrantmod.effects.JamCardEffect;
import thevagrantmod.interfaces.InterfaceHelpers;
import thevagrantmod.patches.CustomCardFields;
import thevagrantmod.powers.PracticeShotPower;

public class JamAllCardsInHandAction extends AbstractGameAction {
    private static final float TIME_PER_CARD_SLOW = 0.3f;
    private static final float TIME_PER_CARD_FAST = 0.15f;

    private Stack<AbstractCard> cardsToJam = new Stack<>();
    private float nextJamTime;
    private float timePerCard;

    public JamAllCardsInHandAction() {
        duration = startDuration = Settings.ACTION_DUR_LONG;
        timePerCard = Settings.FAST_MODE ? TIME_PER_CARD_FAST : TIME_PER_CARD_SLOW;
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (!JammedModifier.canJam(c)) {
                    continue;
                }

                AbstractPower practiceShotPower = AbstractDungeon.player.getPower(PracticeShotPower.ID);
                if (practiceShotPower != null) {
                    practiceShotPower.flash();
                    practiceShotPower.amount--;
                    if (practiceShotPower.amount <= 0) {
                        AbstractDungeon.player.powers.remove(practiceShotPower);
                    }

                    continue;
                }

                cardsToJam.add(c);
            }

            if (cardsToJam.size() == 0) {
                isDone = true;
                return;
            }

            Collections.shuffle(cardsToJam);

            float dur = (cardsToJam.size() + 1) * timePerCard;
            duration = startDuration = dur;

            nextJamTime = duration - timePerCard;
        }

        if (duration <= nextJamTime) {
            nextJamTime -= timePerCard;

            if (cardsToJam.size() <= 0) {
                isDone = true;
                return;
            }

            AbstractCard c = cardsToJam.pop();
            CardModifierManager.addModifier(c, new JammedModifier());
            InterfaceHelpers.cardJamChanged(c, false);

            CustomCardFields.Fields.oldCardTarget.set(c, c.target);
            c.target = CardTarget.SELF;

            AbstractDungeon.effectList.add(new JamCardEffect(c));
        }

        tickDuration();
    }
}
