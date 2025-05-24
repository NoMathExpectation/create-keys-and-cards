package NoMathExpectation.createKeysAndCards

import NoMathExpectation.createKeysAndCards.block.ModBlockEntityTypes
import NoMathExpectation.createKeysAndCards.block.ModBlockTypes
import NoMathExpectation.createKeysAndCards.block.ModBlocks
import NoMathExpectation.createKeysAndCards.block.ModPoiTypes
import com.simibubi.create.foundation.data.CreateRegistrate
import net.minecraft.resources.ResourceLocation
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

/**
 * Main mod class.
 *
 * An example for blocks is in the `blocks` package of this mod.
 */
@Mod(CreateKeysAndCards.ID)
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
object CreateKeysAndCards {
    const val ID = "create_keys_and_cards"

    // the logger for our mod
    val LOGGER: Logger = LogManager.getLogger(ID)

    internal val registrate = CreateRegistrate.create(ID)

    init {
        LOGGER.log(Level.INFO, "Hello world!")

        // Register the KDeferredRegister to the mod-specific event bus
        registrate.registerEventListeners(MOD_BUS)

        // reference objects to register things
        ModCreativeTab
        ModBlocks
        ModBlockTypes
        ModBlockEntityTypes
        ModPoiTypes
    }

    @SubscribeEvent
    fun onCommonSetup(event: FMLCommonSetupEvent) {
        LOGGER.log(Level.INFO, "Create Keys and Cards Loading...")
    }
}

internal const val modId = CreateKeysAndCards.ID
internal val modRegistrate get() = CreateKeysAndCards.registrate
internal val String.modResource get() = ResourceLocation.fromNamespaceAndPath(modId, this)