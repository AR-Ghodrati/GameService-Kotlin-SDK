/*
 * <copyright file="ChatListeners.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.handlers

import ir.firoozehcorp.gameservice.models.gsLive.chat.Chat

/**
 * Represents ChatEventHandlers
 * @author Alireza Ghodrati
 */
object ChatListeners {

    interface ChatReceivedListener {
        fun onChatReceived(newChat: Chat)
    }

    interface SubscribeChannelListener {
        fun onSubscribeChannel(channelName: String)
    }

    interface UnSubscribeChannelListener {
        fun onUnSubscribeChannel(channelName: String)
    }


    private var chatReceivedListeners: MutableList<ChatReceivedListener>? = null
    private var subscribeChannelListeners: MutableList<SubscribeChannelListener>? = null
    private var unSubscribeChannelListeners: MutableList<UnSubscribeChannelListener>? = null


    /**
     * Calls When New Chat Received
     */
    fun addChatReceivedListener(listener: ChatReceivedListener) {
        if (chatReceivedListeners == null) chatReceivedListeners = mutableListOf(listener)
        else {
            chatReceivedListeners?.let {
                if (!it.contains(listener)) it.add(listener)
            }
        }
    }

    fun removeChatReceivedListener(listener: ChatReceivedListener) {
        chatReceivedListeners?.let {
            if (it.contains(listener)) it.remove(listener)
        }
    }


    /**
     * Calls When Current Player Subscribe Channel
     */
    fun addSubscribeChannelListener(listener: SubscribeChannelListener) {
        if (subscribeChannelListeners == null) subscribeChannelListeners = mutableListOf(listener)
        else {
            subscribeChannelListeners?.let {
                if (!it.contains(listener)) it.add(listener)
            }
        }
    }

    fun removeSubscribeChannelListener(listener: SubscribeChannelListener) {
        subscribeChannelListeners?.let {
            if (it.contains(listener)) it.remove(listener)
        }
    }


    /**
     * Calls When Current Player UnSubscribe Channel
     */
    fun addUnSubscribeChannelListener(listener: UnSubscribeChannelListener) {
        if (unSubscribeChannelListeners == null) unSubscribeChannelListeners = mutableListOf(listener)
        else {
            unSubscribeChannelListeners?.let {
                if (!it.contains(listener)) it.add(listener)
            }
        }
    }

    fun removeUnSubscribeChannelListener(listener: UnSubscribeChannelListener) {
        unSubscribeChannelListeners?.let {
            if (it.contains(listener)) it.remove(listener)
        }
    }


    internal fun invokeChatReceivedListeners(newChat: Chat) {
        chatReceivedListeners?.forEach {
            it.onChatReceived(newChat)
        }
    }

    internal fun invokeSubscribeChannelListeners(name: String) {
        subscribeChannelListeners?.forEach {
            it.onSubscribeChannel(name)
        }
    }

    internal fun invokeUnSubscribeChannelListeners(name: String) {
        unSubscribeChannelListeners?.forEach {
            it.onUnSubscribeChannel(name)
        }
    }
}