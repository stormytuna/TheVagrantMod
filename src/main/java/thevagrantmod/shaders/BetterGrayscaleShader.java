package thevagrantmod.shaders;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import thevagrantmod.TheVagrantMod;

public class BetterGrayscaleShader {
    private static ShaderProgram betterGrayscaleShader;

    private static final String fragmentShader = "" +
        "uniform sampler2D u_texture;" +
        "varying vec4 v_color;" +
        "varying vec2 v_texCoord;" +
        "varying float v_strength;" +
        "" +
        "void main() {" +
        "  vec4 texColor = texture2D(u_texture, v_texCoord);" +
        "  float gray = dot(texColor.rgb, vec3(0.299, 0.587, 0.114));" +
        "  gl_FragColor = vec4(mix(texColor.r, gray, v_strength), mix(texColor.g, gray, v_strength), mix(texColor.b, gray, v_strength), texColor.a);" +
        "}";

    private static final String vertexShader = "" +
        "uniform mat4 u_projTrans;" +
        "attribute vec4 a_position;" +
        "attribute vec2 a_texCoord0;" +
        "attribute vec4 a_color;" +
        "attribute float a_strength;" +
        "varying vec4 v_color;" +
        "varying vec2 v_texCoord;" +
        "varying float v_strength;" +
        "uniform vec2 u_viewportInverse;" +
        "" +
        "void main() {" +
        "  gl_Position = u_projTrans * a_position;" +
        "  v_texCoord = a_texCoord0;" +
        "  v_color = a_color;" +
        "  v_strength = a_strength;" +
        "}";

    public static void loadShader() {
        betterGrayscaleShader = new ShaderProgram(vertexShader, fragmentShader);
        TheVagrantMod.logger.info("Loaded betterGrayscaleShader, compile log:");
        TheVagrantMod.logger.info(betterGrayscaleShader.getLog());
        TheVagrantMod.logger.info("Is compiled? " + betterGrayscaleShader.isCompiled());
    }

    public static void apply(SpriteBatch sb, float strength) {
        sb.end();
        betterGrayscaleShader.setAttributef("a_strength", strength, 0f, 0f, 0f);
        sb.setShader(betterGrayscaleShader);
        sb.begin();
    }
}
