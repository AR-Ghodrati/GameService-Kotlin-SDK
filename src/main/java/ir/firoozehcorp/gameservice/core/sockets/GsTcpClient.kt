/*
 * <copyright file="GsTcpClient.kt" company="Firoozeh Technology LTD">
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

import com.google.gson.stream.JsonReader
import ir.firoozehcorp.gameservice.models.GameServiceException
import ir.firoozehcorp.gameservice.models.basicApi.CommandInfo
import ir.firoozehcorp.gameservice.models.enums.gsLive.GSLiveType
import ir.firoozehcorp.gameservice.models.gsLive.APacket
import ir.firoozehcorp.gameservice.models.gsLive.command.Area
import ir.firoozehcorp.gameservice.models.gsLive.command.Packet
import ir.firoozehcorp.gameservice.models.internal.interfaces.GameServiceCallback
import ir.firoozehcorp.gameservice.utils.LogUtil
import java.io.*
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketException
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.concurrent.thread


/**
 * @author Alireza Ghodrati
 */
internal class GsTcpClient(area: Area? = null) : GsSocketClient() {

    init {
        if (area?.protocol?.toUpperCase() != "TCP")
            throw GameServiceException("Only TCP Protocol Supported")
        endpoint = area
    }

    public override fun init(commandInfo: CommandInfo?,callback: GameServiceCallback<Boolean>) {
        try {
            thread(priority = Thread.MAX_PRIORITY) {
                val ip = if(commandInfo == null) endpoint.ip else commandInfo.ip
                val port = commandInfo?.port ?: endpoint.port

                socket = Socket()
                socket.connect(InetSocketAddress(InetAddress.getByName(ip), port), timeout)
                input = BufferedReader(InputStreamReader(socket.getInputStream(), "UTF-8"))
                out = DataOutputStream(socket.getOutputStream())
                isRunning = AtomicBoolean(true)
                LogUtil.logData("GsTcpClient -> init With IP: $ip,Port: $port")
                callback.onResponse(true)
            }
        } catch (e: Exception) {
            callback.onResponse(false)
        }
    }

    public override fun startReceiving() {
        thread(priority = Thread.MAX_PRIORITY) {
            try {
                while (isRunning.get()) {
                    var ch: Int
                    var start = 0
                    var current = 0
                    var index = 0

                    val data = StringBuilder()
                    while (input.read().also { ch = it } != -1) {
                        val c = ch.toChar()
                        data.append(c)
                        if (c == lStart) {
                            if (current == 0) start = index
                            current++
                        } else if (c == lEnd) {
                            current--
                            if (current == 0) {
                                val reader = JsonReader(StringReader(data.substring(start, index + 1)))
                                reader.isLenient = true
                                val packet: Packet = gson.fromJson(reader, Packet::class.java)
                                LogUtil.logData("GsTcpClient -> DataIn: $packet")
                                onDataReceived.invokeListeners(packet)
                            }
                        }
                        index++
                    }
                }
            } catch (e: Exception) {
                when (e) {
                    is SocketException, is IOException -> onError.invokeListeners(GameServiceException(e.message))
                    else -> {
                    }
                }
            }

        }
    }

    public override fun stopReceiving() {
        try {
            isRunning.set(false)
            socket.close()
            input.close()
            out.flush()
            out.close()
        } catch (e: IOException) {
        }
    }

    public override fun updatePwd(newPwd: String?) {
    }

    public override fun setType(type: GSLiveType?) {
    }

    public override fun send(packet: APacket?) {
        thread(priority = Thread.MAX_PRIORITY) {
            try {
                LogUtil.logData("GsTcpClient -> DataOut: $packet")
                out.write(gson
                        .toJson(packet)
                        .toByteArray(charset("UTF-8"))
                )
                out.flush()
            } catch (e: Exception) {
            }
        }
    }

    public override fun isAvailable(): Boolean {
        return isRunning.get()
    }
}