package thevagrantmod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class JamCardEffect extends AbstractGameEffect {
    private AbstractCard c;

    public JamCardEffect(AbstractCard card) {
        duration = 1f;
        c = card;
    }

    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();

        if (this.duration < 0.0F) {
            this.isDone = true;
            this.c.resetAttributes();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        c.render(sb);
        // TODO: Some fancy vfx
        // Fade into gray (not sure how to do this :P)
        // Sparks particles
    }

    @Override
    public void dispose() {}
}
