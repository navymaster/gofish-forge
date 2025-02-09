package navy_master.gofish.client.be;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import navy_master.gofish.entity.block.AstralCrateBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import org.joml.Matrix4f;

public class AstralCrateRenderer implements BlockEntityRenderer<AstralCrateBlockEntity> {
    private final TheEndPortalRenderer endPortalRenderer;

    public AstralCrateRenderer(BlockEntityRendererProvider.Context ctx) {
        this.endPortalRenderer = new TheEndPortalRenderer(ctx);
    }

    @Override
    public void render(AstralCrateBlockEntity astralCrate, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        Matrix4f matrix4f = poseStack.last().pose();
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.endPortal());

        this.renderSides(astralCrate, matrix4f, vertexConsumer);
    }

    private void renderSides(AstralCrateBlockEntity entity, Matrix4f matrix4f, VertexConsumer vertexConsumer) {
        float r = .2f;
        float g = .2f;
        float b = .2f;

        this.renderSide(entity, matrix4f, vertexConsumer, 0.0F, 0.99F, 0.01F, 1.0F, 0.99F, 1.0F, 1.0F, 1.0F, r, g, b);
        this.renderSide(entity, matrix4f, vertexConsumer, 0.01F, 0.99F, 0.99F, 0.0F, 0.01F, 0.0F, 0.0F, 0.0F, r, g, b);
        this.renderSide(entity, matrix4f, vertexConsumer, 0.99F, 0.99F, 0.99F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, r, g, b);
        this.renderSide(entity, matrix4f, vertexConsumer, 0.01F, 0.01F, 0.01F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, r, g, b);
        this.renderSide(entity, matrix4f, vertexConsumer, 0.01F, 0.99F, 0.01F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, r, g, b);
        this.renderSide(entity, matrix4f, vertexConsumer, 0.01F, 0.99F, 0.99F, 0.99F, 1.0F, 1.0F, 0.0F, 0.0F, r, g, b);
    }

    private void renderSide(AstralCrateBlockEntity entity, Matrix4f model, VertexConsumer vertices,
                            float x1, float x2, float y1, float y2,
                            float z1, float z2, float z3, float z4,
                            float r, float g, float b) {
        vertices.vertex(model, x1, y1, z1).color(r, g, b, 1.0F).endVertex();
        vertices.vertex(model, x2, y1, z2).color(r, g, b, 1.0F).endVertex();
        vertices.vertex(model, x2, y2, z3).color(r, g, b, 1.0F).endVertex();
        vertices.vertex(model, x1, y2, z4).color(r, g, b, 1.0F).endVertex();
    }
}
