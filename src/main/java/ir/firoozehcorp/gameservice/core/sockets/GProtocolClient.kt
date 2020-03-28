/*
 * <copyright file="GProtocolClient.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.core.sockets

import com.google.gson.Gson
import ir.firoozehcorp.gameservice.models.GameServiceException
import ir.firoozehcorp.gameservice.models.enums.gsLive.GProtocolSendType
import ir.firoozehcorp.gameservice.models.enums.gsLive.GSLiveType
import ir.firoozehcorp.gameservice.models.gsLive.APacket
import ir.firoozehcorp.gameservice.models.gsLive.command.Area
import ir.firoozehcorp.gameservice.models.gsLive.realtime.Packet
import ir.firoozehcorp.gameservice.models.internal.EventHandler
import ir.firoozehcorp.gameservice.models.internal.interfaces.GameServiceCallback


/**
 * @author Alireza Ghodrati
 */
internal abstract class GProtocolClient {

    internal interface ErrorListener : EventHandler.IEventHandler<GameServiceException> {
        override fun invoke(element: GameServiceException, from: Class<*>?)
    }

    internal interface DataReceivedListener : EventHandler.IEventHandler<Pair<Packet, GProtocolSendType>> {
        override fun invoke(element: Pair<Packet, GProtocolSendType>, from: Class<*>?)
    }

    protected var gson: Gson = Gson()
    protected lateinit var endpoint: Area

    protected var IsAvailable = false

    val onError: EventHandler<ErrorListener, GameServiceException> = EventHandler()
    val onDataReceived: EventHandler<DataReceivedListener, Pair<Packet, GProtocolSendType>> = EventHandler()


    protected abstract fun init(callback: GameServiceCallback<Boolean>)
    protected abstract fun startReceiving()
    protected abstract fun stopReceiving()
    protected abstract fun updatePwd(newPwd: String?)
    protected abstract fun setType(type: GSLiveType?)
    protected abstract fun send(packet: APacket?, type: GProtocolSendType)
    protected abstract fun isAvailable(): Boolean

}