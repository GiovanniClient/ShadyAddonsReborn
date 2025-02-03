package cheaters.get.banned.mixins;

import cheaters.get.banned.gui.polyconfig.PolyfrostConfig;
import net.minecraft.network.play.client.C01PacketChatMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(C01PacketChatMessage.class)
public class MixinChat {

    @Shadow private String message;

    @Inject(method = "<init>(Ljava/lang/String;)V", at = @At("RETURN"))
    private void modifyChatMessage(String message, CallbackInfo ci) {
        if (PolyfrostConfig.FAKE_IRONMAN && !message.startsWith("/")) {  // Skip commands if enabled
            this.message = "♲: " + message;
        }
    }

}
