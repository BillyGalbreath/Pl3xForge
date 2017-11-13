package net.pl3x.forge.entity.render;

import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.entity.EntityChairSeat;

@SideOnly(Side.CLIENT)
public class RenderChair extends Render<EntityChairSeat> {
    public RenderChair(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityChairSeat entity) {
        return new ResourceLocation(Pl3x.modId, "textures/entity/chair_seat.png");
    }

    @Override
    public void setRenderOutlines(boolean renderOutlinesIn) {
        //
    }

    @Override
    protected int getTeamColor(EntityChairSeat entityIn) {
        return 16777215;
    }

    @Override
    public boolean shouldRender(EntityChairSeat livingEntity, ICamera camera, double camX, double camY, double camZ) {
        return false;
    }

    @Override
    public void doRender(EntityChairSeat entity, double x, double y, double z, float entityYaw, float partialTicks) {
        //
    }

    @Override
    public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {
        //
    }

    @Override
    protected boolean canRenderName(EntityChairSeat entity) {
        return false;
    }

    @Override
    protected void renderName(EntityChairSeat entity, double x, double y, double z) {
        //
    }

    @Override
    protected void renderLivingLabel(EntityChairSeat entityIn, String str, double x, double y, double z, int maxDistance) {
        //
    }
}
