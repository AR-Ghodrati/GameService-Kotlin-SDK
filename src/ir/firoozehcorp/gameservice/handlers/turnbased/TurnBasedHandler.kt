/*
 * <copyright file="TurnBasedHandler.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.handlers.turnbased

import ir.firoozehcorp.gameservice.core.GameService
import ir.firoozehcorp.gameservice.core.sockets.GsSocketClient
import ir.firoozehcorp.gameservice.core.sockets.GsTcpClient
import ir.firoozehcorp.gameservice.handlers.HandlerCore
import ir.firoozehcorp.gameservice.handlers.turnbased.request.*
import ir.firoozehcorp.gameservice.handlers.turnbased.response.*
import ir.firoozehcorp.gameservice.models.GameServiceException
import ir.firoozehcorp.gameservice.models.enums.gsLive.GProtocolSendType
import ir.firoozehcorp.gameservice.models.enums.gsLive.GSLiveType
import ir.firoozehcorp.gameservice.models.gsLive.APacket
import ir.firoozehcorp.gameservice.models.gsLive.Room
import ir.firoozehcorp.gameservice.models.gsLive.command.Packet
import ir.firoozehcorp.gameservice.models.gsLive.command.StartPayload
import ir.firoozehcorp.gameservice.models.internal.interfaces.GameServiceCallback
import ir.firoozehcorp.gameservice.models.listeners.CoreListeners
import ir.firoozehcorp.gameservice.utils.GsLiveSystemObserver

/**
 * @author Alireza Ghodrati
 */
internal class TurnBasedHandler(payload: StartPayload) : HandlerCore() {

    private val tcpClient: GsTcpClient = GsTcpClient(payload.area)


    private lateinit var responseHandlers: MutableMap<Int, IResponseHandler>
    private lateinit var requestHandlers: MutableMap<String, IRequestHandler>


    companion object {
        var PlayerHash: String = ""
        val PlayToken: String? = GameService.PlayToken
        var CurrentRoom: Room? = null
    }

    init {
        CurrentRoom = payload.room
        observer = GsLiveSystemObserver(GSLiveType.TurnBased)

        setListeners()

        initRequestMessageHandlers()
        initResponseMessageHandlers()
    }


    private fun setListeners() {

        tcpClient.onError += object : GsSocketClient.ErrorListener {
            override fun invoke(element: GameServiceException, from: Class<*>?) {
                if (disposed) return
                init()
            }
        }
        tcpClient.onDataReceived += object : GsSocketClient.DataReceivedListener {
            override fun invoke(element: Packet, from: Class<*>?) {
                GameService.SynchronizationContext?.send({
                    responseHandlers[element.action]?.handlePacket(element, gson)
                }, null)
            }
        }

        CoreListeners.Ping += object : CoreListeners.PingListener {
            override fun invoke(element: Void?, from: Class<*>?) {
                if (from != PingResponseHandler::class.java) return
                request(PingPongHandler.signature)
            }
        }
        CoreListeners.Authorized += object : CoreListeners.AuthorisationListener {
            override fun invoke(element: String, from: Class<*>?) {
                if (from != AuthResponseHandler::class.java) return
                PlayerHash = element
                tcpClient.updatePwd(element)
            }
        }
    }

    private fun initRequestMessageHandlers() {
        requestHandlers = mutableMapOf(
                AuthorizationHandler.signature to AuthorizationHandler(),
                ChooseNextHandler.signature to ChooseNextHandler(),
                CompleteHandler.signature to CompleteHandler(),
                CurrentTurnHandler.signature to CurrentTurnHandler(),
                FinishHandler.signature to FinishHandler(),
                GetMemberHandler.signature to GetMemberHandler(),
                LeaveRoomHandler.signature to LeaveRoomHandler(),
                PingPongHandler.signature to PingPongHandler(),
                TakeTurnHandler.signature to TakeTurnHandler()
        )
    }

    private fun initResponseMessageHandlers() {
        responseHandlers = mutableMapOf(
                AuthResponseHandler.action to AuthResponseHandler(),
                ChooseNextResponseHandler.action to ChooseNextResponseHandler(),
                CompleteResponseHandler.action to CompleteResponseHandler(),
                CurrentTurnResponseHandler.action to CurrentTurnResponseHandler(),
                ErrorResponseHandler.action to ErrorResponseHandler(),
                FinishResponseHandler.action to FinishResponseHandler(),
                JoinRoomResponseHandler.action to JoinRoomResponseHandler(),
                PingResponseHandler.action to PingResponseHandler(),
                LeaveRoomResponseHandler.action to LeaveRoomResponseHandler(),
                MemberDetailsResponseHandler.action to MemberDetailsResponseHandler(),
                TakeTurnResponseHandler.action to TakeTurnResponseHandler()
        )

    }


    public override fun init() {
        tcpClient.init(object : GameServiceCallback<Boolean> {
            override fun onFailure(error: GameServiceException) {}
            override fun onResponse(response: Boolean) {
                tcpClient.startReceiving()
            }
        })
    }

    public override fun request(handlerName: String, payload: Any?) {
        requestHandlers[handlerName]?.handleAction(payload)?.let { send(it) }
    }

    override fun request(handlerName: String, payload: Any?, type: GProtocolSendType) {

    }

    override fun send(packet: APacket) {
        if (!observer.increase()) return
        tcpClient.send(packet)
    }

    override fun close() {
        disposed = true
        isFirstInit = false
        tcpClient.stopReceiving()
        observer.dispose()
    }
}