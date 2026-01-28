package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.powers.BlightPower;
import thevagrantmod.util.CardStats;

public class TaintedSlash extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("TaintedSlash");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.ATTACK,
        CardRarity.COMMON,
        CardTarget.ENEMY,
        0
    );

    public TaintedSlash() {
        super(ID, INFO);
        setDamage(3, 1);
        setMagic(2, 1);
    }

    @Override
    public AbstractCard makeCopy() {
        return new TaintedSlash();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        DamageInfo info = new DamageInfo(p, damage);
        addToBot(new DamageAction(m, info, AttackEffect.SLASH_HEAVY));
        addToBot(new ApplyPowerAction(m, p, new BlightPower(m, magicNumber)));
    }
}

