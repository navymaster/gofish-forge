package navy_master.gofish.mixin;

import navy_master.gofish.api.FireproofEntity;
import navy_master.gofish.impl.GoFishLootTables;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;

@Mixin(FishingHook.class)
public abstract class FishingBobberLootMixin extends Entity {

    private FishingBobberLootMixin(EntityType<?> type, ServerLevel world) {
        super(type, world);
    }

    @Redirect(
            method = "retrieve",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/loot/LootDataManager;getLootTable(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/world/level/storage/loot/LootTable;"))
    private LootTable getTable(LootDataManager lootManager, net.minecraft.resources.ResourceLocation id) {
        if (this.level().dimensionType().ultraWarm()) {
            return this.level().getServer().getLootData().getLootTable(GoFishLootTables.NETHER_FISHING);
        } else if (!this.level().dimensionType().bedWorks()) {
            return this.level().getServer().getLootData().getLootTable(GoFishLootTables.END_FISHING);
        }
        return this.level().getServer().getLootData().getLootTable(BuiltInLootTables.FISHING);
    }

    @Inject(
            method = "retrieve",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;setDeltaMovement(DDD)V"),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void setFireproof(ItemStack usedItem, CallbackInfoReturnable<Integer> cir,
                              Player player, int i,
                              net.minecraftforge.event.entity.player.ItemFishedEvent event, // 新增的事件参数
                              net.minecraft.world.level.storage.loot.LootParams lootParams, // 替换LootContext.Builder
                              LootTable lootTable,
                              List<ItemStack> list,
                              Iterator<ItemStack> var7,
                              ItemStack itemStack,
                              ItemEntity itemEntity,
                              double d, double e, double f, double g) { // 保持4个double参数
        if (this.level().dimensionType().ultraWarm()) {
            ((FireproofEntity) itemEntity).gf_setFireproof(true);
        }
    }

}
