package thevagrantmod.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;

/**
 * Allows Powers to implement effects when the player jams or unjams a card
 */
public interface OnCardJamChangedSubscriber {
    /**
     * Called just after the player jams or unjams a card
     * @param c The card jammed or unjammed
     * @param jammedToUnjammed true if the card was just unjammed, false if the card was just jammed
     */
    void receiveCardJamChanged(AbstractCard c, boolean jammedToUnjammed);
}
