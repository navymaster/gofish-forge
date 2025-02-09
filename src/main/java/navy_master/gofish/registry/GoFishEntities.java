package navy_master.gofish.registry;


import navy_master.gofish.GoFish;
import navy_master.gofish.entity.block.AstralCrateBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GoFishEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, GoFish.MOD_ID);

    //星体箱的方块实体，用于渲染
    public static final RegistryObject<BlockEntityType<AstralCrateBlockEntity>> ASTRAL_CRATE =
            BLOCK_ENTITIES.register("astral_crate", () ->
                    BlockEntityType.Builder.of(
                            AstralCrateBlockEntity::new,
                            GoFishBlocks.ASTRAL_CRATE.get()
                    ).build(null)
            );
}
