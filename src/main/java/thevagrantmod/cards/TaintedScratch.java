package thevagrantmod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;
import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.powers.BlightPower;
import thevagrantmod.util.CardStats;

public class TaintedScratch extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("TaintedScratch");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.ATTACK,
        CardRarity.COMMON,
        CardTarget.ENEMY,
        0
    );

    public TaintedScratch() {
        super(ID, INFO);
        setDamage(3, 1);
        setMagic(2, 1);
    }

    @Override
    public AbstractCard makeCopy() {
        return new TaintedScratch();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new ClawEffect(m.hb.cX, m.hb.cY, new Color(49f/255f,42f/255f,54f/255f, 1), Color.WHITE), 0.1F));
        }

        DamageInfo info = new DamageInfo(p, damage);
        addToBot(new DamageAction(m, info, AttackEffect.NONE)); //Slash_Diagonal
        addToBot(new ApplyPowerAction(m, p, new BlightPower(m, magicNumber)));
    }
}

