/*
 * <copyright file="RealTimeHandler.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.handlers.realtime

import com.google.gson.Gson
import ir.firoozehcorp.gameservice.core.GameService
import ir.firoozehcorp.gameservice.core.sockets.GProtocolClient
import ir.firoozehcorp.gameservice.core.sockets.GsUdpClient
import ir.firoozehcorp.gameservice.handlers.HandlerCore
import ir.firoozehcorp.gameservice.handlers.realtime.request.*
import ir.firoozehcorp.gameservice.handlers.realtime.response.*
import ir.firoozehcorp.gameservice.handlers.turnbased.TurnBasedHandler
import ir.firoozehcorp.gameservice.models.GameServiceException
import ir.firoozehcorp.gameservice.models.enums.gsLive.GProtocolSendType
import ir.firoozehcorp.gameservice.models.enums.gsLive.GSLiveType
import ir.firoozehcorp.gameservice.models.gsLive.APacket
import ir.firoozehcorp.gameservice.models.gsLive.Room
import ir.firoozehcorp.gameservice.models.gsLive.command.StartPayload
import ir.firoozehcorp.gameservice.models.gsLive.realtime.Packet
import ir.firoozehcorp.gameservice.models.internal.interfaces.GameServiceCallback
import ir.firoozehcorp.gameservice.models.listeners.CoreListeners
import ir.firoozehcorp.gameservice.utils.GsLiveSystemObserver

/**
 * @author Alireza Ghodrati
 */
internal class RealTimeHandler(payload: StartPayload) : HandlerCore() {


    private var client: GsUdpClient = GsUdpClient(payload.area)
    private lateinit var responseHandlers: MutableMap<Int, IResponseHandler>
    private lateinit var requestHandlers: MutableMap<String, IRequestHandler>


    companion object {
        var PlayerHash: String = ""
        val PlayToken: String? = GameService.PlayToken
        var CurrentRoom: Room? = null
        val gson: Gson = Gson()
    }

    init {
        CurrentRoom = payload.room
        observer = GsLiveSystemObserver(GSLiveType.RealTime)

        setListeners()

        initRequestMessageHandlers()
        initResponseMessageHandlers()
    }


    private fun initRequestMessageHandlers() {
        requestHandlers = mutableMapOf(
                AuthorizationHandler.signature to AuthorizationHandler(),
                GetMemberHandler.signature to GetMemberHandler(),
                LeaveRoomHandler.signature to LeaveRoomHandler(),
                SendPublicMessageHandler.signature to SendPublicMessageHandler(),
                SendPrivateMessageHandler.signature to SendPrivateMessageHandler()
        )
    }


    private fun initResponseMessageHandlers() {
        responseHandlers = mutableMapOf(
                AuthResponseHandler.action to AuthResponseHandler(),
                ErrorResponseHandler.action to ErrorResponseHandler(),
                JoinRoomResponseHandler.action to JoinRoomResponseHandler(),
                LeaveRoomResponseHandler.action to LeaveRoomResponseHandler(),
                MemberDetailsResponseHandler.action to MemberDetailsResponseHandler(),
                PrivateMessageResponseHandler.action to PrivateMessageResponseHandler(),
                PublicMessageResponseHandler.action to PublicMessageResponseHandler()
        )
    }


    private fun setListeners() {
        client.onError += object : GProtocolClient.ErrorListener {
            override fun invoke(element: GameServiceException, from: Class<*>?) {
                if (disposed) return
                init()
            }
        }
        client.onDataReceived += object : GProtocolClient.DataReceivedListener {
            override fun invoke(element: Pair<Packet, GProtocolSendType>, from: Class<*>?) {
                GameService.SynchronizationContext?.send({
                    responseHandlers[element.first.action]?.handlePacket(element.first, element.second, gson)
                }, null)
            }
        }

        CoreListeners.Authorized += object : CoreListeners.AuthorisationListener {
            override fun invoke(element: String, from: Class<*>?) {
                if (from != AuthResponseHandler::class.java) return
                TurnBasedHandler.PlayerHash = element
                client.updatePwd(element)
            }
        }
        CoreListeners.GProtocolConnected += object : CoreListeners.GProtocolListener {
            override fun invoke(element: Void?, from: Class<*>?) {
                request(AuthorizationHandler.signature, type = GProtocolSendType.Reliable)
            }
        }
    }

    public override fun init() {
        client.init(object : GameServiceCallback<Boolean> {
            override fun onFailure(error: GameServiceException) {}
            override fun onResponse(response: Boolean) {
                client.startReceiving()
            }
        })
    }

    public override fun request(handlerName: String, payload: Any?) {
    }

    public override fun request(handlerName: String, payload: Any?, type: GProtocolSendType) {
        requestHandlers[handlerName]?.handleAction(payload)?.let { send(it, type) }
    }

    override fun send(packet: APacket) {
    }

    override fun send(packet: APacket, type: GProtocolSendType) {
        if (!observer.increase()) return
        client.send(packet, type)
    }

    override fun close() {
        disposed = true
        isFirstInit = false
        client.stopReceiving()
        observer.dispose()
    }

}