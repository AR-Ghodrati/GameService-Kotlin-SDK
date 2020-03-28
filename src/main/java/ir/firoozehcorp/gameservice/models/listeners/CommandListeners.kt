/*
 * <copyright file="CommandListeners.kt" company="Firoozeh Technology LTD">
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

import ir.firoozehcorp.gameservice.models.basicApi.User
import ir.firoozehcorp.gameservice.models.enums.gsLive.command.AutoMatchCancel
import ir.firoozehcorp.gameservice.models.gsLive.Invite
import ir.firoozehcorp.gameservice.models.gsLive.Room
import ir.firoozehcorp.gameservice.models.gsLive.command.AutoMatchEvent
import ir.firoozehcorp.gameservice.models.internal.EventHandler

/**
 * Represents CommandListeners In MultiPlayer System
 * @author Alireza Ghodrati
 */
open class CommandListeners : CoreListeners() {

    interface AvailableRoomsListener : EventHandler.IEventHandler<MutableList<Room>> {
        override fun invoke(element: MutableList<Room>, from: Class<*>?)
    }

    interface AutoMatchUpdatedListener : EventHandler.IEventHandler<AutoMatchEvent> {
        override fun invoke(element: AutoMatchEvent, from: Class<*>?)
    }

    interface AutoMatchCanceledListener : EventHandler.IEventHandler<AutoMatchCancel> {
        override fun invoke(element: AutoMatchCancel, from: Class<*>?)
    }

    interface InviteInboxListener : EventHandler.IEventHandler<MutableList<Invite>> {
        override fun invoke(element: MutableList<Invite>, from: Class<*>?)
    }

    interface NewInviteListener : EventHandler.IEventHandler<Invite> {
        override fun invoke(element: Invite, from: Class<*>?)
    }


    interface FindUserListener : EventHandler.IEventHandler<MutableList<User>> {
        override fun invoke(element: MutableList<User>, from: Class<*>?)
    }

    interface InvitationSentListener : EventHandler.IEventHandler<Boolean> {
        override fun invoke(element: Boolean, from: Class<*>?)
    }


    companion object {

        /**
         * Returns Available Rooms
         */
        val AvailableRoomsReceived: EventHandler<AvailableRoomsListener, MutableList<Room>> = EventHandler()


        /**
         * Returns New Auto Match Event When New User Added To List
         */
        val AutoMatchUpdated: EventHandler<AutoMatchUpdatedListener, AutoMatchEvent> = EventHandler()


        /**
         * Returns Current AutoMatch Canceled Status
         */
        val AutoMatchCanceled: EventHandler<AutoMatchCanceledListener, AutoMatchCancel> = EventHandler()


        /**
         * Returns Available Invites
         */
        val InviteInboxReceived: EventHandler<InviteInboxListener, MutableList<Invite>> = EventHandler()


        /**
         * Calls When New Invite Received
         */
        val NewInviteReceived: EventHandler<NewInviteListener, Invite> = EventHandler()


        /**
         * Returns List of Users
         */
        val FindUserReceived: EventHandler<FindUserListener, MutableList<User>> = EventHandler()


        /**
         * Calls When Current Invitation Sent Successfully
         */
        val InvitationSent: EventHandler<InvitationSentListener, Boolean> = EventHandler()
    }

}