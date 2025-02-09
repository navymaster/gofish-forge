package navy_master.gofish.client.item;

import com.mojang.blaze3d.vertex.PoseStack;
import navy_master.gofish.entity.block.AstralCrateBlockEntity;
import navy_master.gofish.registry.GoFishBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class AstralCrateItemRenderer implements IClientItemExtensions {
    private static final AstralCrateBlockEntity astralCrate = new AstralCrateBlockEntity(BlockPos.ZERO, GoFishBlocks.ASTRAL_CRATE.get().defaultBlockState());

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return new BERenderer();
    }

    private static class BERenderer extends BlockEntityWithoutLevelRenderer {
        private final BlockEntityRenderDispatcher blockEntityDispatcher;

        public BERenderer() {
            super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
            this.blockEntityDispatcher = Minecraft.getInstance().getBlockEntityRenderDispatcher();
        }

        @Override
        public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack poseStack,
                                 MultiBufferSource bufferSource, int light, int overlay) {

            BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
            BlockEntityRenderer<AstralCrateBlockEntity> renderer = blockEntityDispatcher.getRenderer(astralCrate);

            // 渲染方块本体
            blockRenderer.renderSingleBlock(
                    astralCrate.getBlockState(),
                    poseStack,
                    bufferSource,
                    light,
                    overlay
            );

            // 渲染方块实体
            if (renderer != null) {
                renderer.render(
                        astralCrate,
                        0f,
                        poseStack,
                        bufferSource,  // 使用MultiBufferSource而不是VertexConsumer
                        light,
                        overlay
                );
            }
        }
    }
}
