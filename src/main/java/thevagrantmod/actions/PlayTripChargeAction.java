package thevagrantmod.actions;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.powers.MasterTrapperPower;

public class PlayTripChargeAction extends AbstractGameAction {
    AbstractCard card;
    int damage;

    public PlayTripChargeAction(AbstractCard card, int damage) {
        this.card = card;
        this.damage = damage;
        duration = startDuration = Settings.ACTION_DUR_XFAST;
    }

    private void damageAll() {
        addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, damage, DamageType.THORNS, AttackEffect.FIRE));
    }

    private void damageLowestHealth() {
        AbstractMonster weakestMonster = null;
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (monster.isDeadOrEscaped()) {
                continue;
            }

            if (weakestMonster == null || monster.currentHealth < weakestMonster.currentHealth) {
                weakestMonster = monster;
            }
        }

        if (weakestMonster == null) {
            return;
        }

        addToTop(new DamageAction(weakestMonster, new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.THORNS), AttackEffect.FIRE));
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                isDone = true;
                return;
            }

            card.superFlash();

            float rad = (card.targetAngle * MathUtils.degreesToRadians) + (MathUtils.PI / 2);
            Vector2 dir = new Vector2(MathUtils.cos(rad), MathUtils.sin(rad));
            dir.x *= 150f * Settings.scale;
            dir.y *= 150f * Settings.scale;
            card.target_x += dir.x;
            card.target_y += dir.y;

            if (AbstractDungeon.player.hasPower(MasterTrapperPower.ID)){
                damageAll();
            } else {
                damageLowestHealth();
            }

            addToTop(new DrawCardAction(1));
            addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand, true));
        }

        tickDuration();
    }
}
