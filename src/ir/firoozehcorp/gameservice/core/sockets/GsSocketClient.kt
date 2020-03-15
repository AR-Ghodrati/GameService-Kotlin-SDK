/*
 * <copyright file="GsSocketClient.kt" company="Firoozeh Technology LTD">
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
import ir.firoozehcorp.gameservice.models.enums.gsLive.GSLiveType
import ir.firoozehcorp.gameservice.models.gsLive.command.Area
import ir.firoozehcorp.gameservice.models.gsLive.command.Packet
import ir.firoozehcorp.gameservice.models.internal.interfaces.GameServiceCallback
import java.io.BufferedReader
import java.io.DataOutputStream
import java.net.Socket
import java.util.concurrent.atomic.AtomicBoolean


/**
 * @author Alireza Ghodrati
 */
internal abstract class GsSocketClient {

    protected lateinit var socket: Socket
    protected var gson: Gson = Gson()
    protected lateinit var input: BufferedReader
    protected lateinit var out: DataOutputStream
    protected lateinit var isRunning: AtomicBoolean
    protected lateinit var endpoint: Area

    protected var isLogEnable = false


    protected val l_START = '{'
    protected val l_END = '}'


    protected abstract fun init(callback: GameServiceCallback<Boolean>)
    protected abstract fun startReceiving(callback: GameServiceCallback<Packet>)
    protected abstract fun stopReceiving()
    protected abstract fun updatePwd(newPwd: String?)
    protected abstract fun setType(type: GSLiveType?)
    protected abstract fun send(packet: Packet?)
    protected abstract fun isAvailable(): Boolean

}