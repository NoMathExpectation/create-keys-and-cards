package NoMathExpectation.createKeysAndCards.block

import NoMathExpectation.createKeysAndCards.block.guardianChamber.GuardianChamberBlock
import NoMathExpectation.createKeysAndCards.modRegistrate
import NoMathExpectation.createKeysAndCards.poi
import net.minecraft.world.entity.ai.village.poi.PoiType
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object ModPoiTypes {
    val guardianChamber: PoiType by modRegistrate.poi(GuardianChamberBlock.ID) {
        PoiType(
            ModBlocks.guardianChamber.stateDefinition.possibleStates.toSet(),
            1,
            1,
        )
    }.register()
}