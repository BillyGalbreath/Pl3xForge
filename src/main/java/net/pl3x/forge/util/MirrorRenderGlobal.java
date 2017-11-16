package net.pl3x.forge.util;

import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.pl3x.forge.configuration.ClientConfig;

import java.util.List;

public class MirrorRenderGlobal extends RenderGlobal {
    public Minecraft mc;

    public MirrorRenderGlobal(Minecraft mc) {
        super(mc);
        this.mc = mc;
    }

    @Override
    public void renderClouds(float partialTicks, int pass, double x, double y, double z) {
        if (ClientConfig.mirrorOptions.clouds) {
            super.renderClouds(partialTicks, pass, x, y, z);
        }
    }

    @Override
    public void renderEntities(Entity renderViewEntity, ICamera camera, float partialTicks) {
        int pass = net.minecraftforge.client.MinecraftForgeClient.getRenderPass();
        if (renderEntitiesStartupCounter > 0) {
            if (pass > 0) {
                return;
            }
            --renderEntitiesStartupCounter;
        } else {
            double d0 = renderViewEntity.prevPosX + (renderViewEntity.posX - renderViewEntity.prevPosX) * (double) partialTicks;
            double d1 = renderViewEntity.prevPosY + (renderViewEntity.posY - renderViewEntity.prevPosY) * (double) partialTicks;
            double d2 = renderViewEntity.prevPosZ + (renderViewEntity.posZ - renderViewEntity.prevPosZ) * (double) partialTicks;
            this.world.profiler.startSection("prepare");
            TileEntityRendererDispatcher.instance.prepare(world, mc.getTextureManager(), mc.fontRenderer, mc.getRenderViewEntity(), mc.objectMouseOver, partialTicks);
            renderManager.cacheActiveRenderInfo(world, mc.fontRenderer, mc.getRenderViewEntity(), mc.pointedEntity, mc.gameSettings, partialTicks);
            if (pass == 0) {
                this.countEntitiesTotal = 0;
                this.countEntitiesRendered = 0;
                this.countEntitiesHidden = 0;
            }
            Entity entity = mc.getRenderViewEntity();
            double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
            double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
            double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;
            TileEntityRendererDispatcher.staticPlayerX = d3;
            TileEntityRendererDispatcher.staticPlayerY = d4;
            TileEntityRendererDispatcher.staticPlayerZ = d5;
            renderManager.setRenderPosition(d3, d4, d5);
            mc.entityRenderer.enableLightmap();
            world.profiler.endStartSection("global");
            List<Entity> list = world.getLoadedEntityList();
            if (pass == 0) {
                countEntitiesTotal = list.size();
            }

            for (int i = 0; i < world.weatherEffects.size(); ++i) {
                Entity entity1 = world.weatherEffects.get(i);
                if (!entity1.shouldRenderInPass(pass)) {
                    continue;
                }
                ++countEntitiesRendered;

                if (entity1.isInRangeToRender3d(d0, d1, d2)) {
                    renderManager.renderEntityStatic(entity1, partialTicks, false);
                }
            }

            world.profiler.endStartSection("entities");
            List<Entity> list1 = Lists.newArrayList();
            List<Entity> list2 = Lists.newArrayList();
            BlockPos.PooledMutableBlockPos pos = BlockPos.PooledMutableBlockPos.retain();

            for (RenderGlobal.ContainerLocalRenderInformation info : renderInfos) {
                Chunk chunk = world.getChunkFromBlockCoords(info.renderChunk.getPosition());
                ClassInheritanceMultiMap<Entity> map = chunk.getEntityLists()[info.renderChunk.getPosition().getY() / 16];
                if (!map.isEmpty()) {
                    for (Entity entity2 : map) {
                        if (!entity2.shouldRenderInPass(pass)) {
                            continue;
                        }
                        boolean flag = renderManager.shouldRender(entity2, camera, d0, d1, d2) || entity2.isRidingOrBeingRiddenBy(mc.player);
                        if (flag) {
                            boolean flag1 = mc.getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase) mc.getRenderViewEntity()).isPlayerSleeping();
                            if ((entity2 != mc.getRenderViewEntity() || mc.gameSettings.thirdPersonView != 0 || flag1) && (entity2.posY < 0.0D || entity2.posY >= 256.0D || world.isBlockLoaded(pos.setPos(entity2)))) {
                                ++countEntitiesRendered;
                                Entity backup = renderManager.renderViewEntity;
                                renderManager.renderViewEntity = mc.player;
                                renderManager.renderEntityStatic(entity2, partialTicks, false);
                                renderManager.renderViewEntity = backup;
                                if (isOutlineActive(entity2, entity, camera)) {
                                    list1.add(entity2);
                                }
                                if (renderManager.isRenderMultipass(entity2)) {
                                    list2.add(entity2);
                                }
                            }
                        }
                    }
                }
            }

            pos.release();

            if (!list2.isEmpty()) {
                for (Entity entity3 : list2) {
                    renderManager.renderMultipass(entity3, partialTicks);
                }
            }

            if (pass == 0)
                if (isRenderEntityOutlines() && (!list1.isEmpty() || entityOutlinesRendered)) {
                    world.profiler.endStartSection("entityOutlines");
                    entityOutlineFramebuffer.framebufferClear();
                    entityOutlinesRendered = !list1.isEmpty();
                    if (!list1.isEmpty()) {
                        GlStateManager.depthFunc(519);
                        GlStateManager.disableFog();
                        entityOutlineFramebuffer.bindFramebuffer(false);
                        RenderHelper.disableStandardItemLighting();
                        renderManager.setRenderOutlines(true);
                        for (Entity aList1 : list1) {
                            renderManager.renderEntityStatic(aList1, partialTicks, false);
                        }
                        renderManager.setRenderOutlines(false);
                        RenderHelper.enableStandardItemLighting();
                        GlStateManager.depthMask(false);
                        entityOutlineShader.render(partialTicks);
                        GlStateManager.enableLighting();
                        GlStateManager.depthMask(true);
                        GlStateManager.enableFog();
                        GlStateManager.enableBlend();
                        GlStateManager.enableColorMaterial();
                        GlStateManager.depthFunc(515);
                        GlStateManager.enableDepth();
                        GlStateManager.enableAlpha();
                    }
                    mc.getFramebuffer().bindFramebuffer(false);
                }

            world.profiler.endStartSection("blockentities");
            RenderHelper.enableStandardItemLighting();

            TileEntityRendererDispatcher.instance.preDrawBatch();
            for (RenderGlobal.ContainerLocalRenderInformation info : this.renderInfos) {
                List<TileEntity> list3 = info.renderChunk.getCompiledChunk().getTileEntities();
                if (!list3.isEmpty()) {
                    for (TileEntity tileentity2 : list3) {
                        if (!tileentity2.shouldRenderInPass(pass) || !camera.isBoundingBoxInFrustum(tileentity2.getRenderBoundingBox())) {
                            continue;
                        }
                        TileEntityRendererDispatcher.instance.render(tileentity2, partialTicks, -1);
                    }
                }
            }

            synchronized (setTileEntities) {
                for (TileEntity tileentity : setTileEntities) {
                    if (!tileentity.shouldRenderInPass(pass) || !camera.isBoundingBoxInFrustum(tileentity.getRenderBoundingBox())) {
                        continue;
                    }
                    TileEntityRendererDispatcher.instance.render(tileentity, partialTicks, -1);
                }
            }
            TileEntityRendererDispatcher.instance.drawBatch(pass);

            this.preRenderDamagedBlocks();

            for (DestroyBlockProgress destroyblockprogress : damagedBlocks.values()) {
                BlockPos blockpos = destroyblockprogress.getPosition();
                if (world.getBlockState(blockpos).getBlock().hasTileEntity()) {
                    TileEntity tileentity1 = world.getTileEntity(blockpos);
                    if (tileentity1 instanceof TileEntityChest) {
                        TileEntityChest tileentitychest = (TileEntityChest) tileentity1;
                        if (tileentitychest.adjacentChestXNeg != null) {
                            blockpos = blockpos.offset(EnumFacing.WEST);
                            tileentity1 = world.getTileEntity(blockpos);
                        } else if (tileentitychest.adjacentChestZNeg != null) {
                            blockpos = blockpos.offset(EnumFacing.NORTH);
                            tileentity1 = world.getTileEntity(blockpos);
                        }
                    }
                    IBlockState iblockstate = world.getBlockState(blockpos);
                    if (tileentity1 != null && iblockstate.hasCustomBreakingProgress()) {
                        TileEntityRendererDispatcher.instance.render(tileentity1, partialTicks, destroyblockprogress.getPartialBlockDamage());
                    }
                }
            }

            postRenderDamagedBlocks();
            mc.entityRenderer.disableLightmap();
            mc.mcProfiler.endSection();
        }
    }
}
