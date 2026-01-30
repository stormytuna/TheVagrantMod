package thevagrantmod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class JamSparkEffect extends AbstractGameEffect  {
    private static final float DUR = 0.8f;

    private AtlasRegion img;
    private float x;
    private float y;
    private float vX;
    private float vY;

    public JamSparkEffect(float x, float y) {
        img = ImageMaster.STRIKE_LINE_2;
        duration = MathUtils.random(DUR / 2, DUR);

        this.x = x + (MathUtils.random(-30f, 30f) * Settings.scale) - (img.packedWidth / 2f);
        this.y = y + (MathUtils.random(-40f, 40f) * Settings.scale) - (img.packedHeight / 2f);
        Vector2 velocity = new Vector2(this.x - x, this.y - y);
        vX = velocity.nor().x * MathUtils.random(300f, 900f) * Settings.scale;;
        vY = velocity.nor().y * MathUtils.random(200f, 800f) * Settings.scale;;

        color = new Color(173f / 255f, 216f / 255f, 230f / 255f, 0f);
        rotation = MathUtils.random(0f, 360f);
        scale = MathUtils.random(0.12f, 0.5f) * Settings.scale;
    }

    @Override
    public void update() {
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();

        vX *= 0.98f;
        vY *= 0.98f;

        Vector2 velocity = new Vector2(vX, vY);
        rotation = velocity.angle();

        if (1f - duration < 0.1f) {
            color.a = Interpolation.fade.apply(0f, DUR, (DUR - duration) * 10f);
        } else {
            color.a = Interpolation.pow2Out.apply(0f, DUR, duration);
        }

        duration -= Gdx.graphics.getDeltaTime();
        if (duration < 0f) {
            isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1); 
        sb.setColor(color);
        sb.draw(img, x, y, img.packedWidth / 2f, img.packedHeight / 2f, img.packedWidth, img.packedHeight, scale, scale, rotation);
        sb.setBlendFunction(770, 771);
    }

    @Override
    public void dispose() { }
}
