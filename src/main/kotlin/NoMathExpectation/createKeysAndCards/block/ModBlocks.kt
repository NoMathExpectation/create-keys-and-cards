package NoMathExpectation.createKeysAndCards.block

import NoMathExpectation.createKeysAndCards.block.guardianChamber.GuardianChamberBlock
import NoMathExpectation.createKeysAndCards.modRegistrate
import NoMathExpectation.createKeysAndCards.setImpact
import com.simibubi.create.foundation.data.TagGen
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object ModBlocks {
    val guardianChamber: GuardianChamberBlock by
    modRegistrate.block(GuardianChamberBlock.ID, ::GuardianChamberBlock)
        .transform(TagGen.axeOrPickaxe())
        .setImpact(256.0)
        .simpleItem()
        .register()
}
