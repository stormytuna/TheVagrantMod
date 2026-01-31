package thevagrantmod.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class InterfaceHelpers {
    /**
     * Calls the OnCardJamChangedSubscriber::receiveCardJamChanged method for all the player's Powers
     * @param c The card jammed or unjammed
     * @param jammedToUnjammed true if the card was just unjammed, false if the card was just jammed
     */
    public static void cardJamChanged(AbstractCard c, boolean jammedToUnjammed) {
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof OnCardJamChangedSubscriber) {
                ((OnCardJamChangedSubscriber)p).receiveCardJamChanged(c, jammedToUnjammed);
            }
        }
    }
}
