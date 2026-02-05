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

    private boolean isPreview;

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.ATTACK,
        CardRarity.UNCOMMON,
        CardTarget.ENEMY,
        1
    );

    public RepeatingShot() {
        this(false);
    }

    public RepeatingShot(boolean isPreview) {
        super(ID, INFO);

        this.isPreview = isPreview;

        setDamage(8, 2);
        setExhaust(true);

        if (!isPreview) {
            cardsToPreview = new RepeatingShot(true);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new RepeatingShot();
    }

    @Override
    public void upgrade() {
        super.upgrade();

        if (!isPreview) {
            cardsToPreview.upgrade();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(m, damage), AttackEffect.BLUNT_LIGHT));
        addToBot(new MakeTempCardInHandAction(cardsToPreview.makeStatEquivalentCopy()));
    }
}

