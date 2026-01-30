package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.util.CardStats;

public class RepeatingShot extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("RepeatingShot");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.ATTACK,
        CardRarity.UNCOMMON,
        CardTarget.ENEMY,
        1
    );

    public RepeatingShot() {
        super(ID, INFO);
        setDamage(8, 2);
        setExhaust(true);
    }

    @Override
    public AbstractCard makeCopy() {
        return new RepeatingShot();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(m, damage), AttackEffect.BLUNT_LIGHT));

        AbstractCard card = makeStatEquivalentCopy();
        card.cost = 1;
        card.costForTurn = 1;
        card.isCostModifiedForTurn = false;
        addToBot(new MakeTempCardInHandAction(card));
    }
}

