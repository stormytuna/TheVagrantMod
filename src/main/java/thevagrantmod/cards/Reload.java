package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.actions.JamSpecificCardAction;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.util.CardStats;

public class Reload extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("Reload");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.SKILL,
        CardRarity.COMMON,
        CardTarget.SELF,
        0
    );

    public Reload() {
        super(ID, INFO);
        setMagic(2, 1);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Reload();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new JamSpecificCardAction(this));
    }
}

