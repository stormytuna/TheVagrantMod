package thevagrantmod.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.DamageCallbackAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.powers.BountyHuntingPower;
import thevagrantmod.util.CardStats;

public class BountyHunting extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("BountyHunting");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.ATTACK,
        CardRarity.RARE,
        CardTarget.ENEMY,
        1
    );

    public BountyHunting() {
        super(ID, INFO);
        setDamage(10, 2);
        setExhaust(true);
        tags.add(CardTags.HEALING);
    }

    @Override
    public AbstractCard makeCopy() {
        return new BountyHunting();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageCallbackAction(m, new DamageInfo(p, damage), AttackEffect.SMASH, unblockedDamage -> {
            if ((m.isDying || m.currentHealth <= 0) && !m.halfDead && !m.hasPower(MinionPower.POWER_ID)) {
                addToTop(new ApplyPowerAction(p, p, new BountyHuntingPower(p, 1)));

                // If final hit would trigger bounty hunting, our power is never added
                //   using a separate tracker instead of power amount
                BountyHuntingPower.GrantAdditionalCardRewards.bountiesClaimedThisFight++;
            }
        }));
    }
}

