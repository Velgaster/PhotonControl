package io.github.itsilya.photoncontrol.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import io.github.itsilya.photoncontrol.mixins.LightingProviderAccessor
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.LiteralText
import net.minecraft.util.math.ChunkPos
import net.minecraft.world.chunk.light.ChunkLightProvider
import net.minecraft.world.chunk.light.LightingProvider

class PhotonControlCommand(dispatcher: CommandDispatcher<ServerCommandSource>) {
    init {
        val builder = CommandManager.literal("pc")

        builder.then(
            CommandManager.literal("test").executes(::test)
        )

        dispatcher.register(builder)
    }

    private fun test(ctx: CommandContext<ServerCommandSource>): Int {
        val player = ctx.source.player
        val world: ServerWorld = player.serverWorld
        val pos = player.blockPos
        val chunkPos = ChunkPos(pos)

        val lightingProvider: LightingProvider = world.lightingProvider
        val chunkLightingProvider: ChunkLightProvider<*, *> =
            (lightingProvider as LightingProviderAccessor).chunkLightProvider

//        lightingProvider.setLightEnabled(chunkPos, true)
//        lightingProvider.setRetainData(chunkPos, true)
//        lightingProvider.addLightSource(pos, 15)
//        lightingProvider.updateSectionStatus(ChunkSectionPos.from(pos), true)
//        lightingProvider.setColumnEnabled(chunkPos, true)
//        world.chunkManager.markForUpdate(pos)

        chunkLightingProvider.addLightSource(pos, 15)
        world.chunkManager.markForUpdate(pos)

        ctx.source.sendFeedback(LiteralText("Test Done"), false)
        return 1
    }
}