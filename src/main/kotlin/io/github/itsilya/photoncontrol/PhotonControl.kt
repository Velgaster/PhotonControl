package io.github.itsilya.photoncontrol

import net.minecraft.server.MinecraftServer
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import org.apache.logging.log4j.Logger

interface PhotonControl {
    val logger: Logger
    val server: MinecraftServer
    val isClient: Boolean

    companion object {
        private var implementation: AbstractPhotonControl? = null

        val instance: AbstractPhotonControl
            get() = implementation ?: throw IllegalStateException("It's too soon to get the instance of PhotonControl")
    }

    fun init()

    fun setLight(world: ServerWorld, pos: BlockPos, level: Int, chunkPos: ChunkPos)
}