package navy_master.gofish.registry;


import navy_master.gofish.GoFish;
import navy_master.gofish.loot.WeatherCondition;
import navy_master.gofish.loot.biome.MatchBiomeLootCondition;
import navy_master.gofish.loot.moon.FullMoonCondition;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GoFish.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GoFishLootHandler {
    private static final ResourceLocation FISHING_LOOT_TABLE = new ResourceLocation("minecraft", "gameplay/fishing");
    private static boolean hasModifiedFishPool = false;

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        if (FISHING_LOOT_TABLE.equals(event.getName()) && !hasModifiedFishPool) {
            hasModifiedFishPool = true;

            LootPool.Builder poolBuilder = LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .name("gofish_custom_fish");

            /*寒冷天气的鱼
            没有和fabric中IS_SNOWY完全等同的标签
            这里用POLAR_BEARS_SPAWN_ON_ALTERNATE_BLOCKS和SPAWNS_SNOW_FOXES综合一下
            TODO::这里的生物群系标签和fabric版本不一致，先凑合跑
            */
            addBiomeEntry(poolBuilder, GoFishItems.SNOWBALL_FISH.get(), 10, BiomeTags.POLAR_BEARS_SPAWN_ON_ALTERNATE_BLOCKS);
            addBiomeEntry(poolBuilder, GoFishItems.ICICLE_FISH.get(), 10, BiomeTags.POLAR_BEARS_SPAWN_ON_ALTERNATE_BLOCKS);
            addBiomeEntry(poolBuilder, GoFishItems.SNOWBALL_FISH.get(), 10, BiomeTags.SPAWNS_SNOW_FOXES);
            addBiomeEntry(poolBuilder, GoFishItems.ICICLE_FISH.get(), 10, BiomeTags.SPAWNS_SNOW_FOXES);
            // 沼泽
            addBiomeEntry(poolBuilder, GoFishItems.SLIMEFISH.get(), 10, BiomeTags.HAS_SWAMP_HUT);
            addBiomeEntry(poolBuilder, GoFishItems.LILYFISH.get(), 10, BiomeTags.HAS_SWAMP_HUT);

            // 海洋
            addBiomeEntry(poolBuilder, GoFishItems.SEAWEED_EEL.get(), 10, BiomeTags.IS_OCEAN);

            // 台地
            addBiomeEntry(poolBuilder, GoFishItems.TERRAFISH.get(), 10, BiomeTags.IS_BADLANDS);

            // 平原和森林
            addBiomeEntry(poolBuilder, GoFishItems.CARROT_CARP.get(), 10, BiomeTags.HAS_VILLAGE_PLAINS);
            addBiomeEntry(poolBuilder, GoFishItems.OAKFISH.get(), 10, BiomeTags.HAS_VILLAGE_PLAINS);
            addBiomeEntry(poolBuilder, GoFishItems.CARROT_CARP.get(), 10, BiomeTags.IS_FOREST);
            addBiomeEntry(poolBuilder, GoFishItems.OAKFISH.get(), 10, BiomeTags.IS_FOREST);

            // 月夜
            addMoonEntry(poolBuilder, GoFishItems.LUNARFISH.get(), 50);
            addMoonEntry(poolBuilder, GoFishItems.GALAXY_STARFISH.get(), 25);
            addMoonEntry(poolBuilder, GoFishItems.STARRY_SALMON.get(), 50);
            addMoonEntry(poolBuilder, GoFishItems.NEBULA_SWORDFISH.get(), 25);

            // 天气条件
            addWeatherEntry(poolBuilder, GoFishItems.RAINY_BASS.get(), 100, true, false, false);
            addWeatherEntry(poolBuilder, GoFishItems.THUNDERING_BASS.get(), 50, false, true, false);
            addWeatherEntry(poolBuilder, GoFishItems.BLIZZARD_BASS.get(), 100, false, false, true);

            // 云蟹
            poolBuilder.add(LootItem.lootTableItem(GoFishItems.CLOUDY_CRAB.get())
                    .setWeight(50)
                    .when(LocationCheck.checkLocation(
                            LocationPredicate.Builder.location()
                                    .setY(MinMaxBounds.Doubles.atLeast(150))
                    ))
            );

            event.getTable().addPool(poolBuilder.build());
        }
    }

    private static void addBiomeEntry(LootPool.Builder builder, Item item, int weight, TagKey<Biome> biomeTag) {
        builder.add(LootItem.lootTableItem(item)
                .setWeight(weight)
                .when(MatchBiomeLootCondition.builder(biomeTag))
        );
    }

    private static void addMoonEntry(LootPool.Builder builder, Item item, int weight) {
        builder.add(LootItem.lootTableItem(item)
                .setWeight(weight)
                .when(FullMoonCondition.builder())
        );
    }

    private static void addWeatherEntry(LootPool.Builder builder, Item item, int weight, boolean raining, boolean thundering, boolean snowing) {
        builder.add(LootItem.lootTableItem(item)
                .setWeight(weight)
                .when(WeatherCondition.builder(raining, thundering, snowing))
        );
    }
}
