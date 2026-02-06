package thevagrantmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.powers.BlightPower;

public class StrangeSpores extends BaseRelic{
    public static final String ID = TheVagrantMod.makeID("StrangeSpores");
    public static final RelicTier TIER = RelicTier.SHOP;
    public static final LandingSound LANDING_SOUND = LandingSound.MAGICAL;
    public static final int BLIGHT_AMOUNT = 2;

    public StrangeSpores() {
        super(ID, TIER, LANDING_SOUND);
        pool = TheVagrant.Meta.CARD_COLOR;
    }

    public AbstractRelic makeCopy(){
        return new StrangeSpores();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + BLIGHT_AMOUNT + DESCRIPTIONS[1];
    }

    @Override
    public void onPlayerEndTurn() {
        flash();
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
            if (mo.isDeadOrEscaped()){
                continue;
            }

            addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new BlightPower(mo, BLIGHT_AMOUNT)));
        }
    }
}
