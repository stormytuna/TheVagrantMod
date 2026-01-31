package thevagrantmod.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.interfaces.OnCardJamChangedSubscriber;

public class SparePartsPower extends BasePower implements OnCardJamChangedSubscriber {
    public static final String ID = TheVagrantMod.makeID("SparePartsPower");

    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public SparePartsPower(AbstractCreature owner, int amount) {
        super(ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0];
        } else {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public void receiveCardJamChanged(AbstractCard c, boolean jammedToUnjammed) {
        if (jammedToUnjammed) {
            flash();
            addToBot(new DrawCardAction(amount));
        }
    }
}
