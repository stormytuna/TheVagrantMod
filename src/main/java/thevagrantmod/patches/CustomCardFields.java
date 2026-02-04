package thevagrantmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;

public class CustomCardFields {
    @SpirePatch2(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class Fields {
        public static SpireField<CardTarget> oldCardTarget = new SpireField<>(() -> CardTarget.NONE );
        public static SpireField<Float> grayscaleStrength = new SpireField<>(() -> 0f );
    }
}
