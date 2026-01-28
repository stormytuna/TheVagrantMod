package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.util.CardStats;

public class SwiftShot extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("SwiftShot");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.ATTACK,
        CardRarity.UNCOMMON,
        CardTarget.ENEMY,
        0
    );

    public SwiftShot() {
        super(ID, INFO);
        setDamage(4, 2);
        setMagic(1, 1);
        setExhaust(true);
    }

    @Override
    public AbstractCard makeCopy() {
        return new SwiftShot();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageType.NORMAL), AttackEffect.BLUNT_LIGHT));
        addToBot(new DrawCardAction(magicNumber));
    }
}

