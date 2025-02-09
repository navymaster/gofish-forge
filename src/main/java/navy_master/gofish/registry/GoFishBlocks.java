package navy_master.gofish.registry;

import navy_master.gofish.GoFish;
import navy_master.gofish.block.AstralCrateBlock;
import navy_master.gofish.block.CrateBlock;
import navy_master.gofish.item.CrateItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class GoFishBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, GoFish.MOD_ID);

    // 注册战利品箱
    public static final RegistryObject<Block> WOODEN_CRATE = registerCrate("wooden_crate",
            () -> new CrateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)),
            props -> props.stacksTo(8),
            new ResourceLocation(GoFish.MOD_ID, "gameplay/fishing/wooden_crate"));

    public static final RegistryObject<Block> IRON_CRATE = registerCrate("iron_crate",
            () -> new CrateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)),
            props -> props.stacksTo(8),
            new ResourceLocation(GoFish.MOD_ID, "gameplay/fishing/iron_crate"));

    public static final RegistryObject<Block> GOLDEN_CRATE = registerCrate("golden_crate",
            () -> new CrateBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)),
            props -> props.stacksTo(8).rarity(Rarity.UNCOMMON),
            new ResourceLocation(GoFish.MOD_ID, "gameplay/fishing/golden_crate"));

    public static final RegistryObject<Block> DIAMOND_CRATE = registerCrate("diamond_crate",
            () -> new CrateBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK)),
            props -> props.stacksTo(8).rarity(Rarity.RARE),
            new ResourceLocation(GoFish.MOD_ID, "gameplay/fishing/diamond_crate"));

    public static final RegistryObject<Block> FROSTED_CRATE = registerCrate("frosted_crate",
            () -> new CrateBlock(BlockBehaviour.Properties.copy(Blocks.BLUE_ICE)),
            props -> props.stacksTo(8).rarity(Rarity.RARE),
            new ResourceLocation(GoFish.MOD_ID, "gameplay/fishing/frosted_crate"));

    public static final RegistryObject<Block> SLIMEY_CRATE = registerCrate("slimey_crate",
            () -> new CrateBlock(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK)),
            props -> props.stacksTo(8),
            new ResourceLocation(GoFish.MOD_ID, "gameplay/fishing/slimey_crate"));

    public static final RegistryObject<Block> SUPPLY_CRATE = registerCrate("supply_crate",
            () -> new CrateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)),
            props -> props.stacksTo(8),
            new ResourceLocation(GoFish.MOD_ID, "gameplay/fishing/supply_crate"));

    public static final RegistryObject<Block> FIERY_CRATE = registerCrate("fiery_crate",
            () -> new CrateBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)),
            props -> props.stacksTo(8).fireResistant(),
            new ResourceLocation(GoFish.MOD_ID, "gameplay/fishing/fiery_crate"));

    public static final RegistryObject<Block> SOUL_CRATE = registerCrate("soul_crate",
            () -> new CrateBlock(BlockBehaviour.Properties.copy(Blocks.STONE)),
            props -> props.stacksTo(8).fireResistant().rarity(Rarity.RARE),
            new ResourceLocation(GoFish.MOD_ID, "gameplay/fishing/soul_crate"));

    public static final RegistryObject<Block> GILDED_BLACKSTONE_CRATE = registerCrate("gilded_blackstone_crate",
            () -> new CrateBlock(BlockBehaviour.Properties.copy(Blocks.GILDED_BLACKSTONE)),
            props -> props.stacksTo(8).fireResistant().rarity(Rarity.UNCOMMON),
            new ResourceLocation(GoFish.MOD_ID, "gameplay/fishing/gilded_blackstone_crate"));

    public static final RegistryObject<Block> END_CRATE = registerCrate("end_crate",
            () -> new AstralCrateBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE)),
            props -> props.stacksTo(8).fireResistant().rarity(Rarity.EPIC),
            new ResourceLocation(GoFish.MOD_ID, "gameplay/fishing/end_crate"));

    //同时注册方块和物品
    private static RegistryObject<Block> registerCrate(String name, Supplier<Block> blockSupplier,
                                                       UnaryOperator<Item.Properties> itemProps,
                                                       ResourceLocation lootTableId) {
        RegistryObject<Block> blockReg = BLOCKS.register(name, blockSupplier);
        GoFishItems.ITEMS.register(name, () -> new CrateItem(blockReg.get(), itemProps.apply(new Item.Properties()), lootTableId));
        return blockReg;
    }

    //星体箱需要额外做渲染，所以单独提出来注册
    public static final RegistryObject<Block> ASTRAL_CRATE = BLOCKS.register("astral_crate",
            () -> new AstralCrateBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE).noOcclusion()));
}
