package navy_master.gofish.registry;


import com.mojang.serialization.Codec;
import navy_master.gofish.GoFish;
import navy_master.gofish.loot.WeatherCondition;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class GoFishParticles {
    // 创建延迟注册器
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(Registries.PARTICLE_TYPE, GoFish.MOD_ID);

    public static final Supplier<SimpleParticleType> LAVA_FISHING =
            register("lava_fishing");

    private static Supplier<SimpleParticleType> register(String id) {
        RegistryObject<SimpleParticleType> regObj = PARTICLE_TYPES.register(
                id,
                () -> new SimpleParticleType(false)
        );
        return regObj::get; // 返回解包的Supplier
    }
}
