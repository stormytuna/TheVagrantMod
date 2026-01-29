package thevagrantmod.actions;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.helpers.CardModifierManager;
import thevagrantmod.cardModifiers.JammedModifier;
import thevagrantmod.effects.JamCardEffect;

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

            Fields.oldCardTarget.set(card, card.target);
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

    @SpirePatch2(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class Fields {
        public static SpireField<CardTarget> oldCardTarget = new SpireField<>(() -> CardTarget.NONE );
    }
}
