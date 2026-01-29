package thevagrantmod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import thevagrantmod.TheVagrantMod;

public class JamCardEffect extends AbstractGameEffect {
    private AbstractCard c;

    private static final float DUR = 0.5f;
    private static final float BEGIN_GRAY_FADEOUT = 0.4f;

    public JamCardEffect(AbstractCard card) {
        duration = DUR;
        c = card;
    }

    @Override
    public void update() {
        if (duration == DUR) {
            CardCrawlGame.sound.playAV(TheVagrantMod.makeID("JAM"), 0.2f, 4f);
        }

        this.duration -= Gdx.graphics.getDeltaTime();

        // TODO: Maybe some fancy particles?

        if (duration <= BEGIN_GRAY_FADEOUT) {
            Fields.grayscaleStrength.set(c, 1f - (duration / BEGIN_GRAY_FADEOUT));
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
            this.c.resetAttributes();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        c.render(sb);
    }

    @Override
    public void dispose() {}

    @SpirePatch2(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class Fields {
        public static SpireField<Float> grayscaleStrength = new SpireField<>(() -> 0f );
    }

}
