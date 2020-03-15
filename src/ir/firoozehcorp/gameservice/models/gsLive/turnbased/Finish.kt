/*
 * <copyright file="Finish.kt" company="Firoozeh Technology LTD">
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
import ir.firoozehcorp.gameservice.models.Outcome
import ir.firoozehcorp.gameservice.models.gsLive.Member
import java.io.Serializable

/**
 * Represents Finish Data Model In GameService TurnBased MultiPlayer System
 * @author Alireza Ghodrati
 */
class Finish : Serializable {

    /**
     * Gets Member Data of Has Announced The End Of The Game.
     * @return Member Data of Has Announced The End Of The Game.
     */
    @SerializedName("0")
    @Expose
    lateinit var memberFinish: Member


    /**
     * Gets the Outcomes sent from the player.
     * Call From Other Player With this Function @see("Finish")/>
     * @return the Outcomes sent from the player
     */
    @SerializedName("1")
    @Expose
    lateinit var outcomes: Outcome

}