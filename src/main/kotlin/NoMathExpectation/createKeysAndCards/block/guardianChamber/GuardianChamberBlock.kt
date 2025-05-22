package NoMathExpectation.createKeysAndCards.block.guardianChamber

import NoMathExpectation.createKeysAndCards.block.ModBlockEntityTypes
import NoMathExpectation.createKeysAndCards.block.ModBlockTypes
import com.simibubi.create.content.kinetics.base.KineticBlock
import com.simibubi.create.foundation.block.IBE
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.state.BlockState

class GuardianChamberBlock(
    properties: Properties
) : KineticBlock(properties), IBE<GuardianChamberBlockEntity> {
    override fun getBlockEntityClass() = GuardianChamberBlockEntity::class.java

    override fun getBlockEntityType() = ModBlockEntityTypes.guardianChamber

    override fun codec() = ModBlockTypes.guardianChamber

    companion object {
        const val ID = "guardian_chamber"
    }

    override fun getRotationAxis(state: BlockState) = Direction.Axis.Y

    override fun hasShaftTowards(world: LevelReader, pos: BlockPos, state: BlockState, face: Direction): Boolean {
        return face == Direction.DOWN
    }
}