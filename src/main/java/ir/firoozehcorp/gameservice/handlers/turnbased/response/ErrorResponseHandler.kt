/*
 * <copyright file="ErrorResponseHandler.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.handlers.turnbased.response

import com.google.gson.Gson
import ir.firoozehcorp.gameservice.models.consts.TurnBase
import ir.firoozehcorp.gameservice.models.enums.gsLive.GSLiveType
import ir.firoozehcorp.gameservice.models.gsLive.command.ErrorEvent
import ir.firoozehcorp.gameservice.models.gsLive.command.Packet
import ir.firoozehcorp.gameservice.models.listeners.CoreListeners


/**
 * @author Alireza Ghodrati
 */
internal class ErrorResponseHandler : BaseResponseHandler() {

    companion object {
        const val action = TurnBase.Errors
    }

    override fun handleResponse(packet: Packet, jsonHandler: Gson) {
        CoreListeners.Error.invokeListeners(ErrorEvent().apply {
            this.type = GSLiveType.TurnBased
            this.error = packet.message.toString()
        }, javaClass)
    }
}