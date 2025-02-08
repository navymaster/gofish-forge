package navy_master.gofish.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class TooltippedItem extends Item {

    private final int lines;

    public TooltippedItem(Properties properties, int lines) {
        super(properties);
        this.lines = lines;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, level, tooltip, context);

        if(lines > 0) {
            for (int i = 1; i <= lines; i++) {
                tooltip.add(Component.translatable(
                        "%s.tooltip_%d".formatted(getDescriptionId(), i)
                ).withStyle(ChatFormatting.GRAY));
            }
        }
    }
}
