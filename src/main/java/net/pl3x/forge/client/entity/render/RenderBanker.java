package net.pl3x.forge.client.entity.render;

import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.client.Pl3xForgeClient;
import net.pl3x.forge.client.entity.EntityBanker;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderBanker extends RenderLiving<EntityBanker> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Pl3xForgeClient.modId + ":textures/entity/banker.png");

    public RenderBanker(RenderManager renderManager) {
        super(renderManager, new ModelVillager(0), 0);
        this.addLayer(new LayerCustomHead(this.getMainModel().villagerHead));
    }

    @Override
    public ModelVillager getMainModel() {
        return (ModelVillager) super.getMainModel();
    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(EntityBanker entity) {
        return TEXTURE;
    }

    @Override
    public void preRenderCallback(EntityBanker banker, float partialTickTime) {
        float f = 0.9375F;
        shadowSize = 0.5F;
        GlStateManager.scale(f, f, f);
    }

    public static class RenderBankerFactory implements IRenderFactory<EntityBanker> {
        @Override
        public Render<EntityBanker> createRenderFor(RenderManager manager) {
            return new RenderBanker(manager);
        }
    }
}
