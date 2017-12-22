package net.pl3x.forge.entity.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.entity.EntityPanda;
import net.pl3x.forge.entity.model.ModelPanda;

@SideOnly(Side.CLIENT)
public class RenderPanda extends RenderLiving<EntityPanda> {
    private static final ResourceLocation PANDA_TEXTURE = new ResourceLocation(Pl3x.modId, "textures/entity/bear/panda.png");

    public RenderPanda(RenderManager renderManager) {
        super(renderManager, new ModelPanda(), 0.7F);
    }

    protected ResourceLocation getEntityTexture(EntityPanda entity) {
        return PANDA_TEXTURE;
    }

    public void doRender(EntityPanda entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    protected void preRenderCallback(EntityPanda entity, float partialTickTime) {
        GlStateManager.scale(1.2F, 1.2F, 1.2F);
        super.preRenderCallback(entity, partialTickTime);
    }
}
