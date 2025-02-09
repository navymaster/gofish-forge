package navy_master.gofish.mixin;


import navy_master.gofish.api.SmeltingBobber;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Optional;

@Mixin(FishingHook.class)
public abstract class FishingBobberAutosmeltMixin extends Entity implements SmeltingBobber {

    private FishingBobberAutosmeltMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Unique
    private boolean gf_smelts = false;

    @Override
    public boolean gf_canSmelt() {
        return gf_smelts;
    }

    @Override
    public void gf_setSmelts(boolean value) {
        this.gf_smelts = value;
    }

    @ModifyVariable(
            method = "retrieve",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/item/ItemEntity;setDeltaMovement(DDD)V",
                    shift = At.Shift.AFTER
            ),
            ordinal=0
    )
    private ItemEntity processOutput(ItemEntity itemEntity) {
        if(gf_smelts) {
            Optional<RecipeHolder<SmeltingRecipe>> cooked = level().getRecipeManager().getRecipeFor(
                    RecipeType.SMELTING,
                    new SimpleContainer(itemEntity.getItem()),
                    level()
            );

            cooked.ifPresent(smeltingRecipe -> itemEntity.setItem(smeltingRecipe.value().getResultItem(level().registryAccess())));
        }

        return itemEntity;
    }
}
