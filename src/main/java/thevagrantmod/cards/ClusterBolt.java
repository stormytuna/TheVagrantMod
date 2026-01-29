package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.actions.JamAllCardsInHandAction;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.util.CardStats;

public class ClusterBolt extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("ClusterBolt");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.ATTACK,
        CardRarity.RARE,
        CardTarget.ALL_ENEMY,
        2
    );

    public ClusterBolt() {
        super(ID, INFO);
        setDamage(8, 2);
        setMagic(3);
    }

    @Override
    public AbstractCard makeCopy() {
        return new ClusterBolt();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new DamageAllEnemiesAction(p, damage, DamageType.NORMAL, AttackEffect.FIRE));
        }

        addToBot(new JamAllCardsInHandAction());
    }
}

