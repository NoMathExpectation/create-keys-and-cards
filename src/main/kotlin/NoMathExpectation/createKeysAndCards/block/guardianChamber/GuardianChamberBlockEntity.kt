package NoMathExpectation.createKeysAndCards.block.guardianChamber

import com.simibubi.create.content.kinetics.base.KineticBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class GuardianChamberBlockEntity(
    type: BlockEntityType<*>,
    pos: BlockPos,
    state: BlockState
): KineticBlockEntity(type, pos, state) {
}