package navy_master.gofish;

import navy_master.gofish.registry.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipe;
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


    public GoFish() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        // 注册所有DeferredRegister
        registerDeferredRegistries(modBus);
        modBus.addListener(GoFishItems::addToCreativeTab); // 添加事件监听器
        // 注册通用配置
        modBus.addListener(this::onCommonSetup);

        // 注册燃料事件
        forgeBus.addListener(this::onFuelBurnTime);
    }

    private void registerDeferredRegistries(IEventBus modBus) {
        GoFishEnchantments.ENCHANTMENTS.register(modBus);
        GoFishItems.ITEMS.register(modBus);
        GoFishBlocks.BLOCKS.register(modBus);
        GoFishEntities.BLOCK_ENTITIES.register(modBus);
        GoFishParticles.PARTICLE_TYPES.register(modBus);
        GoFishLoot.REGISTER.register(modBus);
    }

    // 注册标签页
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

    private void onCommonSetup(FMLCommonSetupEvent event) {
        // 酿造配方
        event.enqueueWork(() -> {
            registerBrewingRecipes();
        });
    }

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
                        Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), 
                        Ingredient.of(GoFishItems.CLOUDY_CRAB.get().getDefaultInstance()),
                        PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.SLOW_FALLING)
                )
        );
        BrewingRecipeRegistry.addRecipe(
                new BrewingRecipe(
                        Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),
                        Ingredient.of(GoFishItems.CHARFISH.get().getDefaultInstance()),
                        PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WEAKNESS)
                )
        );
        BrewingRecipeRegistry.addRecipe(
                new BrewingRecipe(
                        Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),
                        Ingredient.of(GoFishItems.RAINY_BASS.get().getDefaultInstance()),
                        PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER_BREATHING)
                )
        );
        BrewingRecipeRegistry.addRecipe(
                new BrewingRecipe(
                        Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),
                        Ingredient.of(GoFishItems.MAGMA_COD.get().getDefaultInstance()),
                        PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.FIRE_RESISTANCE)
                )
        );
    }

}
