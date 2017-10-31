package net.pl3x.forge.entity.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.entity.EntityPenguin;
import net.pl3x.forge.entity.model.ModelPenguin;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class RenderPenguin extends RenderLiving<EntityPenguin> {
    public RenderPenguin(RenderManager renderManager) {
        super(renderManager, new ModelPenguin(), 0.5F);
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityPenguin penguin) {
        String name = penguin.getName().toLowerCase().trim();
        if (name.equals("joshie") || name.equals("joshiejack")) {
            return this.getPenguinTexture("joshie");
        } else if (name.equals("darkosto")) {
            return this.getPenguinTexture("darkosto");
        }
        return penguin.isChild() ? this.getPenguinTexture("penguin_child") : this.getPenguinTexture("penguin");
    }

    private ResourceLocation getPenguinTexture(String fileName) {
        return new ResourceLocation(Pl3x.modId, "textures/entity/penguin/" + fileName + ".png");
    }
}
