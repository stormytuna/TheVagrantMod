package thevagrantmod.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.DamageCallbackAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.util.CardStats;

public class PinDown extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("PinDown");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.ATTACK,
        CardRarity.UNCOMMON,
        CardTarget.ENEMY,
        1
    );

    public PinDown() {
        super(ID, INFO);
        setDamage(5, 2);
    }

    @Override
    public AbstractCard makeCopy() {
        return new PinDown();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageCallbackAction(m, new DamageInfo(p, damage), AttackEffect.BLUNT_LIGHT, unblockedDamage -> {
            if (unblockedDamage > 0) {
                addToBot(new GainBlockAction(p, unblockedDamage));
            }
        }));
    }
}

