/*
 * <copyright file="Complete.kt" company="Firoozeh Technology LTD">
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
import ir.firoozehcorp.gameservice.models.Outcomes
import java.io.Serializable

/**
 * Represents Complete Data Model In GameService TurnBased MultiPlayer System
 * @author Alireza Ghodrati
 */
class Complete : Serializable {

    /**
     * Gets the Accept Counts from Other Players.
     * @return the Accept Counts from Other Players.
     */
    @SerializedName("Accept")
    @Expose
    var acceptCounts: String? = null
        private set


    /**
     * Gets the Game Result(Outcomes).
     * (Type : Dictionary(MemberID,Outcome))
     * @return the Game Result(Outcomes).
     */
    @SerializedName("Outcomes")
    @Expose
    var result: Outcomes? = null
        private set


    override fun toString(): String {
        return "Complete(acceptCounts=$acceptCounts, result=$result)"
    }


}