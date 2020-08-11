package io.github.itsilya.photoncontrol

import io.github.itsilya.photoncontrol.command.PhotonControlCommand
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.util.math.ChunkSectionPos
import net.minecraft.world.chunk.light.LightingProvider
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

abstract class AbstractPhotonControl() : PhotonControl {
    val name = "PhotonControl"
    override val logger: Logger = LogManager.getLogger(PhotonControl::class.java)

    override fun init() {
        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher, _ ->
            PhotonControlCommand.register(dispatcher)
        })
    }

    override fun setLight(world: ServerWorld, pos: BlockPos, level: Int, chunkPos: ChunkPos) {
        val lightingProvider: LightingProvider = world.lightingProvider
        lightingProvider.setLightEnabled(chunkPos, true)
        lightingProvider.addLightSource(pos, level)
        lightingProvider.updateSectionStatus(ChunkSectionPos.from(pos), true)

        world.chunkManager.markForUpdate(pos)
    }
}
