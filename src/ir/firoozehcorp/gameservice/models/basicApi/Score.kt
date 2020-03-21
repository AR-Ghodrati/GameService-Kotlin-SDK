/*
 * <copyright file="Score.kt" company="Firoozeh Technology LTD">
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
 * Represents Score Data Model In Game Service Basic API
 * @author Alireza Ghodrati
 */
class Score : Serializable {

    /**
     * Gets the Game id.
     * @return the Game id
     */
    @SerializedName("game")
    @Expose
    var gameId: String? = null
        private set


    /**
     * Gets the User Submit this Score.
     * @return the User Submit this Score
     */
    @SerializedName("user")
    @Expose
    var user: User? = null
        private set


    /**
     * Gets the Value of This Score.
     * @return the Value of This Score
     */
    @SerializedName("value")
    @Expose
    var value: Int = 0
        private set


    /**
     * Gets the Rank of This Score.
     * @return the Rank of This Score
     */
    @SerializedName("rank")
    @Expose
    var rank: Int = 0
        private set


    /**
     * Gets the Tries Count of This Score.
     * @return the Tries Count of This Score.
     */
    @SerializedName("tries")
    @Expose
    var tries: Int = 0
        private set


    override fun toString(): String {
        return "Score(gameId=$gameId, user=$user, value=$value, rank=$rank, tries=$tries)"
    }


}