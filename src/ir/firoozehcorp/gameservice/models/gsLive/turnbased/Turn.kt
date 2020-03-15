/*
 * <copyright file="Turn.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.models.gsLive.turnbased

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ir.firoozehcorp.gameservice.models.gsLive.Member
import java.io.Serializable

/**
 * Represents Turn Data Model In GameService TurnBased MultiPlayer System
 * @author Alireza Ghodrati
 */
class Turn : Serializable {

    /**
     * Gets the Data Send In Turn By Other Player.
     * @return the Data Send In Turn By Other Player.
     */
    @SerializedName("0")
    @Expose
    var data: String? = null


    /**
     * Gets The Player Member who has TakeTurn.
     * @return The Player Member who has TakeTurn.
     */
    @SerializedName("1")
    @Expose
    lateinit var whoTakeTurn: Member


    override fun toString(): String {
        return "Turn(data=$data, whoTakeTurn=$whoTakeTurn)"
    }


}