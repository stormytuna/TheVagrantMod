package thevagrantmod.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.CardModifierManager;
import thevagrantmod.TheVagrantMod;
import thevagrantmod.cardModifiers.JammedModifier;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.interfaces.InterfaceHelpers;
import thevagrantmod.patches.CustomCardFields;
import thevagrantmod.util.CardStats;

public class Refinement extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("Refinement");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.SKILL,
        CardRarity.UNCOMMON,
        CardTarget.SELF,
        1
    );

    public Refinement() {
        super(ID, INFO);
        setMagic(2, 1);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Refinement();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        if (upgraded) {
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
        } else {
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        }

        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainEnergyAction(magicNumber));
    }

    @SpirePatch2(clz = AbstractPlayer.class, method = "applyStartOfCombatLogic")
    public static class JamAllRefinementCardsOnCombatStart {
        private static void jamCard(AbstractCard c) {
            CardModifierManager.addModifier(c, new JammedModifier());
            InterfaceHelpers.cardJamChanged(c, false);

            CustomCardFields.Fields.oldCardTarget.set(c, c.target);
            c.target = CardTarget.SELF;

            CustomCardFields.Fields.grayscaleStrength.set(c, 1f);
        }

        @SpirePostfixPatch
        public static void patch() {
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (c.cardID == ID) {
                    jamCard(c);
                }
            }

            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.cardID == ID) {
                    jamCard(c);
                }
            }
        }
    }
}

