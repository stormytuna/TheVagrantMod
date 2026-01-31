package thevagrantmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.helpers.CardModifierManager;
import thevagrantmod.cardModifiers.JammedModifier;
import thevagrantmod.effects.JamCardEffect;
import thevagrantmod.interfaces.InterfaceHelpers;

public class UnjamSpecificCardAction extends AbstractGameAction {
    private AbstractCard card;

    public UnjamSpecificCardAction(AbstractCard card) {
        this.card = card;
        duration = startDuration = Settings.ACTION_DUR_MED;
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            CardModifierManager.removeModifiersById(card, JammedModifier.ID, false);
            InterfaceHelpers.cardJamChanged(card, true);

            card.target = JamSpecificCardAction.Fields.oldCardTarget.get(card);

            // TODO: Centralise!!
            AbstractDungeon.effectList.add(new JamCardEffect(card, true));
            if (AbstractDungeon.player.hoveredCard == card) {
                AbstractDungeon.player.releaseCard();
            }
            AbstractDungeon.actionManager.removeFromQueue(card);
            card.unhover();
            card.untip();
            card.stopGlowing();
        }

        tickDuration();
    }
}
