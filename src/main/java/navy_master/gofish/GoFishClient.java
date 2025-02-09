package navy_master.gofish;

import navy_master.gofish.client.be.AstralCrateRenderer;
import navy_master.gofish.client.item.AstralCrateItemRenderer;
import navy_master.gofish.registry.GoFishBlocks;
import navy_master.gofish.registry.GoFishEntities;
import navy_master.gofish.registry.GoFishItems;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = "gofish", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GoFishClient {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // 设置渲染层（1.20.2正确方式）
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(GoFishBlocks.ASTRAL_CRATE.get(), RenderType.cutout());
        });

        // 注册物品自定义渲染属性
        event.enqueueWork(() -> {
            ItemProperties.register(
                    GoFishBlocks.ASTRAL_CRATE.get().asItem(),
                    new ResourceLocation("gofish", "crate_render"),
                    (stack, level, entity, seed) -> 1.0F
            );
        });

        // 注册所有钓鱼竿的模型谓词
        registerFishingRodPredicates(GoFishItems.BLAZE_ROD.get());
        registerFishingRodPredicates(GoFishItems.CELESTIAL_ROD.get());
        registerFishingRodPredicates(GoFishItems.FROSTED_ROD.get());
        registerFishingRodPredicates(GoFishItems.SOUL_ROD.get());
        registerFishingRodPredicates(GoFishItems.MATRIX_ROD.get());
        registerFishingRodPredicates(GoFishItems.SLIME_ROD.get());
        registerFishingRodPredicates(GoFishItems.DIAMOND_REINFORCED_ROD.get());
        registerFishingRodPredicates(GoFishItems.SKELETAL_ROD.get());
        registerFishingRodPredicates(GoFishItems.EYE_OF_FISHING.get());
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // 注册方块实体渲染器
        event.registerBlockEntityRenderer(
                GoFishEntities.ASTRAL_CRATE.get(),
                AstralCrateRenderer::new
        );
    }

    private static void registerFishingRodPredicates(Item item) {
        // 正确使用Forge的ItemProperties注册模型谓词
        ItemProperties.register(
                item,
                new ResourceLocation("cast"),
                (itemStack, clientLevel, livingEntity, seed) -> {
                    if (livingEntity == null) {
                        return 0.0F;
                    }

                    boolean mainHand = livingEntity.getMainHandItem() == itemStack;
                    boolean offHand = livingEntity.getOffhandItem() == itemStack;

                    if (livingEntity.getMainHandItem().getItem() instanceof FishingRodItem) {
                        offHand = false;
                    }

                    return (mainHand || offHand) &&
                            livingEntity instanceof Player &&
                            ((Player)livingEntity).fishing != null ? 1.0F : 0.0F;
                }
        );
    }
}
