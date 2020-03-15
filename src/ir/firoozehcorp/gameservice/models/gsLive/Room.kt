/*
 * <copyright file="Room.kt" company="Firoozeh Technology LTD">
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
import ir.firoozehcorp.gameservice.models.enums.gsLive.GSLiveType
import java.io.Serializable

/**
 * Represents Room Data Model In GameService MultiPlayer System (GSLive)
 * @author Alireza Ghodrati
 */
class Room : Serializable {

    /**
     * Gets the Room id.
     * You Can Use it In MultiPlayer Functions that Needs Room id
     * @return the Game id.
     */
    @SerializedName("_id")
    @Expose
    lateinit var id: String


    /**
     * Gets the Room Name.
     * @return the Room Name
     */
    @SerializedName("name")
    @Expose
    lateinit var name: String


    /**
     * Gets the Room Logo URL.
     * @return the Room Logo URL.
     */
    @SerializedName("logo")
    @Expose
    var logo: String? = null


    /**
     * Gets the Room Creator Id.
     * @return the Room Creator Id.
     */
    @SerializedName("creator")
    @Expose
    var creator: String? = null


    /**
     * Gets the Room Minimum Member Value To Accept.
     * @return the Room Minimum Member Value To Accept.
     */
    @SerializedName("min")
    @Expose
    var min: Int = 0


    /**
     * Gets the Room Minimum Member Value To Accept.
     * @return the Room Minimum Member Value To Accept.
     */
    @SerializedName("max")
    @Expose
    var max: Int = 0


    /**
     * Gets the Room Role Value.
     * @return the Room Role Value
     */
    @SerializedName("role")
    @Expose
    lateinit var role: String


    /**
     * Gets the Game Id
     * @return Gets the Game Id
     */
    @SerializedName("game")
    @Expose
    var gameId: String? = null


    /**
     * Gets the Room Privacy Value.
     * @return the Room Privacy Value.
     */
    @SerializedName("private")
    @Expose
    var isPrivate: Boolean = false


    /**
     * Gets the Room Status Value.
     * @return the Room Status Value.
     */
    @SerializedName("status")
    @Expose
    var status: Int = 0


    /**
     * Gets the Room Type
     * @return the Room Type
     */
    @SerializedName("syncMode")
    @Expose
    var type: GSLiveType = GSLiveType.NotSet


    override fun toString(): String {
        return "Room(id=$id, name=$name, logo=$logo, creator=$creator, min=$min, max=$max, role=$role, gameId=$gameId, isPrivate=$isPrivate, status=$status, type=$type)"
    }


}