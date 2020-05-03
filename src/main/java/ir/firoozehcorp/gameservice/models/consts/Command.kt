/*
 * <copyright file="Command.kt" company="Firoozeh Technology LTD">
 * Copyright (C) 2020. Firoozeh Technology LTD. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
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

package ir.firoozehcorp.gameservice.models.consts

import ir.firoozehcorp.gameservice.models.gsLive.command.Area

/**
 * @author Alireza Ghodrati
 */
internal object Command {

    val area = Area().apply {
        ip = "command.gamesservice.ir"
        port = 3003
        protocol = "tcp"
    }

    // For Send
    const val ActionAuth = 0
    const val ActionAutoMatch = 1
    const val ActionCreateRoom = 2
    const val ActionGetRooms = 3
    const val ActionJoinRoom = 4
    const val ActionPing = 5
    const val ActionInviteUser = 6
    const val ActionKickUser = 7
    const val ActionGetInviteList = 8
    const val ActionAcceptInvite = 9
    const val ActionFindMembers = 10
    const val ActionNotification = 11
    const val ActionOnInvite = 15
    const val ActionCancelAutoMatch = 16


    const val ActionSubscribe = 12
    const val ActionChat = 13
    const val ActionUnSubscribe = 14
    const val ActionGetChannelsSubscribed = 17
    const val ActionPrivateChat = 18
    const val ActionChatRoomDetails = 19
    const val ActionGetLastChats = 20
    const val ActionGetPendingChats = 21


    const val Error = 100
}