package thevagrantmod.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.interfaces.OnCardJamChangedSubscriber;

public class Toolbelt extends BaseRelic implements OnCardJamChangedSubscriber{
    public static final String ID = TheVagrantMod.makeID("Toolbelt");
    public static final RelicTier TIER = RelicTier.RARE;
    public static final LandingSound LANDING_SOUND = LandingSound.CLINK;
    public static final int BLOCK_AMOUNT = 4;

    public Toolbelt() {
        super(ID, TIER, LANDING_SOUND);
        pool = TheVagrant.Meta.CARD_COLOR;
    }

    public AbstractRelic makeCopy(){
        return new Toolbelt();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + BLOCK_AMOUNT + DESCRIPTIONS[1];
    }

    @Override
    public void receiveCardJamChanged(AbstractCard c, boolean jammedToUnjammed) {
        if (jammedToUnjammed) {
            flash();
            addToBot(new GainBlockAction(AbstractDungeon.player, BLOCK_AMOUNT));
        }
    }
}
