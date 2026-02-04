package thevagrantmod.actions;

import java.util.Collections;
import java.util.Stack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.helpers.CardModifierManager;
import thevagrantmod.cardModifiers.JammedModifier;
import thevagrantmod.effects.JamCardEffect;
import thevagrantmod.interfaces.InterfaceHelpers;
import thevagrantmod.patches.CustomCardFields;

public class JamAllCardsInHandAction extends AbstractGameAction {
    private Stack<AbstractCard> cardsToJam = new Stack<>();
    private float delayBetweenJams;
    private float nextJamTime;

    public JamAllCardsInHandAction() {
        duration = startDuration = Settings.ACTION_DUR_LONG;
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (!JammedModifier.canJam(c)) {
                    continue;
                }

                cardsToJam.add(c);
            }

            if (cardsToJam.size() == 0) {
                isDone = true;
                return;
            }

            Collections.shuffle(cardsToJam);

            // * 0.8 to ensure we always have a little extra buffer at the end of jamming all our cards
            delayBetweenJams = (startDuration * 0.8f) / cardsToJam.size();
            nextJamTime = startDuration - delayBetweenJams;
        }

        if (duration <= nextJamTime) {
            nextJamTime -= delayBetweenJams;

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
