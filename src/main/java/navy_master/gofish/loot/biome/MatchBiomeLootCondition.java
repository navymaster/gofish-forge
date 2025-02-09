package navy_master.gofish.loot.biome;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import navy_master.gofish.loot.moon.FullMoonCondition;
import navy_master.gofish.registry.GoFishLoot;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public record MatchBiomeLootCondition(Optional<BiomeTagPredicate> category, Optional<BiomePredicate> biome) implements LootItemCondition {

    /*public static final Codec<MatchBiomeLootCondition> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            ExtraCodecs.strictOptionalField(BiomeTagPredicate.CODEC, "category").forGetter(MatchBiomeLootCondition::category),
                            ExtraCodecs.strictOptionalField(BiomePredicate.CODEC, "biome").forGetter(MatchBiomeLootCondition::biome)
                    )
                    .apply(instance, MatchBiomeLootCondition::new)
    );*/
    public static final Codec<MatchBiomeLootCondition> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            // 修改点 1: 使用基础 optionalField 方法
                            BiomeTagPredicate.CODEC.optionalFieldOf("category").forGetter(MatchBiomeLootCondition::category),
                            BiomePredicate.CODEC.optionalFieldOf("biome").forGetter(MatchBiomeLootCondition::biome)
                    )
                    .apply(instance, MatchBiomeLootCondition::new)
    );


    @Override
    public LootItemConditionType getType() {
        return GoFishLoot.MATCH_BIOME.get();
    }

    @Override
    public Set<LootContextParam<?>> getReferencedContextParams() {
        return ImmutableSet.of(LootContextParams.ORIGIN);
    }

    @Override
    public boolean test(LootContext lootContext) {
        Vec3 origin = lootContext.getParamOrNull(LootContextParams.ORIGIN);

        if(origin != null) {
            Holder<Biome> fisherBiome = lootContext.getLevel().getBiome(BlockPos.containing(origin.x, origin.y, origin.z));

            if (category.isEmpty() || category.get().getValid().isEmpty()) {
                if (biome.isPresent() && !biome.get().getValid().isEmpty()) {
                    return biome.get().test(fisherBiome);
                }
            } else {
                return category.get().test(fisherBiome);
            }
        }
        return false;
    }

    public static LootItemCondition.Builder builder(ResourceKey<Biome>... biomes) {
        return builder(Collections.emptyList(), List.of(biomes));
    }

    public static LootItemCondition.Builder builder(TagKey<Biome>... categories) {
        return builder(Arrays.asList(categories), Collections.emptyList());
    }

    public static LootItemCondition.Builder builder(List<TagKey<Biome>> categories, List<ResourceKey<Biome>> biomes) {
        List<String> stringCats = new ArrayList<>();
        List<String> stringBiomes = new ArrayList<>();

        categories.forEach(category -> stringCats.add(category.location().toString()));
        biomes.forEach(biome -> stringBiomes.add(biome.location().toString()));

        return builder(BiomeTagPredicate.Builder.create().setValidByString(stringCats),
                BiomePredicate.Builder.create().setValidFromString(stringBiomes));
    }

    public static LootItemCondition.Builder builder(String category, String biome) {
        return builder(BiomeTagPredicate.Builder.create().add(category),
                BiomePredicate.Builder.create().add(biome));
    }

    public static LootItemCondition.Builder builder(BiomeTagPredicate.Builder categoryBuilder) {
        return builder(categoryBuilder, BiomePredicate.Builder.create());
    }

    public static LootItemCondition.Builder builder(BiomePredicate.Builder biomeBuilder) {
        return builder(BiomeTagPredicate.Builder.create(), biomeBuilder);
    }

    public static LootItemCondition.Builder builder(BiomeTagPredicate.Builder categoryBuilder, BiomePredicate.Builder biomeBuilder) {
        return () -> new MatchBiomeLootCondition(
                Optional.of(categoryBuilder.build()),
                Optional.of(biomeBuilder.build())
        );
    }
    public static class CondtionSerializer implements Serializer<MatchBiomeLootCondition> {

        // 1.20.1 需要空实现的序列化方法
        @Override
        public void serialize(JsonObject json, MatchBiomeLootCondition value, JsonSerializationContext context) {}

        @Override
        public MatchBiomeLootCondition deserialize(JsonObject json, JsonDeserializationContext context) {
            return new MatchBiomeLootCondition(Optional.empty(), Optional.empty()); // 实际通过CODEC解析
        }
    }
}
