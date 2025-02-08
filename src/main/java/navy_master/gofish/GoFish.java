package navy_master.gofish;

//import navy_master.gofish.command.FishCommand;
import navy_master.gofish.registry.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(GoFish.MOD_ID)
public class GoFish {

    public static final String MOD_ID = "gofish";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    // 物品组注册
    /*public static final ResourceKey<CreativeModeTab> ITEM_GROUP = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB,
            id("group")
    );*/

    public GoFish() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        // 注册所有DeferredRegister
        registerDeferredRegistries(modBus);
        modBus.addListener(GoFishItems::addToCreativeTab); // 添加事件监听器
        // 注册通用配置
        //modBus.addListener(this::onCommonSetup);

        // 注册命令
        //forgeBus.addListener(this::onCommandRegister);

        // 注册燃料事件
        forgeBus.addListener(this::onFuelBurnTime);
    }

    private void registerDeferredRegistries(IEventBus modBus) {
        // 初始化所有注册类（需要提前修改为使用DeferredRegister）

        GoFishEnchantments.ENCHANTMENTS.register(modBus);
        GoFishItems.ITEMS.register(modBus);
        GoFishBlocks.BLOCKS.register(modBus);
        //GoFishEntities.ENTITIES.register(modBus);
        //GoFishParticles.PARTICLES.register(modBus);
    }

    // 注册标签页（替换原 ITEM_GROUP 声明）
    public static final RegistryObject<CreativeModeTab> ITEM_GROUP = TABS.register("group",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(GoFishItems.GOLDEN_FISH.get()))
                    .title(Component.translatable("itemGroup.gofish.group"))
                    .displayItems((params, output) -> {
                        // 自动添加所有已注册物品
                        GoFishItems.ITEMS.getEntries().forEach(item -> output.accept(item.get()));
                        GoFishBlocks.BLOCKS.getEntries().forEach(block -> output.accept(block.get()));
                    })
                    .build()
    );

    /*private void onCommonSetup(FMLCommonSetupEvent event) {
        // 酿造配方需要在同步线程执行
        event.enqueueWork(() -> {
            registerBrewingRecipes();
            GoFishLoot.init();
            GoFishLootHandler.init();
        });
    }

    /*private void onCommandRegister(RegisterCommandsEvent event) {
        FishCommand.register(event.getDispatcher());
    }*/

    private void onFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.is(GoFishItems.OAKFISH.get())) event.setBurnTime(300);
        if (stack.is(GoFishItems.CHARFISH.get())) event.setBurnTime(1600);
    }

    public static ResourceLocation id(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    public static void registerBrewingRecipes() {
        BrewingRecipeRegistry.addRecipe(
                new BrewingRecipe(
                        Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), // 输入药水
                        Ingredient.of(GoFishItems.CLOUDY_CRAB.get().getDefaultInstance()),                  // 酿造材料
                        PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.SLOW_FALLING) // 输出药水
                )
        );
        BrewingRecipeRegistry.addRecipe(
                new BrewingRecipe(
                        Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), // 输入药水
                        Ingredient.of(GoFishItems.CHARFISH.get().getDefaultInstance()),                  // 酿造材料
                        PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WEAKNESS) // 输出药水
                )
        );
        BrewingRecipeRegistry.addRecipe(
                new BrewingRecipe(
                        Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), // 输入药水
                        Ingredient.of(GoFishItems.RAINY_BASS.get().getDefaultInstance()),                  // 酿造材料
                        PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER_BREATHING) // 输出药水
                )
        );
        BrewingRecipeRegistry.addRecipe(
                new BrewingRecipe(
                        Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), // 输入药水
                        Ingredient.of(GoFishItems.MAGMA_COD.get().getDefaultInstance()),                  // 酿造材料
                        PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.FIRE_RESISTANCE) // 输出药水
                )
        );
    }

}
