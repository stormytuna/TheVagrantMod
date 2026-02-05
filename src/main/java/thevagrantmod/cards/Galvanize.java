package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.powers.BlightPower;
import thevagrantmod.powers.CoveringFirePower;
import thevagrantmod.powers.GalvanizePower;
import thevagrantmod.util.CardStats;

public class Galvanize extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("Galvanize");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.SKILL,
        CardRarity.RARE,
        CardTarget.SELF,
        -1
    );

    public Galvanize() {
        super(ID, INFO);
        setExhaust(true);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Galvanize();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int GALVANIZE_TIMES = energyOnUse;

        if (upgraded){
            GALVANIZE_TIMES++;
        }

        if (p.hasRelic(ChemicalX.ID)){
            p.getRelic(ChemicalX.ID).flash();
            GALVANIZE_TIMES += 2;
        }

        for(int i = 0; i < GALVANIZE_TIMES; i++){
            addToBot(new ApplyPowerAction(p, p, new GalvanizePower(p, 1)));
        }

        if (!this.freeToPlayOnce){
            p.energy.use(EnergyPanel.totalCount);
        }
    }
}

