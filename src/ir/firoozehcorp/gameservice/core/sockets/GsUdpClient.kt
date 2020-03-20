/*
 * <copyright file="GsUdpClient.kt" company="Firoozeh Technology LTD">
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

import ir.firoozehcorp.gameservice.models.GameServiceException
import ir.firoozehcorp.gameservice.models.enums.gsLive.GProtocolSendType
import ir.firoozehcorp.gameservice.models.enums.gsLive.GSLiveType
import ir.firoozehcorp.gameservice.models.gsLive.APacket
import ir.firoozehcorp.gameservice.models.gsLive.command.Area
import ir.firoozehcorp.gameservice.models.gsLive.realtime.Packet
import ir.firoozehcorp.gameservice.models.internal.interfaces.GameServiceCallback
import ir.firoozehcorp.gameservice.models.listeners.CoreListeners
import ir.firoozehcorp.gprotocol.GProtocolLoader
import ir.firoozehcorp.gprotocol.Models.GProtocolListener
import ir.firoozehcorp.gprotocol.Models.GProtocolType


/**
 * @author Alireza Ghodrati
 */
internal class GsUdpClient(area: Area) : GProtocolClient(), GProtocolListener {

    init {
        if (area.protocol.toUpperCase() != "TCP")
            throw GameServiceException("Only TCP Protocol Supported")
        endpoint = area
    }

    public override fun init(callback: GameServiceCallback<Boolean>) {
        GProtocolLoader.Init(endpoint.ip, endpoint.port, this)
    }

    public override fun startReceiving() {
        GProtocolLoader.StartReceiving()
    }

    public override fun stopReceiving() {
        GProtocolLoader.StopReceiving()
    }

    public override fun updatePwd(newPwd: String?) {
    }

    public override fun setType(type: GSLiveType?) {
    }

    public override fun send(packet: APacket?, type: GProtocolSendType) {
        when (type) {
            GProtocolSendType.NotSet -> {
            }
            GProtocolSendType.Reliable -> GProtocolLoader.SendReliable(gson.toJson(packet))
            GProtocolSendType.UnReliable -> GProtocolLoader.SendUnReliable(gson.toJson(packet))
        }
    }

    public override fun isAvailable(): Boolean {
        return IsAvailable
    }

    override fun OnTimeout() {
        IsAvailable = false
        onError.invokeListeners(GameServiceException("ServerTimeout"))
    }

    override fun OnData(p0: String?, p1: GProtocolType) {
        val packet = gson.fromJson(p0, Packet::class.java)
        val type = if (p1 == GProtocolType.ChannelReliable) GProtocolSendType.Reliable else GProtocolSendType.UnReliable
        onDataReceived.invokeListeners(Pair(packet, type))
    }

    override fun OnConnected() {
        IsAvailable = true
        CoreListeners.GProtocolConnected.invokeListeners(null)
    }

    override fun OnDisconnected() {
        IsAvailable = false
        onError.invokeListeners(GameServiceException("ServerDisconnect"))
    }
}