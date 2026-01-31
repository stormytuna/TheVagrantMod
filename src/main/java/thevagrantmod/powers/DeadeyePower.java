package thevagrantmod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;

import thevagrantmod.TheVagrantMod;

public class DeadeyePower extends BasePower {
    public static final String ID = TheVagrantMod.makeID("DeadeyePower");

    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public DeadeyePower(AbstractCreature owner) {
        super(ID, TYPE, TURN_BASED, owner, 1);
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
    public float atDamageGive(float damage, DamageType type) {
        if (type == DamageType.NORMAL) {
            damage *= 2f;
        }

        return damage;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == CardType.ATTACK) {
            flash();
            addToTop(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }
}
