package io.github.itsilya.photoncontrol.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import io.github.itsilya.photoncontrol.PhotonControl
import io.github.itsilya.photoncontrol.mixins.LightingProviderAccessor
import net.fabricmc.fabric.mixin.event.lifecycle.MinecraftServerMixin
import net.minecraft.client.MinecraftClient
import net.minecraft.network.packet.s2c.play.LightUpdateS2CPacket
import net.minecraft.server.MinecraftServer
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
        val commandMap = mapOf<String, (CommandContext<ServerCommandSource>)->Int >(
                "set" to ::setlight,
                "update" to ::updateclient
        )
        commandMap.forEach{
            builder.then(CommandManager.literal(it.key).executes(it.value))
        }
        dispatcher.register(builder)
    }


    private fun updateclient(ctx: CommandContext<ServerCommandSource>): Int {
        val player = ctx.source.player
        val world: ServerWorld = player.serverWorld
        val pos = player.blockPos
        val chunkPos = ChunkPos(pos)
        val lightingProvider: LightingProvider = world.lightingProvider
        world.chunkManager.sendToNearbyPlayers(player, LightUpdateS2CPacket(chunkPos, lightingProvider, true))
        ctx.source.sendFeedback(LiteralText("Client Updated."), false)
        return 1
    }

    private fun setlight(ctx: CommandContext<ServerCommandSource>): Int {
        val player = ctx.source.player
        val world: ServerWorld = player.serverWorld
        val pos = player.blockPos

        val lightingProvider: LightingProvider = world.lightingProvider
        val chunkLightingProvider: ChunkLightProvider<*, *> =
            (lightingProvider as LightingProviderAccessor).chunkLightProvider

        chunkLightingProvider.addLightSource(pos, 15)
        chunkLightingProvider.doLightUpdates(100,false,true)

        ctx.source.sendFeedback(LiteralText("light source set at $pos"), false)
        return 1
    }
}