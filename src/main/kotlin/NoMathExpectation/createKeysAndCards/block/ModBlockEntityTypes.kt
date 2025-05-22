package NoMathExpectation.createKeysAndCards.block

import NoMathExpectation.createKeysAndCards.block.guardianChamber.GuardianChamberBlock
import NoMathExpectation.createKeysAndCards.block.guardianChamber.GuardianChamberBlockEntity
import NoMathExpectation.createKeysAndCards.modRegistrate
import net.minecraft.world.level.block.entity.BlockEntityType
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object ModBlockEntityTypes {
    val guardianChamber: BlockEntityType<GuardianChamberBlockEntity> by
    modRegistrate.blockEntity(GuardianChamberBlock.ID, ::GuardianChamberBlockEntity)
        .validBlock { ModBlocks.guardianChamber }
        .register()
}