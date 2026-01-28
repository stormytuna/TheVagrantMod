package thevagrantmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

import basemod.helpers.CardModifierManager;
import thevagrantmod.cardModifiers.JammedModifier;

public class UnjamSpecificCardAction extends AbstractGameAction {
    private AbstractCard card;

    public UnjamSpecificCardAction(AbstractCard card) {
        this.card = card;
        duration = startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            CardModifierManager.removeModifiersById(card, JammedModifier.ID, false);

            card.target = JamSpecificCardAction.Fields.oldCardTarget.get(card);

            isDone = true;
        }

        tickDuration();
    }
}
