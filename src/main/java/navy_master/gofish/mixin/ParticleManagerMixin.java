package navy_master.gofish.mixin;

import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.WaterDropParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleEngine.class)
public abstract class ParticleManagerMixin {

    @Shadow protected abstract <T extends ParticleOptions> void register(ParticleType<T> particleType, ParticleEngine.SpriteParticleRegistration<T> provider);

    @Inject(
            method = "registerProviders",
            at = @At("RETURN")
    )
    private void registerParticles(CallbackInfo ci) {
        this.register(ParticleTypes.RAIN, WaterDropParticle.Provider::new);
    }
}
