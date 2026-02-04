package thevagrantmod.actions;

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

public class JamSpecificCardAction extends AbstractGameAction {
    private AbstractCard card;

    public JamSpecificCardAction(AbstractCard card) {
        this.card = card;
        setValues(AbstractDungeon.player, AbstractDungeon.player, amount);
        actionType = ActionType.EXHAUST;
        duration = startDuration = Settings.ACTION_DUR_MED;
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            if (!JammedModifier.canJam(card)) {
                isDone = true;
                return;
            }

            CardModifierManager.addModifier(card, new JammedModifier());
            InterfaceHelpers.cardJamChanged(card, false);

            CustomCardFields.Fields.oldCardTarget.set(card, card.target);
            card.target = CardTarget.SELF;

            AbstractDungeon.effectList.add(new JamCardEffect(card));
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
