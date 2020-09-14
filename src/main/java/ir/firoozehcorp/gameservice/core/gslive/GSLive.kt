/*
 * <copyright file="GSLive.kt" company="Firoozeh Technology LTD">
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

import ir.firoozehcorp.gameservice.handlers.GSHandler


/**
 * Represents Game Service MultiPlayer System (GSLive)
 * @author Alireza Ghodrati
 */
class GSLive {

    companion object {
        internal val handler: GSHandler = GSHandler()
    }

    //val realTime: GSLiveRT = GSLiveRT
    val turnBase: GSLiveTB = GSLiveTB
    val chat: GSLiveChat = GSLiveChat


    internal fun init() {
        handler.init()
    }

    internal fun dispose() {
        handler.close()
    }

    /*fun isRealTimeAvailable(): Boolean {
        return handler.realTimeHandler != null
    }
     */

    fun isTurnBasedAvailable(): Boolean {
        return handler.turnBasedHandler != null
    }


}