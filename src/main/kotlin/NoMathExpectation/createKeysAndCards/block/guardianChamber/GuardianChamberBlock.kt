package NoMathExpectation.createKeysAndCards.block.guardianChamber

import NoMathExpectation.createKeysAndCards.block.ModBlockEntityTypes
import NoMathExpectation.createKeysAndCards.block.ModBlockTypes
import NoMathExpectation.createKeysAndCards.block.ModPoiTypes
import NoMathExpectation.createKeysAndCards.modId
import com.simibubi.create.content.equipment.wrench.WrenchItem
import com.simibubi.create.content.kinetics.base.KineticBlock
import com.simibubi.create.foundation.block.IBE
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.ai.village.poi.PoiManager
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.BucketItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.SolidBucketItem
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.ClipContext
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.LiquidBlockContainer
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.phys.BlockHitResult
import net.neoforged.bus.api.Event
import net.neoforged.bus.api.EventPriority
import net.neoforged.bus.api.ICancellableEvent
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent
import net.neoforged.neoforge.event.level.BlockEvent
import kotlin.jvm.optionals.getOrNull
import kotlin.streams.asSequence

class GuardianChamberBlock(
    properties: Properties
) : KineticBlock(properties), IBE<GuardianChamberBlockEntity> {
    override fun getBlockEntityClass() = GuardianChamberBlockEntity::class.java

    override fun getBlockEntityType() = ModBlockEntityTypes.guardianChamber

    override fun codec() = ModBlockTypes.guardianChamber

    @EventBusSubscriber(modid = modId)
    companion object {
        const val ID = "guardian_chamber"
        const val MAX_EFFECT_RANGE = 256.0

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        fun onBlockBreak(event: BlockEvent.BreakEvent) {
            handleBlockChange(event, event.level, event.pos, event.player)
        }

//        @SubscribeEvent(priority = EventPriority.HIGHEST)
//        fun onFluidPlaceBlock(event: BlockEvent.FluidPlaceBlockEvent) {
//
//        }

//        @SubscribeEvent(priority = EventPriority.HIGHEST)
//        fun onBlockToolModify(event: BlockEvent.BlockToolModificationEvent) {
//
//        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        fun onBlockPlace(event: BlockEvent.EntityPlaceEvent) {
            handleBlockChange(event, event.level, event.pos, event.entity)
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        fun onPortalSpawn(event: BlockEvent.PortalSpawnEvent) {
            handleBlockChange(event, event.level, event.pos)
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        fun onBlockMultiPlace(event: BlockEvent.EntityMultiPlaceEvent) {
            handleBlockChange(event, event.level, event.pos, event.entity)
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        fun onFarmlandTrample(event: BlockEvent.FarmlandTrampleEvent) {
            handleBlockChange(event, event.level, event.pos, event.entity)
        }

//        @SubscribeEvent(priority = EventPriority.HIGHEST)
//        fun onNeighborNotify(event: BlockEvent.NeighborNotifyEvent) {
//
//        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        fun onRightClickBlock(event: PlayerInteractEvent.RightClickBlock) {
            if (event.level.isClientSide) {
                return
            }

            val pos = getPossibleBlockChangePos(
                event.entity,
                event.hand,
                event.pos,
                event.hitVec,
            ) ?: return

            handleBlockChange(event, event.level, pos, event.entity)
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        fun onRightClickItem(event: PlayerInteractEvent.RightClickItem) {
            if (event.level.isClientSide) {
                return
            }

            val pos = getPossibleBlockChangePos(
                event.entity,
                event.hand,
                event.pos,
                null,
            ) ?: return

            handleBlockChange(event, event.level, pos, event.entity)
        }

        fun getPossibleBlockChangePos(
            player: Player,
            hand: InteractionHand,
            origPos: BlockPos,
            hitResult: BlockHitResult?
        ): BlockPos? {
            if (player.isCreative) {
                return null
            }

            val level = player.level()
            val item = player.getItemInHand(hand).item

            return when (item) {
                is BucketItem -> {
                    val hitResult = Item.getPlayerPOVHitResult(
                        level,
                        player,
                        if (item.content == Fluids.EMPTY) ClipContext.Fluid.SOURCE_ONLY else ClipContext.Fluid.NONE,
                    )
                    val posLookAt = hitResult.blockPos
                    val posRelative = posLookAt.relative(hitResult.direction)

                    if (item.content == Fluids.EMPTY) {
                        posLookAt
                    } else {
                        val blockState = level.getBlockState(origPos)
                        val block = blockState.block
                        if (block is LiquidBlockContainer && block.canPlaceLiquid(
                                player,
                                level,
                                posLookAt,
                                blockState,
                                item.content
                            )
                        ) {
                            posLookAt
                        } else {
                            posRelative
                        }
                    }
                }

                is SolidBucketItem -> item.updatePlacementContext(
                    BlockPlaceContext(
                        UseOnContext(
                            player,
                            hand,
                            hitResult ?: return null,
                        )
                    )
                )?.clickedPos ?: return null

                is WrenchItem -> origPos
                else -> null
            }
        }

        private fun handleBlockChange(event: Event, level: LevelAccessor, pos: BlockPos, entity: Entity? = null) {
            if (level.isClientSide) {
                return
            }

            if (entity is Player && entity.isCreative) {
                return
            }

            if (event !is ICancellableEvent) {
                return
            }

            val level = level as? ServerLevel ?: return
            val takeEffect = level.poiManager.getInRange(
                { it.value() == ModPoiTypes.guardianChamber },
                pos,
                MAX_EFFECT_RANGE.toInt(),
                PoiManager.Occupancy.ANY,
            ).asSequence()
                .mapNotNull { level.getBlockEntity(it.pos, ModBlockEntityTypes.guardianChamber).getOrNull() }
                .any { it.shouldTakeEffect(pos, entity) }

            if (takeEffect) {
                event.isCanceled = true
            }
        }
    }

    override fun getRotationAxis(state: BlockState) = Direction.Axis.Y

    override fun hasShaftTowards(world: LevelReader, pos: BlockPos, state: BlockState, face: Direction): Boolean {
        return face == Direction.DOWN
    }
}