package net.pl3x.forge.entity.ai;

import com.google.common.base.Predicate;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNodeType;
import net.pl3x.forge.entity.EntityTrafficCone;
import net.pl3x.forge.item.ModItems;

import java.util.List;

public class EntityAIFollowConeHead extends EntityAIBase {
    private final EntityTrafficCone trafficCone;
    private final Predicate<EntityPlayer> followPredicate;
    private EntityPlayer followingEntity;
    private final double followSpeed;
    private final PathNavigate navigation;
    private int timeToRecalcPath;
    private final float stopDistance;
    private float oldWaterCost;
    private final float areaSize;

    public EntityAIFollowConeHead(EntityTrafficCone trafficCone, double followSpeed, float stopDistance, float areaSize) {
        this.trafficCone = trafficCone;
        this.followSpeed = followSpeed;
        this.stopDistance = stopDistance;
        this.areaSize = areaSize;
        followPredicate = player -> player != null && player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == ModItems.TRAFFIC_CONE;
        navigation = trafficCone.getNavigator();
        setMutexBits(3);

        if (!(trafficCone.getNavigator() instanceof PathNavigateGround) && !(trafficCone.getNavigator() instanceof PathNavigateFlying)) {
            throw new IllegalArgumentException("Unsupported entity type for FollowConeheadGoal");
        }
    }

    public boolean shouldExecute() {
        List<EntityPlayer> list = trafficCone.world.getEntitiesWithinAABB(EntityPlayer.class,
                trafficCone.getEntityBoundingBox().grow((double) areaSize), followPredicate);
        if (!list.isEmpty()) {
            for (EntityPlayer player : list) {
                if (!player.isInvisible() && !player.isSpectator()) {
                    followingEntity = player;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean shouldContinueExecuting() {
        return followingEntity != null && !navigation.noPath() && trafficCone.getDistanceSq(followingEntity) > (double) (stopDistance * stopDistance);
    }

    public void startExecuting() {
        timeToRecalcPath = 0;
        oldWaterCost = trafficCone.getPathPriority(PathNodeType.WATER);
        trafficCone.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    public void resetTask() {
        followingEntity = null;
        navigation.clearPath();
        trafficCone.setPathPriority(PathNodeType.WATER, oldWaterCost);
    }

    public void updateTask() {
        if (followingEntity != null && !trafficCone.getLeashed()) {
            trafficCone.getLookHelper().setLookPositionWithEntity(followingEntity, 10.0F, (float) trafficCone.getVerticalFaceSpeed());

            if (--timeToRecalcPath <= 0) {
                timeToRecalcPath = 10;
                double d0 = trafficCone.posX - followingEntity.posX;
                double d1 = trafficCone.posY - followingEntity.posY;
                double d2 = trafficCone.posZ - followingEntity.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                if (d3 > (double) (stopDistance * stopDistance)) {
                    navigation.tryMoveToEntityLiving(followingEntity, followSpeed);
                } else {
                    navigation.clearPath();
                    //EntityLookHelper entitylookhelper = followingEntity.getLookHelper();

                    if (d3 <= (double) stopDistance) {// || entitylookhelper.getLookPosX() == trafficCone.posX &&
                        //entitylookhelper.getLookPosY() == trafficCone.posY &&
                        //entitylookhelper.getLookPosZ() == trafficCone.posZ) {
                        double d4 = followingEntity.posX - trafficCone.posX;
                        double d5 = followingEntity.posZ - trafficCone.posZ;
                        navigation.tryMoveToXYZ(trafficCone.posX - d4, trafficCone.posY, trafficCone.posZ - d5, followSpeed);
                    }
                }
            }
        }
    }
}
