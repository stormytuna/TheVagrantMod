package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import thevagrantmod.TheVagrantMod;
import thevagrantmod.actions.JamSpecificCardAction;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.util.CardStats;

public class DrainingTouch extends BaseCard{
    public static final String ID = TheVagrantMod.makeID("DrainingTouch");

    private static final CardStats INFO = new CardStats(
            TheVagrant.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public DrainingTouch() {
        super(ID, INFO);
        setMagic(1, 1);
        setExhaust(true);
    }

    @Override
    public AbstractCard makeCopy() { return new DrainingTouch(); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber)));
    }
}
