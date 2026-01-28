package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import thevagrantmod.TheVagrantMod;
import thevagrantmod.actions.JamSpecificCardAction;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.util.CardStats;

public class ScrapArmor extends BaseCard{
    public static final String ID = TheVagrantMod.makeID("ScrapArmor");

    private static final CardStats INFO = new CardStats(
            TheVagrant.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public ScrapArmor() {
        super(ID, INFO);
        setMagic(4, 1);
    }

    @Override
    public AbstractCard makeCopy() { return new ScrapArmor(); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new PlatedArmorPower(p, magicNumber)));
        addToBot(new JamSpecificCardAction(this));
    }
}
