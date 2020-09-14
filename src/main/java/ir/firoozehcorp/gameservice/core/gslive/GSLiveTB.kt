/*
 * <copyright file="GSLiveTB.kt" company="Firoozeh Technology LTD">
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
import ir.firoozehcorp.gameservice.handlers.turnbased.request.*
import ir.firoozehcorp.gameservice.models.GameServiceException
import ir.firoozehcorp.gameservice.models.Outcomes
import ir.firoozehcorp.gameservice.models.annotations.NotNull
import ir.firoozehcorp.gameservice.models.annotations.Nullable
import ir.firoozehcorp.gameservice.models.enums.gsLive.GProtocolSendType
import ir.firoozehcorp.gameservice.models.enums.gsLive.GSLiveType
import ir.firoozehcorp.gameservice.models.gsLive.command.RoomDetail
import ir.firoozehcorp.gameservice.models.gsLive.turnbased.DataPayload

/**
 * Represents Game Service TurnBased MultiPlayer System
 * @author Alireza Ghodrati
 */
object GSLiveTB {


    /**<
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
     * @param option AutoMatch Option
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
    fun joinRoom(@NotNull roomId: String,@Nullable extra: String? = null) {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (roomId.isEmpty()) throw GameServiceException("roomId Cant Be EmptyOrNull")
        GSLive.handler.commandHandler.request(JoinRoomHandler.signature, RoomDetail().apply
        {
            id = roomId
            this.extra = extra
        })
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
     * If is your Turn, you can send data to other players using this function.
     * Also if You Want to Move Your Turn to the Next player
     * put the next player ID in the function entry
     * You can use this function several times
     * @param data Room's Role
     * @param whoIsNext Next Player's ID
     */
    @Throws(GameServiceException::class)
    fun takeTurn(@Nullable data: String? = null, @Nullable whoIsNext: String? = null) {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (GSLive.handler.turnBasedHandler == null) throw GameServiceException("You Must Create or Join Room First")

        GSLive.handler.turnBasedHandler?.request(TakeTurnHandler.signature, DataPayload()
                .apply {
                    this.data = data
                    this.nextId = whoIsNext
                })
    }


    /**
     * If it's your turn, you can transfer the turn to the next player without sending data
     *  if whoIsNext Set Null , Server Automatically Selects Next Turn
     * @param whoIsNext Next Player's ID
     */
    @Throws(GameServiceException::class)
    fun chooseNext(@Nullable whoIsNext: String? = null) {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (GSLive.handler.turnBasedHandler == null) throw GameServiceException("You Must Create or Join Room First")

        GSLive.handler.turnBasedHandler?.request(ChooseNextHandler.signature, DataPayload().apply { nextId = whoIsNext })
    }


    /**
     * Leave The Current Room , if whoIsNext Set Null , Server Automatically Selects Next Turn ata
     * @param whoIsNext (Type : Member's ID) Player's id You Want To Select Next Turn
     */
    @Throws(GameServiceException::class)
    fun leaveRoom(@Nullable whoIsNext: String? = null) {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (GSLive.handler.turnBasedHandler == null) throw GameServiceException("You Must Create or Join Room First")

        GSLive.handler.turnBasedHandler?.request(LeaveRoomHandler.signature, DataPayload().apply { nextId = whoIsNext })
        GSLive.handler.turnBasedHandler?.close()
    }


    /**
     * If you want to announce the end of the game, use this function to send the result of your game to other players
     * @param outcomes A set of players and their results
     */
    @Throws(GameServiceException::class)
    fun finish(@NotNull outcomes: Outcomes) {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (GSLive.handler.turnBasedHandler == null) throw GameServiceException("You Must Create or Join Room First")

        GSLive.handler.turnBasedHandler?.request(FinishHandler.signature, DataPayload().apply { this.outcomes = outcomes })
    }


    /**
     * If you would like to confirm one of the results posted by other Players
     * @param memberId The Specific player ID
     */
    @Throws(GameServiceException::class)
    fun complete(@NotNull memberId: String) {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (GSLive.handler.turnBasedHandler == null) throw GameServiceException("You Must Create or Join Room First")

        GSLive.handler.turnBasedHandler?.request(CompleteHandler.signature, DataPayload().apply { id = memberId })
    }


    /**
     * Leave The Current Room
     */
    @Throws(GameServiceException::class)
    fun getCurrentTurnMember() {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (GSLive.handler.turnBasedHandler == null) throw GameServiceException("You Must Create or Join Room First")

        GSLive.handler.turnBasedHandler?.request(CurrentTurnHandler.signature)
    }


    /**
     * Leave The Current Room
     */
    @Throws(GameServiceException::class)
    fun getRoomMembersDetail() {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (GSLive.handler.turnBasedHandler == null) throw GameServiceException("You Must Create or Join Room First")

        GSLive.handler.turnBasedHandler?.request(GetMemberHandler.signature)
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
                    type = GSLiveType.TurnBased.ordinal
                })
    }


    /**
     * Accept a Specific Invite With Invite ID
     * Note: After accepting the invitation, you will be automatically entered into the game room
     * @param inviteId (Type : InviteID) Invite's ID
     */
    @Throws(GameServiceException::class)
    fun AcceptInvite(@NotNull inviteId: String,@Nullable extra: String? = null) {
        if (GameService.IsGuest) throw GameServiceException("This Function Not Working In Guest Mode")
        if (inviteId.isEmpty()) throw GameServiceException("inviteId Cant Be EmptyOrNull")

        GSLive.handler.commandHandler.request(AcceptInviteHandler.signature, RoomDetail()
                .apply {
                    invite = inviteId
                    this.extra = extra
                    type = GSLiveType.TurnBased.ordinal
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