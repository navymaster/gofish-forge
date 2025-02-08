package navy_master.gofish.registry;

import navy_master.gofish.GoFish;
import navy_master.gofish.api.SoundInstance;
import navy_master.gofish.item.*;
import net.minecraft.ChatFormatting;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GoFishItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GoFish.MOD_ID);

    // Fishing Rods
    public static final RegistryObject<Item> BLAZE_ROD = ITEMS.register("blaze_rod", ()->new   ExtendedFishingRodItem.Builder()
            .durability(125)
            .autosmelt()
            .lavaProof(true)
            .build());

    public static final RegistryObject<Item> SKELETAL_ROD = ITEMS.register("skeletal_rod", ()->new  ExtendedFishingRodItem.Builder()
            .durability(75)
            .withCastSound(new SoundInstance(SoundEvents.SKELETON_STEP, 1.0F, SoundInstance.DEFAULT_PITCH))
            .withRetrieveSound(new SoundInstance(SoundEvents.SKELETON_STEP, 0.5F, SoundInstance.DEFAULT_PITCH))
            .lavaProof(true)
            .build());

    public static final RegistryObject<Item> DIAMOND_REINFORCED_ROD = ITEMS.register("diamond_reinforced_rod", ()->new ExtendedFishingRodItem.Builder()
            .durability(300)
            .color(ChatFormatting.AQUA)
            .lavaProof(true)
            .build());

    public static final RegistryObject<Item> EYE_OF_FISHING = ITEMS.register("ender_rod", ()->new ExtendedFishingRodItem.Builder()
            .durability(250)
            .color(ChatFormatting.LIGHT_PURPLE)
            .build());

    public static final RegistryObject<Item> FROSTED_ROD = ITEMS.register("frosted_rod", ()->new ExtendedFishingRodItem.Builder()
            .durability(150)
            .build());

    public static final RegistryObject<Item> SLIME_ROD = ITEMS.register("slime_rod", ()->new ExtendedFishingRodItem.Builder()
            .durability(150)
            .color(ChatFormatting.GREEN)
            .withCastSound(new SoundInstance(SoundEvents.SLIME_JUMP, 0.8F, SoundInstance.DEFAULT_PITCH))
            .withRetrieveSound(new SoundInstance(SoundEvents.SLIME_JUMP, 0.5F, SoundInstance.DEFAULT_PITCH))
            .build());

    public static final RegistryObject<Item> SOUL_ROD = ITEMS.register("soul_rod", ()->new ExtendedFishingRodItem.Builder()
            .durability(250)
            .color(ChatFormatting.LIGHT_PURPLE)
            .baseExperienceGain(5)
            .lavaProof(true)
            .build());

    public static final RegistryObject<Item> MATRIX_ROD = ITEMS.register("matrix_rod", ()->new ExtendedFishingRodItem.Builder()
            .durability(200)
            .color(ChatFormatting.LIGHT_PURPLE)
            .build());

    public static final RegistryObject<Item> CELESTIAL_ROD = ITEMS.register("celestial_rod", ()->new ExtendedFishingRodItem.Builder()
            .durability(150)
            .color(ChatFormatting.LIGHT_PURPLE)
            .tooltipLines(1)
            .nightLuck()
            .build());
    // Fish Items
    public static final RegistryObject<Item> ENDER_EEL = ITEMS.register("ender_eel", ()->new Item(props().food(food(2, 0).build())));
    public static final RegistryObject<Item> ICICLE_FISH = ITEMS.register("icicle_fish", ()->new Item(props().food(
            food(2, 0).effect(() -> new MobEffectInstance(MobEffects.HARM, 1, 0), 1.0f).build()
    )));
    public static final RegistryObject<Item> LILYFISH = ITEMS.register("lilyfish", ()->new Item(props().food(food(2, 0.5f).build())));
    public static final RegistryObject<Item> MATRIX_FISH = ITEMS.register("matrix_fish", ()->new Item(props().food(food(4, 0.5f).build())));
    public static final RegistryObject<Item> SEAWEED = ITEMS.register("seaweed", ()->new Item(props()));
    public static final RegistryObject<Item> BAKED_SEAWEED = ITEMS.register("baked_seaweed", ()->new Item(props().food(food(4, 0.5f).build())));
    public static final RegistryObject<Item> SEAWEED_EEL = ITEMS.register("seaweed_eel", ()->new Item(props().food(food(4, 0).build())));
    public static final RegistryObject<Item> SLIMEFISH = ITEMS.register("slimefish", ()->new Item(props().food(
            food(4, 0.25f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 0), 1.0f).build()
    )));
    public static final RegistryObject<Item> SNOWBALL_FISH = ITEMS.register("snowball_fish", ()->new Item(props().food(food(4, 0.25f).build())));
    public static final RegistryObject<Item> TERRAFISH = ITEMS.register("terrafish", ()->new Item(props().food(food(4, 0.5f).build())));
    public static final RegistryObject<Item> CARROT_CARP = ITEMS.register("carrot_carp", ()->new Item(props().food(food(4, 0.25f).build())));
    public static final RegistryObject<Item> BAKED_CARROT_CARP = ITEMS.register("baked_carrot_carp", ()->new Item(props().food(food(6, 0.5f).build())));
    public static final RegistryObject<Item> OAKFISH = ITEMS.register("oakfish", ()->new Item(props().food(food(3, 0).build())));
    public static final RegistryObject<Item> CHARFISH = ITEMS.register("charfish", ()->new Item(props().food(
            food(2, 0).effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 100, 0), 1.0f).build()
    )));

    // Nether Items
    public static final RegistryObject<Item> SPIKERFISH = ITEMS.register("spikerfish", ()->new Item(props().fireResistant()));
    public static final RegistryObject<Item> BLACKSTONE_TROUT = ITEMS.register("blackstone_trout", ()->new Item(props()
            .food(food(4, 0.75f).build())
            .fireResistant()));
    public static final RegistryObject<Item> GRILLED_BLACKSTONE_TROUT = ITEMS.register("grilled_blackstone_trout",
            ()->new TooltippedItem(props().food(food(8, 0.8f).build()).fireResistant(), 2));
    public static final RegistryObject<Item> GRILLED_BLACKSTONE_DELUXE = ITEMS.register("grilled_blackstone_deluxe",
            ()->new TooltippedItem(props().food(food(11, 0.8f).build()).fireResistant(), 3));
    public static final RegistryObject<Item> BONEFISH = ITEMS.register("bonefish", ()->new Item(props().fireResistant()));
    public static final RegistryObject<Item> GILDED_BLACKSTONE_CARP = ITEMS.register("gilded_blackstone_carp",
            ()->new Item(props().food(food(4, 0.5f).build()).fireResistant()));
    public static final RegistryObject<Item> SMOKEY_SALMON = ITEMS.register("smokey_salmon", ()->new Item(props()
            .food(food(6, 0.5f).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300, 0), 1.0f).build())
            .fireResistant()));
    public static final RegistryObject<Item> SOUL_SALMON = ITEMS.register("soul_salmon", ()->new Item(props()
            .food(food(6, 0.75f).build())
            .fireResistant()));
    public static final RegistryObject<Item> MAGMA_COD = ITEMS.register("magma_cod", ()->new Item(props()
            .food(food(6, 0.5f).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300, 0), 1.0f).build())
            .fireResistant()));
    public static final RegistryObject<Item> BASALT_BASS = ITEMS.register("basalt_bass", ()->new Item(props()
            .food(food(4, 0.25f).build())
            .fireResistant()));
    public static final RegistryObject<Item> OBSIDIAN_HALIBUT = ITEMS.register("obsidian_halibut", ()->new Item(props()
            .food(food(4, 0.25f).build())
            .fireResistant()));

    // End Items
    public static final RegistryObject<Item> ENDFISH = ITEMS.register("endfish", ()->new Item(props().food(food(2, 0).build())));
    public static final RegistryObject<Item> BAKED_ENDFISH = ITEMS.register("baked_endfish",
            ()->new TooltippedItem(props().food(food(6, 0.8f).build()), 2));
    public static final RegistryObject<Item> ENDFISH_AND_CHORUS = ITEMS.register("endfish_and_chorus",
            ()->new TooltippedItem(props().food(food(8, 1.0f).build()), 2));
    public static final RegistryObject<Item> CHORUS_COD = ITEMS.register("chorus_cod",
            ()->new ChorusFruitItem(props().food(food(6, 0).build()).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> DRAGONFISH = ITEMS.register("dragonfish",
            ()->new Item(props().food(food(8, 0).build()).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> OMEGA_FLOATER = ITEMS.register("omega_floater",
            ()->new Item(props().food(food(6, 0).build()).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> PORTAL_PUFFER = ITEMS.register("portal_puffer",
            ()->new Item(props().food(food(4, 0).build()).rarity(Rarity.EPIC)));

    // Moon Fish
    public static final RegistryObject<Item> LUNARFISH = ITEMS.register("lunarfish", ()->new Item(props().food(food(2, 0).build())));
    public static final RegistryObject<Item> GALAXY_STARFISH = ITEMS.register("galaxy_starfish", ()->new Item(props().food(food(2, 0).build())));
    public static final RegistryObject<Item> STARRY_SALMON = ITEMS.register("starry_salmon", ()->new Item(props().food(food(2, 0).build())));
    public static final RegistryObject<Item> NEBULA_SWORDFISH = ITEMS.register("nebula_swordfish", ()->new Item(props().food(food(2, 0).build())));
    public static final RegistryObject<Item> AQUATIC_ASTRAL_STEW = ITEMS.register("aquatic_astral_stew",
            ()->new TooltippedItem(props().food(food(9, 0.75f).build()), 3));

    // Weather Fish
    public static final RegistryObject<Item> RAINY_BASS = ITEMS.register("rainy_bass", ()->new Item(props().food(food(3, 0.5f).build())));
    public static final RegistryObject<Item> STEAMED_BASS = ITEMS.register("steamed_bass",
            ()->new TooltippedItem(props().food(food(6, 0.25f).build()), 2));
    public static final RegistryObject<Item> CLOUDY_CRAB = ITEMS.register("cloudy_crab", ()->new Item(props().food(food(3, 0.75f).build())));
    public static final RegistryObject<Item> THUNDERING_BASS = ITEMS.register("thundering_bass", ()->new Item(props()
            .food(food(5, 0.75f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, 0), 1.0f).build())));
    public static final RegistryObject<Item> SMOKED_CLOUDY_CRAB = ITEMS.register("smoked_cloudy_crab", ()->new Item(props().food(food(7, 0.25f).build())));
    public static final RegistryObject<Item> BLIZZARD_BASS = ITEMS.register("blizzard_bass", ()->new Item(props().food(food(3, 0.5f).build())));

    // Accessories
    public static final RegistryObject<Item> GOLDEN_FISH = ITEMS.register("golden_fish",
            ()->new LureItem(props().stacksTo(1).rarity(Rarity.EPIC), 1));
    public static final RegistryObject<Item> SIMPLE_LURE = ITEMS.register("simple_lure",
            ()->new LureItem(props().stacksTo(1), 1));
    public static final RegistryObject<Item> SOUL_LURE = ITEMS.register("soul_lure",
            ()->new SoulLureItem(props().stacksTo(1)));


    // Helper Methods
    private static Item.Properties props() {
        return new Item.Properties();
    }

    private static FoodProperties.Builder food(int nutrition, float saturation) {
        return new FoodProperties.Builder().nutrition(nutrition).saturationMod(saturation);
    }


    public static void addToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            ITEMS.getEntries().stream()
                    .filter(reg -> reg.get() instanceof ExtendedFishingRodItem)
                    .forEach(event::accept);
        }

        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            ITEMS.getEntries().stream()
                    .filter(reg -> reg.get().isEdible())
                    .forEach(event::accept);
        }

        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            ITEMS.getEntries().stream()
                    .filter(reg -> !reg.get().isEdible() && !(reg.get() instanceof ExtendedFishingRodItem))
                    .forEach(event::accept);
            GoFishBlocks.BLOCKS.getEntries().stream()
                    .map(RegistryObject::get)
                    .forEach(event::accept);
        }
    }

}
