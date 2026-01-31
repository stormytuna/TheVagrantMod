package thevagrantmod.actions;

import java.util.Collections;
import java.util.Stack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.helpers.CardModifierManager;
import thevagrantmod.cardModifiers.JammedModifier;
import thevagrantmod.effects.JamCardEffect;

public class UnjamAllCardsInHandAction extends AbstractGameAction {
    private Stack<AbstractCard> cardsToUnjam = new Stack<>();
    private float delayBetweenUnjams;
    private float nextUnjamTime;

    public UnjamAllCardsInHandAction() {
        duration = startDuration = Settings.ACTION_DUR_LONG;
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (!CardModifierManager.hasModifier(c, JammedModifier.ID)) {
                    continue;
                }

                cardsToUnjam.add(c);
            }

            if (cardsToUnjam.size() == 0) {
                isDone = true;
                return;
            }

            Collections.shuffle(cardsToUnjam);

            // * 0.8 to ensure we always have a little extra buffer at the end of jamming all our cards
            delayBetweenUnjams = (startDuration * 0.8f) / cardsToUnjam.size();
            nextUnjamTime = startDuration - delayBetweenUnjams;
        }

        if (duration <= nextUnjamTime) {
            nextUnjamTime -= delayBetweenUnjams;

            if (cardsToUnjam.size() <= 0) {
                isDone = true;
                return;
            }

            AbstractCard c = cardsToUnjam.pop();
            CardModifierManager.removeModifiersById(c, JammedModifier.ID, false);

            c.target = JamSpecificCardAction.Fields.oldCardTarget.get(c);

            AbstractDungeon.effectList.add(new JamCardEffect(c, true));
        }

        tickDuration();
    }
}
