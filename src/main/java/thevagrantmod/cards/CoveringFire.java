package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.powers.CoveringFirePower;
import thevagrantmod.util.CardStats;

public class CoveringFire extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("CoveringFire");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.ATTACK,
        CardRarity.UNCOMMON,
        CardTarget.ALL_ENEMY,
        2
    );

    public CoveringFire() {
        super(ID, INFO);
        setDamage(3, 1);
        setMagic(3);
    }

    @Override
    public AbstractCard makeCopy() {
        return new CoveringFire();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new DamageRandomEnemyAction(new DamageInfo(p, damage), AttackEffect.BLUNT_LIGHT));
        }

        addToBot(new ApplyPowerAction(p, p, new CoveringFirePower(p, 1)));
    }
}

