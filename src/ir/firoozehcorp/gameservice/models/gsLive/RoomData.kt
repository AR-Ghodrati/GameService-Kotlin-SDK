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
import java.io.Serializable

/**
 * Represents Room Data Model In GameService MultiPlayer System (GSLive)
 * @author Alireza Ghodrati
 */
class RoomData : Serializable {

    /**
     * Gets the Current Room id.
     * @return the Game id.
     */
    @SerializedName("1")
    @Expose
    lateinit var id: String


    /**
     * Gets the Room Name.
     * @return the Room Name
     */
    @SerializedName("2")
    @Expose
    lateinit var name: String


    /**
     * Gets the Room Logo URL.
     * @return the Room Logo URL.
     */
    @SerializedName("3")
    @Expose
    var logo: String? = null


    /**
     * Gets the Room Creator Id.
     * @return the Room Creator Id.
     */
    @SerializedName("4")
    @Expose
    var creator: String? = null


    /**
     * Gets the Room Minimum Member Value To Accept.
     * @return the Room Minimum Member Value To Accept.
     */
    @SerializedName("5")
    @Expose
    var min: Int = 0


    /**
     * Gets the Room Minimum Member Value To Accept.
     * @return the Room Minimum Member Value To Accept.
     */
    @SerializedName("6")
    @Expose
    var max: Int = 0


    /**
     * Gets the Room Role Value.
     * @return the Room Role Value
     */
    @SerializedName("7")
    @Expose
    var role: String? = null


    /**
     * Gets the Room Privacy Value.
     * @return the Room Privacy Value.
     */
    @SerializedName("8")
    @Expose
    var isPrivate: Boolean = false


    /**
     * Gets the Room Status Value.
     * @return the Room Status Value.
     */
    @SerializedName("9")
    @Expose
    var status: Int = 0


    /**
     * Gets the Room Players Joined Count
     * @return the Room Players Joined Count
     */
    @SerializedName("10")
    @Expose
    var joinedPlayers: Int = 0


    override fun toString(): String {
        return "RoomData(id=$id, name=$name, logo=$logo, creator=$creator, min=$min, max=$max, role=$role, isPrivate=$isPrivate, status=$status, joinedPlayers=$joinedPlayers)"
    }


}