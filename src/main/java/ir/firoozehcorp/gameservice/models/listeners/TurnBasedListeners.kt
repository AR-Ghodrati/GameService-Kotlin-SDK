/*
 * <copyright file="TurnBasedListeners.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.models.listeners

import ir.firoozehcorp.gameservice.models.gsLive.Member
import ir.firoozehcorp.gameservice.models.gsLive.command.ErrorEvent
import ir.firoozehcorp.gameservice.models.gsLive.command.JoinEvent
import ir.firoozehcorp.gameservice.models.gsLive.turnbased.Complete
import ir.firoozehcorp.gameservice.models.gsLive.turnbased.Finish
import ir.firoozehcorp.gameservice.models.gsLive.turnbased.Turn
import ir.firoozehcorp.gameservice.models.internal.EventHandler

/**
 * Represents TurnBasedListeners In MultiPlayer System
 * @author Alireza Ghodrati
 */
open class TurnBasedListeners : CommandListeners() {

    interface JoinedRoomListener : EventHandler.IEventHandler<JoinEvent> {
        override fun invoke(element: JoinEvent, from: Class<*>?)
    }

    interface LeftRoomListener : EventHandler.IEventHandler<Member> {
        override fun invoke(element: Member, from: Class<*>?)
    }

    interface TakeTurnListener : EventHandler.IEventHandler<Turn> {
        override fun invoke(element: Turn, from: Class<*>?)
    }

    interface ChoosedNextListener : EventHandler.IEventHandler<Member> {
        override fun invoke(element: Member, from: Class<*>?)
    }


    interface FinishedListener : EventHandler.IEventHandler<Finish> {
        override fun invoke(element: Finish, from: Class<*>?)
    }


    interface CompletedListener : EventHandler.IEventHandler<Complete> {
        override fun invoke(element: Complete, from: Class<*>?)
    }

    interface CurrentTurnMemberListener : EventHandler.IEventHandler<Member> {
        override fun invoke(element: Member, from: Class<*>?)
    }

    interface RoomMembersDetailListener : EventHandler.IEventHandler<MutableList<Member>> {
        override fun invoke(element: MutableList<Member>, from: Class<*>?)
    }


    companion object {

        /**
         * Calls When SomeOne Join To Current Room
         * It Maybe Current Player or Another
         */
        val JoinedRoom: EventHandler<JoinedRoomListener, JoinEvent> = EventHandler()


        /**
         * Calls When SomeOne Left the Current Room
         */
        val LeftRoom: EventHandler<LeftRoomListener, Member> = EventHandler()


        /**
         * Returns New Turn With Data From Another Players
         */
        val TakeTurn: EventHandler<TakeTurnListener, Turn> = EventHandler()


        /**
         * Returns New Turn From Another Players
         */
        val ChoosedNext: EventHandler<ChoosedNextListener, Member> = EventHandler()


        /**
         * Calls When SomeOne Announced To Finish Game
         */
        val Finished: EventHandler<FinishedListener, Finish> = EventHandler()


        /**
         * Calls When The Game Is Finished
         */
        val Completed: EventHandler<CompletedListener, Complete> = EventHandler()


        /**
         * Returns Current Turn Member Received
         */
        val CurrentTurnMemberReceived: EventHandler<CurrentTurnMemberListener, Member> = EventHandler()


        /**
         * Returns Current Room Members Detail
         */
        val RoomMembersDetailReceived: EventHandler<RoomMembersDetailListener, MutableList<Member>> = EventHandler()


        /**
         * Calls When An New Error Received From Server
         */
        val Error: EventHandler<ErrorListener, ErrorEvent> = EventHandler()
    }

}