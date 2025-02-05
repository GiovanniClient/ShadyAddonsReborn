package cheaters.get.banned.mixins;

import cheaters.get.banned.Shady;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static cheaters.get.banned.gui.polyconfig.PolyfrostConfig.NO_NAUSEA;
import static cheaters.get.banned.Shady.DEBUG;

@Mixin(EntityRenderer.class)
public class MixinNausea {

    // Redirect the first rotate call (the positive rotation)
    @Redirect(
            method = "setupCameraTransform",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V",
                    ordinal = 0
            )
    )
    private void redirectRotatePositive(float angle, float x, float y, float z) {
        EntityPlayer player = Shady.mc.thePlayer;
        if (player != null && player.isPotionActive(Potion.confusion) && NO_NAUSEA) {
            Utils.debug("Blocking nausea positive rotate");
            // Skip this rotation
            return;
        }
        // Otherwise call the normal rotate
        GlStateManager.rotate(angle, x, y, z);
    }

    // Redirect the scale call in the nausea block
    @Redirect(
            method = "setupCameraTransform",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V",
                    ordinal = 0
            )
    )
    private void redirectScale(float x, float y, float z) {
        EntityPlayer player = Shady.mc.thePlayer;
        if (player != null && player.isPotionActive(Potion.confusion) && NO_NAUSEA) {
            Utils.debug("Blocking nausea scale");
            // Instead of scaling by 1/f2, force identity scale
            GlStateManager.scale(1.0F, 1.0F, 1.0F);
            return;
        }
        GlStateManager.scale(x, y, z);
    }

    // Redirect the second rotate call (the negative rotation)
    @Redirect(
            method = "setupCameraTransform",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V",
                    ordinal = 1
            )
    )
    private void redirectRotateNegative(float angle, float x, float y, float z) {
        EntityPlayer player = Shady.mc.thePlayer;
        if (player != null && player.isPotionActive(Potion.confusion) && NO_NAUSEA) {
            Utils.debug("Blocking nausea negative rotate");
            // Skip this rotation
            return;
        }
        GlStateManager.rotate(angle, x, y, z);
    }
}
