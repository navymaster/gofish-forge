package navy_master.gofish.eventhandler;

import navy_master.gofish.api.FireproofEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class NetherFishingFireproofHandler {

    @SubscribeEvent
    public void onEntitySpawn(EntityJoinLevelEvent event) {
        // 只在下界维度处理
        if (!event.getLevel().dimensionType().ultraWarm()) {
            return;
        }

        if (event.getEntity() instanceof ItemEntity itemEntity) {
            if (itemEntity instanceof FireproofEntity fireproofEntity) {
                fireproofEntity.gf_setFireproof(true);
            }
        }
    }


}