package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.UpgradeRandomCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.util.CardStats;

public class Rangefinding extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("Rangefinding");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.ATTACK,
        CardRarity.COMMON,
        CardTarget.ENEMY,
        1
    );

    private static final int NUM_ATTACKS = 2;
    private static final int NUM_UPGRADES = 2;

    public Rangefinding() {
        super(ID, INFO);
        setDamage(6, 2);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Rangefinding();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < NUM_ATTACKS; i++) {
            DamageInfo info = new DamageInfo(p, damage);
            addToBot(new DamageAction(m, info, AttackEffect.BLUNT_LIGHT));
        }

        for (int i = 0; i < NUM_UPGRADES; i++) {
            addToBot(new UpgradeRandomCardAction());
        }
    }
}

