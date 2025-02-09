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
import net.minecraft.world.level.storage.loot.Serializer;

import java.util.function.Supplier;

public class GoFishLoot {
    public static final DeferredRegister<LootItemConditionType> REGISTER =
            DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, GoFish.MOD_ID);

    // 修改为使用 BaseSerializer
    public static final Supplier<LootItemConditionType> MATCH_BIOME =
            register("match_biome", new MatchBiomeLootCondition.CondtionSerializer());
    public static final Supplier<LootItemConditionType> FULL_MOON =
            register("full_moon", new FullMoonCondition.CondtionSerializer());
    public static final Supplier<LootItemConditionType> WEATHER =
            register("weather", new WeatherCondition. CondtionSerializer());

    // 参数类型改为 BaseSerializer
    private static Supplier<LootItemConditionType> register(String id, Serializer<?> serializer) {
        RegistryObject<LootItemConditionType> regObj = REGISTER.register(
                id,
                () -> new LootItemConditionType((Serializer<? extends LootItemCondition>) serializer) // 1.20.1 的正确构造方式
        );
        return regObj::get;
    }
}

