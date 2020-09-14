/*
 * <copyright file="JoinData.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.models.gsLive

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ir.firoozehcorp.gameservice.models.enums.gsLive.JoinType
import java.io.Serializable

/**
 * Represents JoinData Data Model In GameService MultiPlayer System (GSLive)
 * @author Alireza Ghodrati
 */
class JoinData : Serializable {

    /**
     * Gets the Room Join Type.
     * @return the Room Join Type
     */
    @SerializedName("1")
    @Expose
    var type: JoinType = JoinType.NotSet
        private set


    /**
     * Gets the Room Join Data.
     * @return the Room Join Data
     */
    @SerializedName("2")
    @Expose
    var roomData: RoomData? = null
        private set


    /**
     * Gets the Player Member Who Joined To Room.
     * @return the Player Member Who Joined To Room
     */
    @SerializedName("3")
    @Expose
    var joinedMember: Member? =null
        private set


    override fun toString(): String {
        return "JoinData(type=$type, roomData=$roomData, joinedMember=$joinedMember)"
    }


}