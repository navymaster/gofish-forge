package navy_master.gofish.registry;


import com.mojang.serialization.Codec;
import navy_master.gofish.GoFish;
import navy_master.gofish.loot.WeatherCondition;
import navy_master.gofish.loot.biome.MatchBiomeLootCondition;
import navy_master.gofish.loot.moon.FullMoonCondition;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class GoFishLoot {
    public static final DeferredRegister<LootItemConditionType> REGISTER =
            DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, GoFish.MOD_ID);

    // 使用Supplier包装实际类型
    public static final Supplier<LootItemConditionType> MATCH_BIOME =
            register("match_biome", MatchBiomeLootCondition.CODEC);
    public static final Supplier<LootItemConditionType> FULL_MOON =
            register("full_moon", FullMoonCondition.CODEC);
    public static final Supplier<LootItemConditionType> WEATHER =
            register("weather", WeatherCondition.CODEC);

    private static Supplier<LootItemConditionType> register(String id, Codec<? extends LootItemCondition> codec) {
        RegistryObject<LootItemConditionType> regObj = REGISTER.register(
                id,
                () -> new LootItemConditionType(codec)
        );
        return regObj::get; // 返回解包的Supplier
    }
}
