/*
 * <copyright file="GSLiveRT.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.core.gslive

import ir.firoozehcorp.gameservice.core.GameService
import ir.firoozehcorp.gameservice.handlers.command.request.*
import ir.firoozehcorp.gameservice.handlers.realtime.request.GetMemberHandler
import ir.firoozehcorp.gameservice.handlers.realtime.request.LeaveRoomHandler
import ir.firoozehcorp.gameservice.handlers.realtime.request.SendPrivateMessageHandler
import ir.firoozehcorp.gameservice.handlers.realtime.request.SendPublicMessageHandler
import ir.firoozehcorp.gameservice.models.GameServiceException
import ir.firoozehcorp.gameservice.models.annotations.NotNull
import ir.firoozehcorp.gameservice.models.enums.gsLive.GProtocolSendType
import ir.firoozehcorp.gameservice.models.enums.gsLive.GSLiveType
import ir.firoozehcorp.gameservice.models.gsLive.command.RoomDetail
import ir.firoozehcorp.gameservice.models.gsLive.realtime.DataPayload

/**
 * Represents Game Service Realtime MultiPlayer System
 * @author Alireza Ghodrati
 */
object GSLiveRT {


    /**
     * Create Room With Option Like : Name , Min , Max , Role , IsPrivate
     * @param option Create Room Option
     */
    @Throws(GameServiceException::class)
    fun createRoom(@NotNull option: GSLiveOption.CreateRoomOption) {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        option.gsLiveType = GSLiveType.RealTime
        GSLive.handler.commandHandler.request(CreateRoomHandler.signature, option)
    }


    /**
     * Create AutoMatch With Option Like :  Min , Max , Role
     * @param option  AutoMatch Option
     */
    @Throws(GameServiceException::class)
    fun autoMatch(@NotNull option: GSLiveOption.AutoMatchOption) {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        option.gsLiveType = GSLiveType.RealTime
        GSLive.handler.commandHandler.request(AutoMatchHandler.signature, option)
    }


    /**
     * Cancel Current AutoMatch
     */
    @Throws(GameServiceException::class)
    fun cancelAutoMatch() {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        GSLive.handler.commandHandler.request(CancelAutoMatchHandler.signature)
    }


    /**
     * Join In Room With RoomID
     * @param roomId Room's id You Want To Join
     */
    @Throws(GameServiceException::class)
    fun joinRoom(@NotNull roomId: String) {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (roomId.isEmpty()) throw GameServiceException("roomId Cant Be EmptyOrNull")
        GSLive.handler.commandHandler.request(JoinRoomHandler.signature, RoomDetail().apply { id = roomId })
    }


    /**
     * Leave The Current Room
     */
    @Throws(GameServiceException::class)
    fun leaveRoom() {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (GSLive.handler.realTimeHandler == null) throw GameServiceException("You Must Create or Join Room First")

        GSLive.handler.realTimeHandler?.request(LeaveRoomHandler.signature, type = GProtocolSendType.Reliable)
        GSLive.handler.realTimeHandler?.close()
    }


    /**
     * Get Available Rooms According To Room's Role
     * @param role Room's Role
     */
    @Throws(GameServiceException::class)
    fun getAvailableRooms(@NotNull role: String) {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (role.isEmpty()) throw GameServiceException("role Cant Be EmptyOrNull")
        GSLive.handler.commandHandler.request(GetRoomsHandler.signature, RoomDetail()
                .apply {
                    this.role = role
                    this.gsLiveType = GSLiveType.RealTime.ordinal
                })
    }


    /**
     * Send A Data To All Players in Room.
     * @param data Data To BroadCast
     * @param sendType Send Type
     */
    @Throws(GameServiceException::class)
    fun sendPublicMessage(@NotNull data: String, sendType: GProtocolSendType) {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (data.isEmpty()) throw GameServiceException("data Cant Be EmptyOrNull")
        if (GSLive.handler.realTimeHandler == null) throw GameServiceException("You Must Create or Join Room First")

        GSLive.handler.realTimeHandler?.request(SendPublicMessageHandler.signature, DataPayload().apply { payload = data }, sendType)
    }


    /**
     * Send A Data To Specific Player in Room.
     * @param data  Data To BroadCast
     * @param receiverId  (Type : MemberID)Player's ID<
     */
    @Throws(GameServiceException::class)
    fun sendPrivateMessage(@NotNull data: String, @NotNull receiverId: String) {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (data.isEmpty() && receiverId.isEmpty()) throw GameServiceException("data Or receiverId Cant Be EmptyOrNull")
        if (GSLive.handler.realTimeHandler == null) throw GameServiceException("You Must Create or Join Room First")

        GSLive.handler.realTimeHandler?.request(SendPrivateMessageHandler.signature, DataPayload()
                .apply {
                    this.receiverId = receiverId
                    payload = data
                }, GProtocolSendType.Reliable)
    }


    /**
     * Leave The Current Room
     */
    @Throws(GameServiceException::class)
    fun getRoomMembersDetail() {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (GSLive.handler.realTimeHandler == null) throw GameServiceException("You Must Create or Join Room First")

        GSLive.handler.realTimeHandler?.request(GetMemberHandler.signature, type = GProtocolSendType.Reliable)
    }


    /**
     * Get Your Invite Inbox
     */
    @Throws(GameServiceException::class)
    fun getInviteInbox() {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        GSLive.handler.commandHandler.request(InviteListHandler.signature, RoomDetail().apply { type = GSLiveType.RealTime.ordinal })
    }


    /**
     * Invite a Specific Player To Specific Room
     * @param roomId  (Type : RoomID)Room's ID
     * @param userId  (Type : UserID)User's ID
     */
    @Throws(GameServiceException::class)
    fun inviteUser(@NotNull roomId: String, @NotNull userId: String) {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (userId.isEmpty() && roomId.isEmpty()) throw GameServiceException("roomId Or userId Cant Be EmptyOrNull")

        GSLive.handler.commandHandler.request(InviteUserHandler.signature, RoomDetail()
                .apply {
                    user = userId
                    id = roomId
                    type = GSLiveType.RealTime.ordinal
                })
    }


    /**
     * Accept a Specific Invite With Invite ID
     * Note: After accepting the invitation, you will be automatically entered into the game room
     * @param inviteId (Type : InviteID) Invite's ID
     */
    @Throws(GameServiceException::class)
    fun inviteUser(@NotNull inviteId: String) {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (inviteId.isEmpty()) throw GameServiceException("inviteId Cant Be EmptyOrNull")

        GSLive.handler.commandHandler.request(AcceptInviteHandler.signature, RoomDetail()
                .apply {
                    invite = inviteId
                    type = GSLiveType.RealTime.ordinal
                })
    }


    /**
     * Find All Users With Specific NickName
     * @param query Player's NickName
     * @param limit (Max = 15) The Result Limits
     */
    @Throws(GameServiceException::class)
    fun findUser(@NotNull query: String, limit: Int) {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (query.isEmpty()) throw GameServiceException("query Cant Be EmptyOrNull")
        if (limit <= 0 || limit > 15) throw GameServiceException("invalid Limit Value")

        GSLive.handler.commandHandler.request(FindUserHandler.signature, RoomDetail()
                .apply {
                    max = limit
                    user = query
                })
    }


}