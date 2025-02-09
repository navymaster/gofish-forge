package navy_master.gofish.registry;


import navy_master.gofish.GoFish;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class GoFishParticles {
    // 创建延迟注册器
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(Registries.PARTICLE_TYPE, GoFish.MOD_ID);

    // 注册粒子类型
    public static final RegistryObject<SimpleParticleType> LAVA_FISHING =
            PARTICLE_TYPES.register("lava_fishing",
                    () -> new SimpleParticleType(false) // alwaysShow参数对应构造方法
            );
}
