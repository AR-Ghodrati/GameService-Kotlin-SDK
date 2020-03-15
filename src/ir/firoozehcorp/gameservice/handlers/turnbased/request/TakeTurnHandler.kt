/*
 * <copyright file="TakeTurnHandler.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.handlers.turnbased.request

import ir.firoozehcorp.gameservice.handlers.turnbased.TurnBasedHandler
import ir.firoozehcorp.gameservice.handlers.turnbased.TurnBasedHandler.Companion.gson
import ir.firoozehcorp.gameservice.models.consts.TurnBase
import ir.firoozehcorp.gameservice.models.gsLive.command.Packet
import ir.firoozehcorp.gameservice.models.gsLive.turnbased.DataPayload


/**
 * @author Alireza Ghodrati
 */
internal class TakeTurnHandler : BaseRequestHandler() {

    companion object {
        const val signature = "TAKE_TURN"
    }


    private fun doAction(payload: DataPayload): Packet {
        return Packet(TurnBasedHandler.PlayToken, TurnBase.OnTakeTurn, gson.toJson(payload))
    }


    override fun checkAction(payload: Any?): Boolean {
        return payload is DataPayload
    }

    override fun doAction(payload: Any?): Packet {
        if (!checkAction(payload)) throw IllegalArgumentException()
        return doAction(payload as DataPayload)
    }

}