package navy_master.gofish.loot.biome;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record BiomePredicate(List<ResourceKey<Biome>> valid) {

    public static final Codec<BiomePredicate> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    ResourceKey.codec(Registries.BIOME).listOf().fieldOf("valid").forGetter(BiomePredicate::valid)
            ).apply(instance, BiomePredicate::new)
    );
    public static final BiomePredicate EMPTY = new BiomePredicate(Collections.emptyList());

    public static BiomePredicate create(List<ResourceKey<Biome>> valid) {
        return new BiomePredicate(valid);
    }

    public List<ResourceKey<Biome>> getValid() {
        return valid;
    }

    public boolean test(Holder<Biome> biome) {
        return biome.is(key -> valid.contains(key));
    }

    public JsonElement toJson() {
        return CODEC.encodeStart(JsonOps.INSTANCE, this).getOrThrow(false, error -> {});
    }

    public static BiomePredicate fromJson(JsonElement json) {
        return CODEC.parse(JsonOps.INSTANCE, json).getOrThrow(false, error -> {});
    }

    public static class Builder {
        private List<ResourceKey<Biome>> valid;

        public static Builder create() {
            return new BiomePredicate.Builder();
        }

        public Builder setValid(List<ResourceKey<Biome>> valid) {
            this.valid = valid;
            return this;
        }

        public Builder setValidFromString(List<String> valid) {
            List<ResourceKey<Biome>> rKeys = new ArrayList<>();
            for (String str : valid) {
                if (!str.isEmpty()) {
                    rKeys.add(ResourceKey.create(Registries.BIOME, new ResourceLocation(str)));
                }
            }
            return setValid(rKeys);
        }

        public Builder add(ResourceKey<Biome> biome) {
            valid.add(biome);
            return this;
        }

        public Builder add(String biome) {
            if (!biome.isEmpty()) {
                valid.add(ResourceKey.create(Registries.BIOME, new ResourceLocation(biome)));
            }
            return this;
        }

        public Builder of(BiomePredicate biomePredicate) {
            this.valid = biomePredicate.valid;
            return this;
        }

        public BiomePredicate build() {
            return new BiomePredicate(this.valid);
        }
    }
}
