package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.util.CardStats;

public class BallisticShot extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("BallisticShot");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.ATTACK,
        CardRarity.UNCOMMON,
        CardTarget.ENEMY,
        1
    );

    public BallisticShot() {
        super(ID, INFO);
        setDamage(8, 10);
    }

    @Override
    public AbstractCard makeCopy() {
        return new BallisticShot();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage), AttackEffect.BLUNT_HEAVY));
        upgradeDamage(baseDamage);
        updateCost(1);
    }
}

