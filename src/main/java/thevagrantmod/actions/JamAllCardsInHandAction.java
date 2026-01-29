package thevagrantmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.helpers.CardModifierManager;
import thevagrantmod.cardModifiers.JammedModifier;
import thevagrantmod.effects.JamCardEffect;

public class JamAllCardsInHandAction extends AbstractGameAction {
    public JamAllCardsInHandAction() {
        duration = startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (!JammedModifier.canJam(c)) {
                    continue;
                }

                CardModifierManager.addModifier(c, new JammedModifier());

                JamSpecificCardAction.Fields.oldCardTarget.set(c, c.target);
                c.target = CardTarget.SELF;

                AbstractDungeon.effectList.add(new JamCardEffect(c));
            }

            isDone = true;
        }

        tickDuration();
    }
}
