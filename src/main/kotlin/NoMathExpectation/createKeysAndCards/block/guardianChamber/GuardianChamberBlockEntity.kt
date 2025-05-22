package NoMathExpectation.createKeysAndCards.block.guardianChamber

import NoMathExpectation.createKeysAndCards.modLangBuilder
import NoMathExpectation.createKeysAndCards.number
import com.simibubi.create.content.kinetics.base.KineticBlockEntity
import net.minecraft.ChatFormatting
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class GuardianChamberBlockEntity(
    type: BlockEntityType<*>,
    pos: BlockPos,
    state: BlockState
) : KineticBlockEntity(type, pos, state) {
    val effectRange get() = speed

    override fun addToGoggleTooltip(tooltip: MutableList<Component>, isPlayerSneaking: Boolean): Boolean {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking)

        modLangBuilder.translate("guardian_chamber.tooltip.range")
            .style(ChatFormatting.GRAY)
            .forGoggles(tooltip)
        modLangBuilder.number(effectRange)
            .style(ChatFormatting.AQUA)
            .forGoggles(tooltip, 1)

        return true
    }
}