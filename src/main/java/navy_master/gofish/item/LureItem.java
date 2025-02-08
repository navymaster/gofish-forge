package navy_master.gofish.item;

import navy_master.gofish.api.FishingBonus;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class LureItem extends Item implements FishingBonus {

    private final int lure;

    public LureItem(Properties properties, int lure) {
        super(properties);
        this.lure = lure;
    }

    @Override
    public int getLure() {
        return lure;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, level, tooltip, context);

        for(int i = 1; i <= 2; i++) {
            tooltip.add(Component.translatable(
                    String.format("gofish.lure.tooltip_%d", i),
                    lure
            ).withStyle(ChatFormatting.GRAY));
        }
    }
}
