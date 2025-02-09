package navy_master.gofish.mixin;

import navy_master.gofish.api.FireproofEntity;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityFireproofMixin extends Entity implements FireproofEntity {

    @Unique
    private static final EntityDataAccessor<Boolean> GF_FIRE_IMMUNE = SynchedEntityData.defineId(ItemEntity.class, EntityDataSerializers.BOOLEAN);

    public ItemEntityFireproofMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(
            method = "defineSynchedData",
            at = @At("RETURN")
    )
    private void registerFireImmuneTracker(CallbackInfo ci) { // 无builder参数
        this.entityData.define(GF_FIRE_IMMUNE, false); // 直接访问实体数据
    }

    @Inject(
            method = "fireImmune",
            at = @At("HEAD"),
            cancellable = true
    )
    private void isLavaFishingLoot(CallbackInfoReturnable<Boolean> cir) {
        if (this.entityData.get(GF_FIRE_IMMUNE)) {
            cir.setReturnValue(true);
        }
    }
    @Override
    public boolean isOnFire() {
        if(gf_isFireproof()) {
            return false;
        }

        return super.isOnFire();
    }

    @Override
    public boolean gf_isFireproof() {
        return this.entityData.get(GF_FIRE_IMMUNE);
    }

    @Override
    public void gf_setFireproof(boolean value) {
        this.entityData.set(GF_FIRE_IMMUNE, value);
    }
}
