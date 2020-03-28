/*
 * <copyright file="$this.kt" company="Firoozeh Technology LTD">
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

import ir.firoozehcorp.gameservice.models.gsLive.command.ErrorEvent
import ir.firoozehcorp.gameservice.models.gsLive.command.StartPayload
import ir.firoozehcorp.gameservice.models.internal.EventHandler

/**
 * Represents CoreListeners In MultiPlayer System
 * @author Alireza Ghodrati
 */
open class CoreListeners {

    internal interface GProtocolListener : EventHandler.IEventHandler<Void?> {
        override fun invoke(element: Void?, from: Class<*>?)
    }

    internal interface AuthorisationListener : EventHandler.IEventHandler<String> {
        override fun invoke(element: String, from: Class<*>?)
    }

    internal interface GSLiveSystemListener : EventHandler.IEventHandler<StartPayload> {
        override fun invoke(element: StartPayload, from: Class<*>?)
    }

    internal interface PingListener : EventHandler.IEventHandler<Void?> {
        override fun invoke(element: Void?, from: Class<*>?)
    }

    internal interface DisposeListener : EventHandler.IEventHandler<Void?> {
        override fun invoke(element: Void?, from: Class<*>?)
    }


    interface LoginListener : EventHandler.IEventHandler<Void?> {
        override fun invoke(element: Void?, from: Class<*>?)
    }

    interface ErrorListener : EventHandler.IEventHandler<ErrorEvent> {
        override fun invoke(element: ErrorEvent, from: Class<*>?)
    }


    companion object {

        internal val GProtocolConnected: EventHandler<GProtocolListener, Void?> = EventHandler()
        internal val Authorized: EventHandler<AuthorisationListener, String> = EventHandler()
        internal val GsLiveSystemStarted: EventHandler<GSLiveSystemListener, StartPayload> = EventHandler()
        internal val Ping: EventHandler<PingListener, Void?> = EventHandler()
        internal val Dispose: EventHandler<DisposeListener, Void?> = EventHandler()


        /**
         * Calls When Your Game Successfully Connected To GameService
         */
        val SuccessfullyLogined: EventHandler<LoginListener, Void?> = EventHandler()


        /**
         * Calls When An New Error Received From Server
         */
        val Error: EventHandler<ErrorListener, ErrorEvent> = EventHandler()
    }

}