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

import com.google.gson.Gson
import ir.firoozehcorp.gameservice.core.GameService
import ir.firoozehcorp.gameservice.handlers.turnbased.request.*
import ir.firoozehcorp.gameservice.handlers.turnbased.response.*
import ir.firoozehcorp.gameservice.models.enums.gsLive.GSLiveType
import ir.firoozehcorp.gameservice.models.gsLive.Room
import ir.firoozehcorp.gameservice.models.gsLive.command.StartPayload
import ir.firoozehcorp.gameservice.utils.GsLiveSystemObserver
import java.io.Closeable

/**
 * @author Alireza Ghodrati
 */
internal class TurnBasedHandler(payload: StartPayload) : Closeable {

    private val observer: GsLiveSystemObserver
    private var _isDisposed = false
    private var _isFirstInit = false


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
        observer = GsLiveSystemObserver(GSLiveType.TurnBased)

        initRequestMessageHandlers()
        initResponseMessageHandlers()
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

    override fun close() {

    }
}