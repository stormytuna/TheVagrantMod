package thevagrantmod.relics;

import com.evacipated.cardcrawl.mod.stslib.patches.bothInterfaces.OnCreateCardInterface;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;

public class Prototype extends BaseRelic implements OnCreateCardInterface {
    public static final String ID = TheVagrantMod.makeID("Prototype");
    public static final RelicTier TIER = RelicTier.UNCOMMON;
    public static final LandingSound LANDING_SOUND = LandingSound.CLINK;

    public Prototype() {
        super(ID, TIER, LANDING_SOUND);
        pool = TheVagrant.Meta.CARD_COLOR;
    }

    public AbstractRelic makeCopy(){
        return new Prototype();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onCreateCard(AbstractCard abstractCard) {
        abstractCard.upgrade();
    }
}
