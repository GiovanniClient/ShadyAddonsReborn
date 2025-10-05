package cheaters.get.banned.features;

import net.minecraft.client.renderer.entity.RenderLightningBolt;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.ResourceLocation;

import static cheaters.get.banned.gui.polyconfig.PolyfrostConfig.NO_THUNDERS;

public class NoRenderLightning extends RenderLightningBolt {

    public NoRenderLightning(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntityLightningBolt entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if (!NO_THUNDERS) {
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityLightningBolt entity) {
        return null;
    }
}
