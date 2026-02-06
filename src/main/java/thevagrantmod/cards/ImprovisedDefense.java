package thevagrantmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.actions.JamSpecificCardAction;
import thevagrantmod.cardModifiers.JammedModifier;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.util.CardStats;

public class ImprovisedDefense extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("ImprovisedDefense");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.SKILL,
        CardRarity.COMMON,
        CardTarget.SELF,
        0
    );

    public ImprovisedDefense() {
        super(ID, INFO);
        setBlock(7, 3);
    }

    @Override
    public AbstractCard makeCopy() {
        return new ImprovisedDefense();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));

        ArrayList<AbstractCard> jammableCards = new ArrayList<>(); 
        for (AbstractCard c : p.hand.group) {
            if (c == this || !JammedModifier.canJam(c)) {
                continue;
            }

            jammableCards.add(c);
        }

        if (jammableCards.size() <= 0) {
            return;
        }

        int index = AbstractDungeon.cardRng.random(jammableCards.size() - 1);
        addToBot(new JamSpecificCardAction(jammableCards.get(index)));
    }
}

