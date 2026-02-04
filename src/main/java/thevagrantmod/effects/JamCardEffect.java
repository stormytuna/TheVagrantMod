package thevagrantmod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import thevagrantmod.TheVagrantMod;
import thevagrantmod.patches.CustomCardFields;

public class JamCardEffect extends AbstractGameEffect {
    private AbstractCard c;
    private boolean unjamming;

    private static final float DUR = 0.5f;
    private static final float BEGIN_GRAY_FADEOUT = 0.4f;

    public JamCardEffect(AbstractCard card) {
        this(card, false);
    }

    public JamCardEffect(AbstractCard card, boolean unjamming) {
        duration = DUR;
        c = card;
        this.unjamming = unjamming;
    }

    public static JamCardEffect makeJamCardEffectAndPrepCardValues(AbstractCard card, boolean unjamming) {
        JamCardEffect effect = new JamCardEffect(card, unjamming);

        if (AbstractDungeon.player.hoveredCard == card) {
            AbstractDungeon.player.releaseCard();
        }
        AbstractDungeon.actionManager.removeFromQueue(card);
        card.unhover();
        card.untip();
        card.stopGlowing();

        return effect;
    }

    @Override
    public void update() {
        if (duration == DUR) {
            String sound = unjamming ? "UNJAM" : "JAM";
            CardCrawlGame.sound.playAV(TheVagrantMod.makeID(sound), MathUtils.random(0.1f, 0.3f), 4f);
        }

        this.duration -= Gdx.graphics.getDeltaTime();

        if (!Settings.DISABLE_EFFECTS) {
            float spawnX = c.current_x + (MathUtils.random(-85f, 85f) * Settings.scale);
            float spawnY = c.current_y + (MathUtils.random(-110f, 110f) * Settings.scale);
            JamSparkEffect particle = new JamSparkEffect(spawnX, spawnY);
            AbstractDungeon.topLevelEffectsQueue.add(particle);
        }

        if (duration <= BEGIN_GRAY_FADEOUT) {
            float grayness = 1f - (duration / BEGIN_GRAY_FADEOUT);
            if (unjamming) {
                grayness = 1f - grayness;
            }

            CustomCardFields.Fields.grayscaleStrength.set(c, grayness);
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
            this.c.resetAttributes();
            CustomCardFields.Fields.grayscaleStrength.set(c, unjamming ? 0f : 1f);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        c.render(sb);
    }

    @Override
    public void dispose() {}

    @SpirePatch2(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
    public static class CopyFields {
        @SpirePostfixPatch
        public static void patch(AbstractCard __instance, AbstractCard __result) {
            float grayscaleStrength = CustomCardFields.Fields.grayscaleStrength.get(__instance); 
            CustomCardFields.Fields.grayscaleStrength.set(__result, grayscaleStrength);
        }
    }
}
