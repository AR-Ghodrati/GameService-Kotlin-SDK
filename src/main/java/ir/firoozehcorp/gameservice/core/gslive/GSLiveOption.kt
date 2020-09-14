/*
 * <copyright file="GSLiveOption.kt" company="Firoozeh Technology LTD">
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

import ir.firoozehcorp.gameservice.models.GameServiceException
import ir.firoozehcorp.gameservice.models.enums.gsLive.GSLiveType
import java.io.Serializable

/**
 * Represents GSLive GSLiveOption
 * @author Alireza Ghodrati
 */
class GSLiveOption : Serializable {

    open class AutoMatchOption @Throws(GameServiceException::class) constructor(open var minPlayer: Int = 2, open var maxPlayer: Int = 2, open var role: String, open var isPersist: Boolean = false,open var extra: String? = null) {

        internal var gsLiveType = GSLiveType.NotSet

        init {
            if (minPlayer < 2 || minPlayer > 8) throw GameServiceException("Invalid MinPlayer Value")
            if (maxPlayer < 2 || maxPlayer > 8) throw GameServiceException("Invalid MaxPlayer Value")
            if (role.isEmpty()) throw  GameServiceException("Role Cant Be EmptyOrNull")
            if (maxPlayer < minPlayer) throw GameServiceException("MaxPlayer Cant Smaller Than MinPlayer")
        }
    }


    class CreateRoomOption @Throws(GameServiceException::class) constructor(var roomName: String, override var minPlayer: Int = 2, override var maxPlayer: Int = 2, override var role: String, var isPrivate: Boolean = false, override var isPersist: Boolean = false,override var extra: String? = null)
        : AutoMatchOption(minPlayer, maxPlayer, role, isPersist,extra) {

        init {
            if (roomName.isEmpty()) throw  GameServiceException("RoomName Cant Be EmptyOrNull")
        }
    }

}