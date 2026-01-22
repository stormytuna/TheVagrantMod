package thevagrantmod.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.DamageCallbackAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.powers.BlightPower;
import thevagrantmod.util.CardStats;

public class WeepingWounds extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("WeepingWounds");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.SKILL,
        CardRarity.RARE,
        CardTarget.ENEMY,
        1
    );

    public WeepingWounds() {
        super(ID, INFO);
        setDamage(4, 1);
    }

    @Override
    public AbstractCard makeCopy() {
        return new WeepingWounds();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageCallbackAction(m, new DamageInfo(p, damage), AttackEffect.FIRE, unblockedDamage -> {
            if (unblockedDamage > 0) {
                addToBot(new ApplyPowerAction(m, p, new BlightPower(m, unblockedDamage)));
            }
        }));
    }
}

