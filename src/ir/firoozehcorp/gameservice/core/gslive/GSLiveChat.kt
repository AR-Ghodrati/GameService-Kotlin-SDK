/*
 * <copyright file="GSLiveChat.kt" company="Firoozeh Technology LTD">
 * Copyright (C) 2020. Firoozeh Technology LTD. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </copyright>
 */

package ir.firoozehcorp.gameservice.core.gslive

import ir.firoozehcorp.gameservice.core.GameService
import ir.firoozehcorp.gameservice.handlers.command.request.SendChannelMessageHandler
import ir.firoozehcorp.gameservice.handlers.command.request.SubscribeChannelHandler
import ir.firoozehcorp.gameservice.handlers.command.request.UnSubscribeChannelHandler
import ir.firoozehcorp.gameservice.models.GameServiceException

/**
 * Represents Game Service Chat System
 * @author Alireza Ghodrati
 */
object GSLiveChat {


    /**
     * Subscribe In Channel With channelName.
     * @param channelName (NOTNULL)Name of Channel You want To Subscribe
     */
    @Throws(GameServiceException::class)
    fun subscribeChannel(channelName: String) {
        if (!GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (channelName.isEmpty()) throw GameServiceException("channelName Cant Be EmptyOrNull")

        GSLive.handler.commandHandler.request(SubscribeChannelHandler.signature, channelName)
    }


    /**
     * UnSubscribeChannel With channelName.
     * @param channelName (NOTNULL) Name of Channel You want To UnSubscribe
     */
    @Throws(GameServiceException::class)
    fun unSubscribeChannel(channelName: String) {
        if (!GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (channelName.isEmpty()) throw GameServiceException("channelName Cant Be EmptyOrNull")

        GSLive.handler.commandHandler.request(UnSubscribeChannelHandler.signature, channelName)
    }


    /**
     * Send Message In SubscribedChannel.
     * @param channelName (NOTNULL) Name of Channel You want To Send Message
     * @param message (NOTNULL)Message Data
     */
    @Throws(GameServiceException::class)
    fun sendChannelMessage(channelName: String, message: String) {
        if (!GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (channelName.isEmpty() && message.isEmpty()) throw GameServiceException("channelName Or message Cant Be EmptyOrNull")

        GSLive.handler.commandHandler.request(SendChannelMessageHandler.signature, Pair(channelName, message))
    }

}