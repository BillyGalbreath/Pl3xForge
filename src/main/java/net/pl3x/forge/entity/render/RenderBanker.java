package net.pl3x.forge.entity.render;

import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.entity.EntityBanker;

@SideOnly(Side.CLIENT)
public class RenderBanker extends RenderLiving<EntityBanker> {
    public RenderBanker(RenderManager renderManager) {
        super(renderManager, new ModelVillager(0), 0.5F);
        this.addLayer(new LayerCustomHead(this.getMainModel().villagerHead));
    }

    @Override
    public ModelVillager getMainModel() {
        return (ModelVillager) super.getMainModel();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBanker entity) {
        return new ResourceLocation(Pl3x.modId, "textures/entity/banker.png");
    }
}
