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
import ir.firoozehcorp.gameservice.handlers.command.request.chat.*
import ir.firoozehcorp.gameservice.models.GameServiceException
import ir.firoozehcorp.gameservice.models.annotations.NotNull
import ir.firoozehcorp.gameservice.models.gsLive.command.RoomDetail

/**
 * Represents Game Service Chat System
 * @author Alireza Ghodrati
 */
object GSLiveChat {


    /**
     * Subscribe In Channel With channelName.
     * @param channelName Name of Channel You want To Subscribe
     */
    @Throws(GameServiceException::class)
    fun subscribeChannel(@NotNull channelName: String) {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (channelName.isEmpty()) throw GameServiceException("channelName Cant Be EmptyOrNull")

        GSLive.handler.commandHandler.request(SubscribeChannelHandler.signature, channelName)
    }


    /**
     * UnSubscribeChannel With channelName.
     * @param channelName Name of Channel You want To UnSubscribe
     */
    @Throws(GameServiceException::class)
    fun unSubscribeChannel(@NotNull channelName: String) {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (channelName.isEmpty()) throw GameServiceException("channelName Cant Be EmptyOrNull")

        GSLive.handler.commandHandler.request(UnSubscribeChannelHandler.signature, channelName)
    }


    /**
     * Send Message In SubscribedChannel.
     * @param channelName Name of Channel You want To Send Message
     * @param message Message Data
     */
    @Throws(GameServiceException::class)
    fun sendChannelMessage(@NotNull channelName: String, @NotNull message: String) {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (channelName.isEmpty() && message.isEmpty()) throw GameServiceException("channelName Or message Cant Be EmptyOrNull")

        GSLive.handler.commandHandler.request(SendChannelMessageHandler.signature, Pair(channelName, message))
    }


    /**
     * Send Message In SubscribedChannel.
     * @param memberId ID of Member You want To Send Message
     * @param message Message Data
     */
    @Throws(GameServiceException::class)
    fun sendPrivateMessage(@NotNull memberId: String, @NotNull message: String) {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (memberId.isEmpty() && message.isEmpty()) throw GameServiceException("memberId Or message Cant Be EmptyOrNull")

        GSLive.handler.commandHandler.request(SendPrivateMessageHandler.signature, Pair(memberId, message))
    }


    /**
     *  Get Channels Subscribe List
     */
    @Throws(GameServiceException::class)
    fun getChannelsSubscribed() {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")

        GSLive.handler.commandHandler.request(GetChannelsSubscribedHandler.signature)
    }


    /**
     *  Get Channel last 30 Messages.
     * @param channelName Name of Channel You want To Get last 30 Messages
     */
    @Throws(GameServiceException::class)
    fun getChannelRecentMessages(@NotNull channelName: String) {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (channelName.isEmpty()) throw GameServiceException("channelName Cant Be EmptyOrNull")

        GSLive.handler.commandHandler.request(GetChannelRecentMessagesRequestHandler.signature, RoomDetail().apply { id = channelName })
    }


    /**
     *    Get Channel Members
     * @param channelName Name of Channel You want To Get Members
     * @param skip The skip value
     * @param limit (Max = 15) The Limit value
     */
    @Throws(GameServiceException::class)
    fun getChannelMembers(@NotNull channelName: String, skip: Int, limit: Int) {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (channelName.isEmpty()) throw GameServiceException("channelName Cant Be EmptyOrNull")
        if (limit <= 0 || limit > 15) throw GameServiceException("invalid Limit Value")
        if (skip < 0) throw GameServiceException("invalid Skip Value")

        GSLive.handler.commandHandler.request(GetChannelsMembersRequestHandler.signature, RoomDetail()
                .apply {
                    id = channelName
                    min = skip
                    max = limit
                }
        )
    }


    /**
     *  Get Your Pending Messages
     */
    @Throws(GameServiceException::class)
    fun getPendingMessages() {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")

        GSLive.handler.commandHandler.request(GetPendingMessagesRequestHandler.signature)
    }


}