package navy_master.gofish.mixin;


import navy_master.gofish.api.FireproofEntity;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// 为特定型号钓竿提供不会着火
@Mixin(FishingHook.class)
public abstract class FishingBobberFireproofMixin extends Entity implements FireproofEntity {

    @Unique
    private static final EntityDataAccessor<Boolean> GF_FIRE_IMMUNE = SynchedEntityData.defineId(FishingHook.class, EntityDataSerializers.BOOLEAN);

    private FishingBobberFireproofMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(
            method = "defineSynchedData",
            at = @At("RETURN"))
    private void registerFireImmuneTracker(CallbackInfo ci) {
        entityData.define(GF_FIRE_IMMUNE, false);
    }

    @Override
    public boolean isOnFire() {
        if(entityData.get(GF_FIRE_IMMUNE)) {
            return false;
        }

        return super.isOnFire();
    }

    @Override
    public boolean gf_isFireproof() {
        return entityData.get(GF_FIRE_IMMUNE);
    }

    @Override
    public void gf_setFireproof(boolean value) {
        entityData.set(GF_FIRE_IMMUNE, value);
    }
}
