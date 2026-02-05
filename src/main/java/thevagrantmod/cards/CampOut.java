package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;

import thevagrantmod.util.CardStats;

public class CampOut extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("CampOut");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.POWER,
        CardRarity.UNCOMMON,
        CardTarget.SELF,
        0
    );

    public CampOut() {
        super(ID, INFO);
        setMagic(3, 1);
        tags.add(CardTags.HEALING);
    }

    @Override
    public AbstractCard makeCopy() {
        return new CampOut();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new RegenPower(p, magicNumber)));
    }
}

