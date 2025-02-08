package navy_master.gofish.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CrateItem extends BlockItem {

    private final ResourceLocation loot;

    // Forge 的构造函数需要 Properties 而非 Settings
    public CrateItem(Block block, Properties properties) {
        super(block, properties);
        this.loot = new ResourceLocation("minecraft", "chests/pillager_outpost");
    }

    public CrateItem(Block block, Properties properties, ResourceLocation loot) {
        super(block, properties);
        this.loot = loot;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) { // 方法名改为 useOn
        Player player = context.getPlayer();
        if (player != null && player.isShiftKeyDown()) { // Forge 使用 isShiftKeyDown 判断潜行
            return InteractionResult.FAIL;
        }
        return super.useOn(context);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player.isShiftKeyDown()) {
            if (!level.isClientSide()) { // Forge 的 isClientSide 代替 isClient
                ServerLevel serverLevel = (ServerLevel) level;
                getDrops(serverLevel, loot, player.position()).forEach(stack -> {
                    Containers.dropItemStack(level, player.getX(), player.getY(), player.getZ(), stack); // Forge 的物品掉落方法
                });
            }

            if (!player.isCreative()) {
                player.getItemInHand(hand).shrink(1); // shrink 代替 decrement
            }

            return InteractionResultHolder.success(player.getItemInHand(hand));
        }

        return super.use(level, player, hand);
    }

    private List<ItemStack> getDrops(ServerLevel level, ResourceLocation identifier, Vec3 pos) {
        // 构建 LootParams
        LootParams.Builder paramsBuilder = new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, pos)
                .withParameter(LootContextParams.THIS_ENTITY, level.getRandom().nextBoolean() ? null : level.getRandomPlayer()); // Forge 需要至少一个实体参数

        LootTable lootTable = level.getServer().getLootData().getLootTable(identifier);
        return lootTable.getRandomItems(paramsBuilder.create(LootContextParamSets.CHEST)); // 正确参数类型
    }

    @Override
    @OnlyIn(Dist.CLIENT) // Forge 的客户端注解
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        MutableComponent text = Component.translatable("gofish.crate_tooltip")
                .withStyle(ChatFormatting.GRAY)
                .withStyle(ChatFormatting.ITALIC); // 格式化方法名改为 withStyle
        tooltip.add(text);
    }
}