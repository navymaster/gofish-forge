package navy_master.gofish.registry;

import navy_master.gofish.GoFish;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class GoFishParticles {
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
