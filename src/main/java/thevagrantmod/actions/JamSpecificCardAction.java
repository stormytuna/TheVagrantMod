package thevagrantmod.actions;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
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
            if (!JammedModifier.canJam(card)) {
                isDone = true;
                return;
            }

            CardModifierManager.addModifier(card, new JammedModifier());

            Fields.oldCardTarget.set(card, card.target);
            card.target = CardTarget.SELF;

            isDone = true;
        }

        tickDuration();
    }

    @SpirePatch2(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class Fields {
        public static SpireField<CardTarget> oldCardTarget = new SpireField<>(() -> CardTarget.NONE );
    }
}
