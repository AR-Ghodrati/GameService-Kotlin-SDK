/*
 * <copyright file="SubmitScoreResponse.kt" company="Firoozeh Technology LTD">
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

package ir.FiroozehCorp.GameService.Models.BasicApi

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author Alireza Ghodrati
 */
class SubmitScoreResponse {

    /**
     * Gets the LeaderBoard.
     * @return the LeaderBoard </value>
     */
    @SerializedName("leaderboard_r")
    @Expose
    var leaderboard: Leaderboard? = null


    /**
     * Gets the Score Value.
     * @return the Score Value </value>
     */
    @SerializedName("score")
    @Expose
    var score: Int = 0


    /**
     * Gets the Tries Value.
     * How Many Tries To Submit Score
     * @return the Tries Value </value>
     */
    @SerializedName("tries")
    @Expose
    var tries: Int = 0


    override fun toString(): String {
        return "SubmitScoreResponse(leaderboard=$leaderboard, score=$score, tries=$tries)"
    }


}