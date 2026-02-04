package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.powers.MasterTrapperPower;
import thevagrantmod.util.CardStats;

public class TripCharge extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("TripCharge");

    private static final CardStats INFO = new CardStats(
        CardColor.COLORLESS, // TODO: *Should* be colourless, but don't want it to display in Colourless tab of card library..
        CardType.SKILL,
        CardRarity.SPECIAL,
        CardTarget.NONE,
        -2
    );

    private static final String cantPlayCardString = CardCrawlGame.languagePack.getCardStrings("Tactician").EXTENDED_DESCRIPTION[0];

    public TripCharge() {
        super(ID, INFO);
        setDamage(6, 2);
    }

    @Override
    public AbstractCard makeCopy() {
        return new TripCharge();
    }

    private void damageAll() {
        addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, damage, DamageType.THORNS, AttackEffect.FIRE));
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

        addToBot(new DamageAction(weakestMonster, new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.THORNS), AttackEffect.FIRE));
    }

    @Override
    public void triggerWhenDrawn() {
        superFlash();

        if (AbstractDungeon.player.hasPower(MasterTrapperPower.ID)){
            damageAll();
        } else {
            damageLowestHealth();
        }

        addToBot(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand, true));
        addToBot(new DrawCardAction(1));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = cantPlayCardString;
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        triggerWhenDrawn();
    }
}

