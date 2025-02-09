package navy_master.gofish.mixin;


import navy_master.gofish.item.ExtendedFishingRodItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.client.player.AbstractClientPlayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingHookRenderer.class)
public class FishingBobberEntityRendererMixin {

    private Player gofish_owner;

    @Inject(
            method = "render",
            at = @At("HEAD")
    )
    private void storeContext(FishingHook entity, float f, float g, PoseStack matrixStack, MultiBufferSource buffer, int i, CallbackInfo ci) {
        gofish_owner = entity.getPlayerOwner();
    }

    @ModifyVariable(
            method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getAttackAnim(F)F"),
            ordinal = 0,
            argsOnly = true)
    private int mod(int i) {
        ItemStack itemStack = gofish_owner.getMainHandItem();

        if (!itemStack.is(Items.FISHING_ROD)) {
            if(itemStack.getItem() instanceof ExtendedFishingRodItem) {
                return -i;
            }
        }

        return i;
    }
}
