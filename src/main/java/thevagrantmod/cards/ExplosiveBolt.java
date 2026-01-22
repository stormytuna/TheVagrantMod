package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.actions.JamSpecificCardAction;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.util.CardStats;

public class ExplosiveBolt extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("ExplosiveBolt");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.ATTACK,
        CardRarity.UNCOMMON,
        CardTarget.ALL_ENEMY,
        1
    );

    public ExplosiveBolt() {
        super(ID, INFO);
        setDamage(12, 3);
    }

    @Override
    public AbstractCard makeCopy() {
        return new ExplosiveBolt();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, damage, DamageType.NORMAL, AttackEffect.BLUNT_HEAVY));
        addToBot(new JamSpecificCardAction(this));
    }
}

