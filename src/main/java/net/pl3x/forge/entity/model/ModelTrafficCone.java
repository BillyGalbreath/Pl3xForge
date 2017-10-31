package net.pl3x.forge.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTrafficCone extends ModelBase {
    ModelRenderer base;
    ModelRenderer bottom;
    ModelRenderer middle;
    ModelRenderer top;

    public ModelTrafficCone() {
        base = new ModelRenderer(this, 0, 0);
        base.addBox(-5F, 23F, -5F, 10, 1, 10);
        bottom = new ModelRenderer(this, 0, 11);
        bottom.addBox(-3F, 19F, -3F, 6, 4, 6);
        middle = new ModelRenderer(this, 24, 11);
        middle.addBox(-2F, 15F, -2F, 4, 4, 4);
        top = new ModelRenderer(this, 40, 11);
        top.addBox(-1F, 11F, -1F, 2, 4, 2);

        base.addChild(bottom);
        base.addChild(middle);
        base.addChild(top);
    }

    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        base.render(scale);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
