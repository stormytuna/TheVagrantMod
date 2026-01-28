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
import thevagrantmod.powers.FlamingBoltPower;
import thevagrantmod.util.CardStats;

public class FlamingBolt extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("FlamingBolt");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.ATTACK,
        CardRarity.COMMON,
        CardTarget.ENEMY,
        1
    );

    public FlamingBolt() {
        super(ID, INFO);
        setDamage(6, 2);
    }

    @Override
    public AbstractCard makeCopy() {
        return new FlamingBolt();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        DamageInfo info = new DamageInfo(p, damage);
        addToBot(new DamageAction(m, info, AttackEffect.FIRE));
        addToBot(new ApplyPowerAction(m, p, new FlamingBoltPower(m, damage), damage));
    }
}

