package io.github.itsilya.photoncontrol

import io.github.itsilya.photoncontrol.command.PhotonControlCommand
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import net.minecraft.SharedConstants

class PhotonControl : ModInitializer {

    override fun onInitialize() {

        SharedConstants.isDevelopment = true

        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher, _ ->
            PhotonControlCommand(dispatcher)
        })

    }

}