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
import thevagrantmod.interfaces.InterfaceHelpers;
import thevagrantmod.patches.CustomCardFields;

public class UnjamAllCardsInHandAction extends AbstractGameAction {
    private static final float TIME_PER_CARD_SLOW = 0.3f;
    private static final float TIME_PER_CARD_FAST = 0.15f;

    private Stack<AbstractCard> cardsToUnjam = new Stack<>();
    private float nextUnjamTime;
    private float timePerCard;

    public UnjamAllCardsInHandAction() {
        duration = startDuration = Settings.ACTION_DUR_LONG;
        timePerCard = Settings.FAST_MODE ? TIME_PER_CARD_FAST : TIME_PER_CARD_SLOW;
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

            Collections.shuffle(cardsToUnjam, new java.util.Random(AbstractDungeon.shuffleRng.randomLong()));

            float dur = (cardsToUnjam.size() + 1) * timePerCard;
            duration = startDuration = dur;

            nextUnjamTime = duration - timePerCard;
        }

        if (duration <= nextUnjamTime) {
            nextUnjamTime -= timePerCard;

            if (cardsToUnjam.size() <= 0) {
                isDone = true;
                return;
            }

            AbstractCard c = cardsToUnjam.pop();
            CardModifierManager.removeModifiersById(c, JammedModifier.ID, false);
            InterfaceHelpers.cardJamChanged(c, true);

            c.target = CustomCardFields.Fields.oldCardTarget.get(c);

            AbstractDungeon.effectList.add(new JamCardEffect(c, true));
        }

        tickDuration();
    }
}
