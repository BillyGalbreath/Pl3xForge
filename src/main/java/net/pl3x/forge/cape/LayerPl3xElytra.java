package net.pl3x.forge.cape;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelElytra;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerPl3xElytra implements LayerRenderer<AbstractClientPlayer> {
    private final RenderPlayer renderPlayer;
    private final ModelElytra modelElytra = new ModelElytra();
    private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("textures/entity/elytra.png");

    public LayerPl3xElytra(RenderPlayer renderPlayerIn) {
        this.renderPlayer = renderPlayerIn;
    }

    public void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        ItemStack itemstack = entitylivingbaseIn.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        if (!itemstack.isEmpty() && (itemstack.getItem() == Items.ELYTRA)) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();

            Object[] cobj = CapeManager.getCape(entitylivingbaseIn.getDisplayNameString());
            if ((cobj == null) || (cobj.length != 2)) {
                return;
            }
            CapeManager.CapeData cdata = (CapeManager.CapeData) cobj[1];
            if ((cdata != null) && (cdata.getCapeLocation() != null)) {
                this.renderPlayer.bindTexture(cdata.getCapeLocation());
            } else {
                this.renderPlayer.bindTexture(TEXTURE_ELYTRA);
            }
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 0.0F, 0.125F);
            this.modelElytra.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entitylivingbaseIn);

            this.modelElytra.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            if (itemstack.isItemEnchanted()) {
                LayerArmorBase.renderEnchantedGlint(this.renderPlayer, entitylivingbaseIn, this.modelElytra, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            }
            GlStateManager.popMatrix();
        }
    }

    public boolean shouldCombineTextures() {
        return false;
    }
}
