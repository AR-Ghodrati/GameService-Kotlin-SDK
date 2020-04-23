/*
 * <copyright file="$this.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.models.listeners

import ir.firoozehcorp.gameservice.models.gsLive.chat.Chat
import ir.firoozehcorp.gameservice.models.internal.EventHandler

/**
 * Represents ChatListeners
 * @author Alireza Ghodrati
 */
class ChatListeners {


    interface ChatReceivedListener : EventHandler.IEventHandler<Chat> {
        override fun invoke(element: Chat, from: Class<*>?)
    }

    interface SubscribeChannelListener : EventHandler.IEventHandler<String> {
        override fun invoke(element: String, from: Class<*>?)
    }

    interface UnSubscribeChannelListener : EventHandler.IEventHandler<String> {
        override fun invoke(element: String, from: Class<*>?)
    }

    interface ChannelsSubscribedListener : EventHandler.IEventHandler<MutableList<String>> {
        override fun invoke(element: MutableList<String>, from: Class<*>?)
    }


    companion object {
        /**
         * Calls When New Chat Received
         */
        val NewChatReceived: EventHandler<ChatReceivedListener, Chat> = EventHandler()


        /**
         * Calls When Current Player Subscribe Channel
         */

        val SubscribedChannel: EventHandler<SubscribeChannelListener, String> = EventHandler()


        /**
         * Calls When Current Player UnSubscribe Channel
         */
        val UnSubscribedChannel: EventHandler<UnSubscribeChannelListener, String> = EventHandler()


        /**
         * Calls When Current Player Get Channels Subscribed List
         */
        val ChannelsSubscribed: EventHandler<ChannelsSubscribedListener, MutableList<String>> = EventHandler()
    }


}