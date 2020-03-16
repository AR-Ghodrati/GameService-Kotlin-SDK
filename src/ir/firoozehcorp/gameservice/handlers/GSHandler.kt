/*
 * <copyright file="GSHandler.kt" company="Firoozeh Technology LTD">
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

import ir.firoozehcorp.gameservice.handlers.command.CommandHandler
import ir.firoozehcorp.gameservice.handlers.realtime.RealTimeHandler
import ir.firoozehcorp.gameservice.handlers.realtime.request.LeaveRoomHandler
import ir.firoozehcorp.gameservice.handlers.turnbased.TurnBasedHandler
import ir.firoozehcorp.gameservice.models.enums.gsLive.GSLiveType
import ir.firoozehcorp.gameservice.models.gsLive.command.StartPayload
import ir.firoozehcorp.gameservice.models.listeners.CoreListeners
import java.io.Closeable

/**
 * @author Alireza Ghodrati
 */
internal class GSHandler : Closeable {

    var commandHandler: CommandHandler = CommandHandler()
    var turnBasedHandler: TurnBasedHandler? = null
    var realTimeHandler: RealTimeHandler? = null


    init {

        CoreListeners.GsLiveSystemStarted += object : CoreListeners.GSLiveSystemListener {
            override fun invoke(element: StartPayload, from: Class<*>?) {
                when (element.room.type) {
                    GSLiveType.TurnBased -> connectToTurnBased(element)
                    GSLiveType.RealTime -> connectToRealTime(element)
                    else -> {
                    }
                }
            }
        }

        CoreListeners.Dispose += object : CoreListeners.DisposeListener {
            override fun invoke(element: Void?, from: Class<*>?) {
                when (from) {
                    RealTimeHandler::class.java -> realTimeHandler = null
                    TurnBasedHandler::class.java -> turnBasedHandler = null
                }
            }
        }
    }


    private fun connectToTurnBased(startPayload: StartPayload) {
        if (turnBasedHandler != null) {
            turnBasedHandler?.request(ir.firoozehcorp.gameservice.handlers.turnbased.request.LeaveRoomHandler.signature)
            turnBasedHandler?.close()
        }

        turnBasedHandler = TurnBasedHandler(startPayload)
        turnBasedHandler?.init()
    }


    private fun connectToRealTime(startPayload: StartPayload) {

        if (realTimeHandler != null) {
            realTimeHandler?.request(LeaveRoomHandler.signature)
            realTimeHandler?.close()
        }

        realTimeHandler = RealTimeHandler(startPayload)
        realTimeHandler?.init()
    }

    override fun close() {
        commandHandler.close()
        realTimeHandler?.close()
        turnBasedHandler?.close()
    }


}