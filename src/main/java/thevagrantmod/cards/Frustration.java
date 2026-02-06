package thevagrantmod.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thevagrantmod.TheVagrantMod;
import thevagrantmod.cardModifiers.JammedModifier;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.util.CardStats;

public class Frustration extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("Frustration");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.ATTACK,
        CardRarity.COMMON,
        CardTarget.ENEMY,
        1
    );

    public Frustration() {
        super(ID, INFO);
        setDamage(16, 4);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Frustration();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard c : p.hand.group){
            if (CardModifierManager.hasModifier(c, JammedModifier.ID)){
                return super.canUse(p, m);
            }
        }

        cantUseMessage = CardCrawlGame.languagePack.getUIString(TheVagrantMod.makeID("CantPlayFrustration")).TEXT[0];
        return false;
    }

    @Override
    public void triggerOnGlowCheck() {
        for (AbstractCard c : AbstractDungeon.player.hand.group){
            if (CardModifierManager.hasModifier(c, JammedModifier.ID)){
                glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();

                break;
            }
        }
    }
}

