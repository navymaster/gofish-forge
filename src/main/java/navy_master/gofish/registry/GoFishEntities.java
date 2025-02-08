package navy_master.gofish.registry;


import navy_master.gofish.GoFish;
import navy_master.gofish.block.AstralCrateBlock;
import navy_master.gofish.entity.block.AstralCrateBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GoFishEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, GoFish.MOD_ID);

    public static final RegistryObject<BlockEntityType<AstralCrateBlockEntity>> ASTRAL_CRATE =
            BLOCK_ENTITIES.register("astral_crate", () ->
                    BlockEntityType.Builder.of(
                            AstralCrateBlockEntity::new,
                            GoFishBlocks.ASTRAL_CRATE.get()
                    ).build(null)
            );

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
