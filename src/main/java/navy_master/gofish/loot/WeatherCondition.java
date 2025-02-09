package navy_master.gofish.loot;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import navy_master.gofish.registry.GoFishLoot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;

public record WeatherCondition(Optional<Boolean> raining, Optional<Boolean> thundering, Optional<Boolean> snowing) implements LootItemCondition {

    public static final Codec<WeatherCondition> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            ExtraCodecs.strictOptionalField(Codec.BOOL, "raining").forGetter(WeatherCondition::raining),
                            ExtraCodecs.strictOptionalField(Codec.BOOL, "thundering").forGetter(WeatherCondition::thundering),
                            ExtraCodecs.strictOptionalField(Codec.BOOL, "snowing").forGetter(WeatherCondition::snowing)
                    )
                    .apply(instance, WeatherCondition::new)
    );

    @Override
    public LootItemConditionType getType() {
        return GoFishLoot.WEATHER.get();
    }

    @Override
    public Set<LootContextParam<?>> getReferencedContextParams() {
        return ImmutableSet.of(LootContextParams.THIS_ENTITY, LootContextParams.ORIGIN);
    }

    @Override
    public boolean test(LootContext lootContext) {
        @Nullable Entity entity = lootContext.getParamOrNull(LootContextParams.THIS_ENTITY);
        @Nullable Vec3 pos = lootContext.getParamOrNull(LootContextParams.ORIGIN);

        if (entity != null && pos != null) {
            Level level = entity.level();

            if (raining.isPresent() && raining.get() && !level.isRaining()) {
                return false;
            }

            if (thundering.isPresent() && thundering.get() && !level.isThundering()) {
                return false;
            }

            if (snowing.isPresent() && snowing.get()) {
                BlockPos blockPos = BlockPos.containing(pos.x, pos.y, pos.z);
                if (level.getBiome(entity.blockPosition()).value().coldEnoughToSnow(blockPos)) {
                    return level.isRaining();
                }
                return false;
            }

            return true;
        }
        return false;
    }

    public static LootItemCondition.Builder builder(boolean raining, boolean thundering, boolean snowing) {
        return () -> new WeatherCondition(Optional.of(raining), Optional.of(thundering), Optional.of(snowing));
    }
}
