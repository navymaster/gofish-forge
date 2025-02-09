package navy_master.gofish.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.advancements.critereon.FishingHookPredicate;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingHookPredicate.class)
public class FishingHookPredicateMixin {

    @Inject(
            method = "matches",
            at = @At("HEAD"),
            cancellable = true
    )
    private void overrideCreativePredicate(Entity entity, ServerLevel p_219717_, Vec3 p_219718_, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof ServerPlayer player) {
            if (player.isCreative() && player.hasPermissions(2)) {
                cir.setReturnValue(true);
            }
        }
    }
}
