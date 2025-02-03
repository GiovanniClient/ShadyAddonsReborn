package cheaters.get.banned.mixins;

import cheaters.get.banned.Shady;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static cheaters.get.banned.gui.polyconfig.PolyfrostConfig.NO_BLINDNESS;

@Mixin(EntityRenderer.class)
public class MixinBlindness {
    @Inject(method = "setupFog", at = @At("HEAD"), cancellable = true)
    private void onSetupFog(int startCoords, float partialTicks, CallbackInfo ci) {
        EntityPlayer player = Shady.mc.thePlayer;

        if (player != null && player.isPotionActive(Potion.blindness) && NO_BLINDNESS)
            ci.cancel();
    }
}
