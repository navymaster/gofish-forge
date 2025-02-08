package navy_master.gofish.item;

import navy_master.gofish.api.FishingBonus;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class SoulLureItem extends Item implements FishingBonus {

    private static final TagKey<Biome> SOUL_SAND_VALLEY = TagKey.create(Registries.BIOME, new ResourceLocation("minecraft", "has_structure/ruined_portal_nether"));

    public SoulLureItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getLuckOfTheSea() {
        return 1;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, level, tooltip, context);

        tooltip.add(Component.translatable("gofish.lure.tooltip_1").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("gofish.lots.tooltip_2", 1, Component.translatable("biome.minecraft.soul_sand_valley")).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public boolean shouldApply(Level level, Player player) {
        return level.getBiome(player.blockPosition()).is(SOUL_SAND_VALLEY);
    }
}
