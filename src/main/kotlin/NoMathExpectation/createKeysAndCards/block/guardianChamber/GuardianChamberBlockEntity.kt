package NoMathExpectation.createKeysAndCards.block.guardianChamber

import NoMathExpectation.createKeysAndCards.modLangBuilder
import NoMathExpectation.createKeysAndCards.number
import com.simibubi.create.content.kinetics.base.KineticBlockEntity
import net.minecraft.ChatFormatting
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.util.Mth
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import kotlin.math.abs

class GuardianChamberBlockEntity(
    type: BlockEntityType<*>,
    pos: BlockPos,
    state: BlockState
) : KineticBlockEntity(type, pos, state) {
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

    val effectRange: Double get() = (if (isOverStressed) 0 else abs(speed)).toDouble()

    fun isInEffectRange(pos: BlockPos): Boolean {
        val range = effectRange
        if (Mth.equal(range, 0.0)) {
            return false
        }
        return blockPos.center.distanceToSqr(pos.center) <= range * range
    }

    fun shouldTakeEffect(pos: BlockPos, entity: Entity? = null): Boolean {
        return isInEffectRange(pos)
    }
}