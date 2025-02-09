package navy_master.gofish.mixin;

import navy_master.gofish.item.ExtendedFishingRodItem;
import navy_master.gofish.registry.GoFishParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.tags.TagKey;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FishingHook.class)
public abstract class FishingBobberLavaFishingMixin extends Entity {

    @Shadow public abstract Player getPlayerOwner();
    @Shadow public abstract void remove(Entity.RemovalReason reason);

    private FishingBobberLavaFishingMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @ModifyVariable(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z", ordinal = 0),
            index = 2
    )
    private float bobberInLava(float value) {
        BlockPos blockPos = this.blockPosition();
        FluidState fluidState = this.level().getFluidState(blockPos);

        if (!fluidState.is(FluidTags.LAVA)) {
            return value;
        }

        Item mainHandItem = getPlayerOwner().getMainHandItem().getItem();
        Item offHandItem = getPlayerOwner().getOffhandItem().getItem();

        if (mainHandItem instanceof ExtendedFishingRodItem) {
            ExtendedFishingRodItem usedRod = (ExtendedFishingRodItem) mainHandItem;
            if (usedRod.canFishInLava()) {
                return fluidState.getHeight(this.level(), blockPos);
            }
        } else if (offHandItem instanceof ExtendedFishingRodItem) {
            ExtendedFishingRodItem usedRod = (ExtendedFishingRodItem) offHandItem;
            if (usedRod.canFishInLava()) {
                return fluidState.getHeight(this.level(), blockPos);
            }
        }

        if (!getPlayerOwner().isCreative()) {
            getPlayerOwner().getItemInHand(InteractionHand.MAIN_HAND).hurtAndBreak(5, getPlayerOwner(),
                    player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }

        if (level() instanceof ServerLevel) {
            ((ServerLevel) level()).sendParticles(ParticleTypes.LAVA, getX(), getY(), getZ(), 5, 0, 1, 0, 0);
        }

        getPlayerOwner().playSound(SoundEvents.GENERIC_BURN, .5f, 1f);
        remove(RemovalReason.KILLED);

        return value;
    }

    @Redirect(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z", ordinal = 1)
    )
    private boolean fallOutsideLiquid(FluidState fluid, TagKey<Fluid> tag) {
        return !fluid.isEmpty();
    }
/*
    @Inject(
            method = "catchingFish",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z", ordinal = 0),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void fishingLavaParticles(BlockPos pos, CallbackInfo ci, ServerLevel serverWorld, int i, float f, float g, float h, double d, double e, double j, BlockState blockState) {
        if (!blockState.is(Blocks.LAVA)) {
            return;
        }
        if (this.random.nextFloat() < 0.15F) {
            serverWorld.sendParticles(ParticleTypes.LAVA, d, e - 0.1D, j, 1, g, 0.1D, h, 0.0D);
        }

        float dZ = f * 0.04F;
        float dX = h * 0.04F;
        serverWorld.sendParticles(GoFishParticles.LAVA_FISHING.get(), d, e, j, 0, dX, 0.01D, (-dZ), 1.0D);
        serverWorld.sendParticles(GoFishParticles.LAVA_FISHING.get(), d, e, j, 0, (-dX), 0.01D, dZ, 1.0D);
    }

    @Inject(
            method = "catchingFish",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z", ordinal = 1),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void fishSecondaryLavaParticles(BlockPos pos, CallbackInfo ci, ServerLevel serverWorld, int i, float f, float g, float h, double d, double e, double j, BlockState blockState) {
        if (blockState.is(Blocks.LAVA)) {
            serverWorld.sendParticles(ParticleTypes.LAVA, pos.getX(), pos.getY(), pos.getZ(), 2 + this.random.nextInt(2), 0.10000000149011612D, 0.0D, 0.10000000149011612D, 0.0D);
        }
    }
*/
/*
    @Redirect(
            method = "getOpenWaterTypeForArea(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/entity/projectile/FishingHook$OpenWaterType;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z"
            )
    )
    private boolean isInValidLiquid(FluidState fluidState, TagKey<Fluid> tag) {
        // 保持原始逻辑：当检查水标签时保留原逻辑，其他情况返回流体是否非空
        if (tag == FluidTags.WATER) {
            return fluidState.is(tag); // 保留原版水检查逻辑
        }
        return !fluidState.isEmpty(); // 非水检查时返回流体存在性
    }
*/
}
