package navy_master.gofish.mixin;

import navy_master.gofish.impl.GoFishLootTables;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


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

}
