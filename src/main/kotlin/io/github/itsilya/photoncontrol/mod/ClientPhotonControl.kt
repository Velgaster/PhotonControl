package io.github.itsilya.photoncontrol.mod

import io.github.itsilya.photoncontrol.AbstractPhotonControl
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.MinecraftClient
import net.minecraft.server.MinecraftServer
import java.lang.IllegalStateException

class ClientPhotonControl: AbstractPhotonControl(), ClientModInitializer {
    override fun onInitializeClient() {
        this.init()
    }

    override val isClient: Boolean = true

    private val client: MinecraftClient by lazy {
        FabricLoader.getInstance().gameInstance as MinecraftClient
    }

    override val server: MinecraftServer
        get() = client.server ?: throw IllegalStateException("Server not accessible from the Client now")
}