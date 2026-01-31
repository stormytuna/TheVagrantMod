package thevagrantmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import thevagrantmod.TheVagrantMod;
import thevagrantmod.character.TheVagrant;
import thevagrantmod.powers.BlightPower;
import thevagrantmod.util.CardStats;

public class Plague extends BaseCard {
    public static final String ID = TheVagrantMod.makeID("Plague");

    private static final CardStats INFO = new CardStats(
        TheVagrant.Meta.CARD_COLOR,
        CardType.SKILL,
        CardRarity.UNCOMMON,
        CardTarget.ALL_ENEMY,
        -1
    );

    public Plague() {
        super(ID, INFO);
        setMagic(3);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Plague();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int BLIGHT_TIMES = energyOnUse;

        if (upgraded){
            BLIGHT_TIMES++;
        }

        if (p.hasRelic(ChemicalX.ID)){
            p.getRelic(ChemicalX.ID).flash();
            BLIGHT_TIMES += 2;
        }

        for(int i = 0; i < BLIGHT_TIMES; i++){
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
                if (mo.isDeadOrEscaped()){
                    continue;
                }

                addToBot(new ApplyPowerAction(mo, p, new BlightPower(mo, magicNumber)));
            }
        }

        if (!this.freeToPlayOnce){
            p.energy.use(EnergyPanel.totalCount);
        }
    }
}

