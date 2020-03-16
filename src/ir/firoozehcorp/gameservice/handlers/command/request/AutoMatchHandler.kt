/*
 * <copyright file="AutoMatchHandler.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.handlers.command.request

import ir.firoozehcorp.gameservice.core.gslive.GSLiveOption
import ir.firoozehcorp.gameservice.handlers.HandlerCore.Companion.gson
import ir.firoozehcorp.gameservice.handlers.command.CommandHandler
import ir.firoozehcorp.gameservice.models.consts.Command
import ir.firoozehcorp.gameservice.models.gsLive.command.Packet
import ir.firoozehcorp.gameservice.models.gsLive.command.RoomDetail

/**
 * @author Alireza Ghodrati
 */
internal class AutoMatchHandler : BaseRequestHandler() {

    companion object {
        const val signature = "AUTO_MATCH"
    }


    private fun doAction(option: GSLiveOption.AutoMatchOption): Packet {
        return Packet(CommandHandler.PlayerHash
                , Command.ActionAutoMatch
                , gson.toJson(RoomDetail()
                .apply {
                    role = option.role
                    min = option.minPlayer
                    max = option.maxPlayer
                    type = Command.ActionAutoMatch
                    isPersist = option.isPersist
                    gsLiveType = option.gsLiveType.ordinal
                })
        )
    }


    override fun checkAction(payload: Any?): Boolean {
        return payload != null && payload is GSLiveOption.AutoMatchOption
    }

    override fun doAction(payload: Any?): Packet {
        if (!checkAction(payload)) throw IllegalArgumentException()
        return doAction(payload as GSLiveOption.AutoMatchOption)
    }

}