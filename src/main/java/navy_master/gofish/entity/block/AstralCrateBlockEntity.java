package navy_master.gofish.entity.block;

import navy_master.gofish.registry.GoFishEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AstralCrateBlockEntity extends TheEndPortalBlockEntity {

    public AstralCrateBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return GoFishEntities.ASTRAL_CRATE.get();
    }
}
