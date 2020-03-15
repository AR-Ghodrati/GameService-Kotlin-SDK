/*
 * <copyright file="Game.kt" company="Firoozeh Technology LTD">
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
 * Represents Game Data Model In GameService MultiPlayer System (GSLive)
 * @author Alireza Ghodrati
 */
class Game : Serializable {

    /**
     * Gets the Game id.
     * @return the Game id.
     */
    @SerializedName("_id")
    @Expose
    lateinit var id: String


    /**
     * Gets the Game Logo URL.
     * @return Gets the Game Logo URL.
     */
    @SerializedName("logo")
    @Expose
    var logo: String? = null


    /**
     * Gets the Game name.
     * @return the Game name
     */
    @SerializedName("name")
    @Expose
    lateinit var name: String


    override fun toString(): String {
        return "Game(id=$id, logo=$logo, name=$name)"
    }


}