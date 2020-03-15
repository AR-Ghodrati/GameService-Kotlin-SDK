/*
 * <copyright file="CommandHandler.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.handlers.command

import com.google.gson.Gson
import ir.firoozehcorp.gameservice.core.GameService
import ir.firoozehcorp.gameservice.core.sockets.GsTcpClient
import ir.firoozehcorp.gameservice.handlers.command.request.*
import ir.firoozehcorp.gameservice.handlers.command.resposne.IResponseHandler
import ir.firoozehcorp.gameservice.models.consts.Command
import ir.firoozehcorp.gameservice.models.enums.gsLive.GSLiveType
import ir.firoozehcorp.gameservice.utils.GsLiveSystemObserver
import java.io.Closeable

/**
 * @author Alireza Ghodrati
 */
internal class CommandHandler : Closeable {


    private val tcpClient: GsTcpClient
    private val observer: GsLiveSystemObserver
    private var _isDisposed = false
    private var _isFirstInit = false


    private lateinit var responseHandlers: MutableMap<String, IResponseHandler>
    private lateinit var requestHandlers: MutableMap<String, IRequestHandler>


    companion object {
        var PlayerHash: String = ""
        val GameId: String? = GameService.CurrentGame?.id
        val UserToken: String? = GameService.UserToken
        val gson: Gson = Gson()
    }

    init {
        tcpClient = GsTcpClient(Command.area)
        observer = GsLiveSystemObserver(GSLiveType.Core)

        initRequestMessageHandlers()
        initResponseMessageHandlers()
    }


    private fun initRequestMessageHandlers() {
        requestHandlers = mutableMapOf(
                AcceptInviteHandler.signature to AcceptInviteHandler(),
                AuthorizationHandler.signature to AuthorizationHandler(),
                AutoMatchHandler.signature to AutoMatchHandler(),
                CancelAutoMatchHandler.signature to CancelAutoMatchHandler(),
                FindUserHandler.signature to FindUserHandler(),
                GetRoomsHandler.signature to GetRoomsHandler(),
                InviteListHandler.signature to InviteListHandler(),
                InviteUserHandler.signature to InviteUserHandler(),
                JoinRoomHandler.signature to JoinRoomHandler(),
                PingPongHandler.signature to PingPongHandler(),
                SendChannelMessageHandler.signature to SendChannelMessageHandler(),
                SubscribeChannelHandler.signature to SubscribeChannelHandler(),
                UnSubscribeChannelHandler.signature to UnSubscribeChannelHandler()
        )
    }


    private fun initResponseMessageHandlers() {


    }

    override fun close() {

    }

}