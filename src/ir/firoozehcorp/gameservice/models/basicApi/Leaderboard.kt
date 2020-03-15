/*
 * <copyright file="Leaderboard.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.models.basicApi

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Represents LeaderBoard Data Model In Game Service Basic API
 * @author Alireza Ghodrati
 */
class Leaderboard : Serializable {

    /**
     * Gets the LeaderBoard Name.
     * @return the LeaderBoard Name
     */
    @SerializedName("name")
    @Expose
    var name: String? = null


    /**
     * Gets the LeaderBoard Key.
     * You Can Use It To Submit Score in a LeaderBoard
     * @return the LeaderBoard Key
     */
    @SerializedName("key")
    @Expose
    var key: String? = null


    /**
     * Gets the LeaderBoard status.
     * if the status is True You Can Submit Score in LeaderBoard
     * @return the LeaderBoard status
     */
    @SerializedName("status")
    @Expose
    var status: Boolean = false


    /**
     * Gets the LeaderBoard Cover URL.
     * @return the LeaderBoard Cover URL
     */
    @SerializedName("image")
    @Expose
    var image: String? = null


    /**
     * Gets the Game id.
     * @return the Game id
     */
    @SerializedName("game")
    @Expose
    var game: String? = null


    /**
     * Gets the LeaderBoard Form Value.
     * this Value Sets In gameservice Developers Panel.
     * @return the LeaderBoard From Value
     */
    @SerializedName("from")
    @Expose
    var from: Int = 0


    /**
     * Gets the LeaderBoard To Value.
     * this Value Sets In gameservice Developers Panel.
     * @return the LeaderBoard To Value
     */
    @SerializedName("to")
    @Expose
    var to: Int = 0


    /**
     * Gets the LeaderBoard Order Type.
     * this Value Sets In gameservice Developers Panel.
     * @return the LeaderBoard Order Type
     */
    @SerializedName("order")
    @Expose
    var order: Int = 0


    override fun toString(): String {
        return "Leaderboard(name=$name, key=$key, status=$status, image=$image, game=$game, from=$from, to=$to, order=$order)"
    }


}