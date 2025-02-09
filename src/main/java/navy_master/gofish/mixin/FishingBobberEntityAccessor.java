package navy_master.gofish.mixin;


import net.minecraft.world.entity.projectile.FishingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FishingHook.class)
public interface FishingBobberEntityAccessor {
    @Accessor("luck")
    int getLuckOfTheSeaLevel();

    @Mutable
    @Accessor("luck")
    void setLuckOfTheSeaLevel(int luckOfTheSeaLevel);

    @Accessor("lureSpeed")
    int getLureLevel();

    @Mutable
    @Accessor("lureSpeed")
    void setLureLevel(int lureLevel);
}
