package net.pl3x.forge.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.entity.model.ModelGoCart;

public class EntityGoCart extends EntityVehicle {
    public static final ModelGoCart MODEL = new ModelGoCart();
    public static final ResourceLocation TEXTURE = new ResourceLocation(Pl3x.modId, "textures/entity/vehicle/go_cart.png");

    public EntityGoCart(World worldIn) {
        super(worldIn);
        preventEntitySpawning = true;
        setSize(1.32F, 0.6F);
    }

    public EntityGoCart(World worldIn, double x, double y, double z) {
        this(worldIn);
        setPosition(x, y, z);
        motionX = 0.0D;
        motionY = 0.0D;
        motionZ = 0.0D;
        prevPosX = x;
        prevPosY = y;
        prevPosZ = z;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ModelBase getModel() {
        return MODEL;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderCar(double x, double y, double z, float entityYaw, float partialTicks) {
        getModel().render(this, partialTicks, 0, -0.1F, 0, 0, 0.0625F);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        // TODO
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        // TODO
    }
}
