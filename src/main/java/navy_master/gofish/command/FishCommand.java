package navy_master.gofish.command;


import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import navy_master.gofish.impl.GoFishLootTables;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = "gofish", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FishCommand {
    private static final ResourceLocation FISHING_LOOT_TABLE = new ResourceLocation("minecraft", "gameplay/fishing");
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("fish")
                .requires(source -> source.hasPermission(2))
                .executes(context -> {
                    fish(context, 1);
                    return 1;
                })
                .then(Commands.argument("count", IntegerArgumentType.integer(1, 1000))
                        .executes(context -> {
                            fish(context, IntegerArgumentType.getInteger(context, "count"));
                            return 1;
                        }))
        );
    }

    private static void fish(CommandContext<CommandSourceStack> context, int times) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayerOrException();
        ServerLevel level = source.getLevel();

        LootParams.Builder paramsBuilder = new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, player.position())
                .withParameter(LootContextParams.TOOL, player.getMainHandItem())
                .withOptionalParameter(LootContextParams.THIS_ENTITY, player);

        LootParams params = paramsBuilder.create(LootContextParamSets.FISHING);

        LootTable table;
        if (level.dimensionType().ultraWarm()) {
            table = level.getServer().getLootData().getLootTable(GoFishLootTables.NETHER_FISHING);
        } else if (!level.dimensionType().bedWorks()) {
            table = level.getServer().getLootData().getLootTable(GoFishLootTables.END_FISHING);
        } else {
            table = level.getServer().getLootData().getLootTable(FISHING_LOOT_TABLE);
        }

        for (int i = 0; i < times; i++) {
            List<ItemStack> items = table.getRandomItems(params);
            items.forEach(player.getInventory()::placeItemBackInInventory);
        }
    }

}
