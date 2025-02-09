package navy_master.gofish.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class GoFishTags {
    public static final TagKey<Biome> ICY = TagKey.create(Registries.BIOME, new ResourceLocation("gofish", "icy_biomes"));
    public static final TagKey<Biome> PLAINS = TagKey.create(Registries.BIOME, new ResourceLocation("gofish", "plains_biomes"));
    public static final TagKey<Biome> SWAMP = TagKey.create(Registries.BIOME, new ResourceLocation("gofish", "swamp_biomes"));
}
