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
    // 修正后的第一个注入点（注意参数顺序变化）
    @Inject(
            method = "catchingFish",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z",
                    ordinal = 0
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void fishingLavaParticles(
            BlockPos pos,
            CallbackInfo ci,
            ServerLevel serverLevel,
            int waitTime,        // 第一个int参数
            BlockPos checkedPos, // 实际是BlockPos而非第二个int参数
            float deltaX,
            float deltaY,
            float deltaZ,
            double x,
            double y,
            double z,
            BlockState state
    ) {
        if (!state.is(Blocks.LAVA)) return;

        if (this.random.nextFloat() < 0.15F) {
            serverLevel.sendParticles(ParticleTypes.LAVA, x, y - 0.1D, z, 1, deltaX, 0.1D, deltaZ, 0.0D);
        }

        float dZ = deltaX * 0.04F;
        float dX = deltaZ * 0.04F;
        serverLevel.sendParticles(GoFishParticles.LAVA_FISHING.get(), x, y, z, 0, dX, 0.01D, -dZ, 1.0D);
        serverLevel.sendParticles(GoFishParticles.LAVA_FISHING.get(), x, y, z, 0, -dX, 0.01D, dZ, 1.0D);
    }

    // 修正后的第二个注入点
    @Inject(
            method = "catchingFish",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z",
                    ordinal = 1
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void fishSecondaryLavaParticles(
            BlockPos pos,
            CallbackInfo ci,
            ServerLevel serverLevel,
            int waitTime,
            BlockPos checkedPos,
            float deltaX,
            float deltaY,
            float deltaZ,
            double x,
            double y,
            double z,
            BlockState state
    ) {
        if (state.is(Blocks.LAVA)) {
            serverLevel.sendParticles(ParticleTypes.LAVA, checkedPos.getX(), checkedPos.getY(), checkedPos.getZ(), 2 + this.random.nextInt(2), 0.1D, 0.0D, 0.1D, 0.0D);
        }
    }

    // 修正后的重定向点（使用SRG名确保匹配）
    @Redirect(
            method = "getOpenWaterTypeForBlock", // 实际检查单个方块的方法
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z"
            )
    )
    private boolean isInValidLiquid(FluidState fluidState, TagKey<Fluid> tag) {
        return !fluidState.isEmpty();
    }
}
