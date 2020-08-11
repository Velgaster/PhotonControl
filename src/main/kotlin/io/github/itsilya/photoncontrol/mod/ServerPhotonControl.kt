package io.github.itsilya.photoncontrol.mod

import io.github.itsilya.photoncontrol.AbstractPhotonControl
import net.fabricmc.api.DedicatedServerModInitializer
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.server.MinecraftServer

class ServerPhotonControl: AbstractPhotonControl(), DedicatedServerModInitializer {
    override fun onInitializeServer() {
        this.init()
    }

    override val isClient = false

    override val server by lazy {
        FabricLoader.getInstance().gameInstance as MinecraftServer
    }
}