package NoMathExpectation.createKeysAndCards.block

import NoMathExpectation.createKeysAndCards.CreateKeysAndCards
import NoMathExpectation.createKeysAndCards.block.guardianChamber.GuardianChamberBlock
import com.mojang.serialization.MapCodec
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object ModBlockTypes {
    val registry: DeferredRegister<MapCodec<out Block>> =
        DeferredRegister.create(Registries.BLOCK_TYPE, CreateKeysAndCards.ID)

    val guardianChamber: MapCodec<GuardianChamberBlock> by
    registry.register(GuardianChamberBlock.ID) { -> BlockBehaviour.simpleCodec(::GuardianChamberBlock) }
}