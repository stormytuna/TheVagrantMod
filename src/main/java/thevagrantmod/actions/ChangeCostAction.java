package thevagrantmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

public class ChangeCostAction extends AbstractGameAction {
    private AbstractCard card;
    private int costModification;

    public ChangeCostAction(AbstractCard card, int costModification) {
        this.card = card;
        this.costModification = costModification;
        duration = startDuration = Settings.ACTION_DUR_XFAST;
    }

    @Override
    public void update() {
        if (card != null) {
            card.modifyCostForCombat(costModification); 
        }

        isDone = true;
    }
}
