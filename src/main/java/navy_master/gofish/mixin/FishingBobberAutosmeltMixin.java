package navy_master.gofish.mixin;


import navy_master.gofish.api.FireproofEntity;
import navy_master.gofish.api.SmeltingBobber;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

// 钓鱼时自动熔炼附魔类
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


    @Redirect(
            method = "retrieve",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/entity/item/ItemEntity"
            )
    )
    private ItemEntity createFireproofItemEntity(Level level, double x, double y, double z, ItemStack stack) {
        ItemEntity itemEntity = new ItemEntity(level, x, y, z, stack);
        // 注入检测到钓竿具有深度烧烤时获取配方进行熔炼
        if(gf_smelts) {
            // 修改点 1: 直接使用 SmeltingRecipe 而不是 RecipeHolder<SmeltingRecipe>
            Optional<SmeltingRecipe> cooked = level().getRecipeManager().getRecipeFor(
                    RecipeType.SMELTING,
                    new SimpleContainer(itemEntity.getItem()),
                    level()
            );

            // 修改点 2: 直接访问 SmeltingRecipe 而不是通过 .value()
            cooked.ifPresent(smeltingRecipe ->
                    itemEntity.setItem(smeltingRecipe.getResultItem(level().registryAccess()))
            );
        }
        if (level.dimensionType().ultraWarm()) {
            ((FireproofEntity) itemEntity).gf_setFireproof(true);
        }

        return itemEntity;
    }
}
