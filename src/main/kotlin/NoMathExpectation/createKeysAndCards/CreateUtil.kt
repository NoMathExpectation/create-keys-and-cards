package NoMathExpectation.createKeysAndCards

import com.simibubi.create.api.stress.BlockStressValues
import com.tterrag.registrate.AbstractRegistrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.NoConfigBuilder
import net.createmod.catnip.lang.LangBuilder
import net.createmod.catnip.lang.LangNumberFormat
import net.createmod.catnip.registry.RegisteredObjectsHelper
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.ai.village.poi.PoiType
import net.minecraft.world.level.block.Block
import java.util.function.DoubleSupplier

private val impactMap: MutableMap<ResourceLocation, () -> Double> =
    mutableMapOf<ResourceLocation, () -> Double>().also {
        BlockStressValues.IMPACTS.registerProvider {
            val resourceLocation = RegisteredObjectsHelper.getKeyOrThrow(it)
            val supplier = impactMap[resourceLocation] ?: return@registerProvider null
            DoubleSupplier { supplier() }
        }
    }

internal fun <B : Block, P> BlockBuilder<B, P>.setImpact(supplier: () -> Double) = transform {
    impactMap[name.modResource] = supplier
    it
}

internal fun <B : Block, P> BlockBuilder<B, P>.setImpact(value: Double = 0.0) = setImpact { value }

private val capacityMap: MutableMap<ResourceLocation, () -> Double> =
    mutableMapOf<ResourceLocation, () -> Double>().also {
        BlockStressValues.CAPACITIES.registerProvider {
            val resourceLocation = RegisteredObjectsHelper.getKeyOrThrow(it)
            val supplier = capacityMap[resourceLocation] ?: return@registerProvider null
            DoubleSupplier { supplier() }
        }
    }

internal fun <B : Block, P> BlockBuilder<B, P>.setCapacity(supplier: () -> Double) = transform {
    capacityMap[name.modResource] = supplier
    it
}

internal fun <B : Block, P> BlockBuilder<B, P>.setCapacity(value: Double = 0.0) = setCapacity { value }

val modLangBuilder get() = LangBuilder(modId)

fun LangBuilder.number(value: Number): LangBuilder = text(LangNumberFormat.format(value.toDouble()))

inline fun <S : AbstractRegistrate<S>> AbstractRegistrate<S>.poi(
    name: String,
    crossinline builder: () -> PoiType
): NoConfigBuilder<PoiType, PoiType, S> = generic(name, Registries.POINT_OF_INTEREST_TYPE) { builder() }