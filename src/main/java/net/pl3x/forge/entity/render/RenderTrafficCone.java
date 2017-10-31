package net.pl3x.forge.entity.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.entity.EntityTrafficCone;
import net.pl3x.forge.entity.model.ModelTrafficCone;

@SideOnly(Side.CLIENT)
public class RenderTrafficCone extends RenderLiving<EntityTrafficCone> {
    public RenderTrafficCone(RenderManager renderManager) {
        super(renderManager, new ModelTrafficCone(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityTrafficCone entity) {
        return new ResourceLocation(Pl3x.modId, "textures/entity/traffic_cone.png");
    }
}
