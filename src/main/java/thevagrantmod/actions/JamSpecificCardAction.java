package thevagrantmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

import basemod.helpers.CardModifierManager;
import thevagrantmod.cardModifiers.JammedModifier;

public class JamSpecificCardAction extends AbstractGameAction {
    private AbstractCard card;

    public JamSpecificCardAction(AbstractCard card) {
        this.card = card;
        duration = startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            CardModifierManager.addModifier(card, new JammedModifier());
            isDone = true;
        }

        tickDuration();
    }
}
