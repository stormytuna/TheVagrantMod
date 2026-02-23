package thevagrantmod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
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
        for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
            if (!monster.isDeadOrEscaped()) {
                addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect) new ExplosionSmallEffect(monster.hb.cX, monster.hb.cY), 0.1F));
            }
        }
        addToBot(new DamageAllEnemiesAction(p, damage, DamageType.NORMAL, AttackEffect.NONE));
        addToBot(new JamSpecificCardAction(this));
    }
}

