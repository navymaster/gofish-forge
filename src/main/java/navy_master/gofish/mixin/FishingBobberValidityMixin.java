package navy_master.gofish.mixin;

import navy_master.gofish.item.ExtendedFishingRodItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingHook.class)
public abstract class FishingBobberValidityMixin extends Entity {

    @Shadow public abstract Player getPlayerOwner();

    private FishingBobberValidityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(
            method = "shouldStopFishing",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onShouldStopFishing(Player player, CallbackInfoReturnable<Boolean> cir) {
        ItemStack mainHandStack = player.getMainHandItem();
        ItemStack offHandStack = player.getOffhandItem();

        boolean mainHandHasRod = mainHandStack.getItem() instanceof ExtendedFishingRodItem;
        boolean offHandHasRod = offHandStack.getItem() instanceof ExtendedFishingRodItem;

        if (!player.isRemoved() &&
                player.isAlive() &&
                (mainHandHasRod || offHandHasRod) &&
                this.distanceToSqr(player) <= 1024.0D) {
            cir.setReturnValue(false);
        }
    }
}
