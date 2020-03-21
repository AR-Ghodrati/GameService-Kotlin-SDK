/*
 * <copyright file="Outcome.kt" company="Firoozeh Technology LTD">
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
import java.io.Serializable

/**
 * Represents Outcome Data Model In GameService TurnBased MultiPlayer System
 * @author Alireza Ghodrati
 */
class Outcome : Serializable {

    /**
     * Gets the Placement of Outcome.
     * @return the Placement of Outcome.
     */
    @SerializedName("0")
    @Expose
    var placement: Int = -1
        private set


    /**
     * Gets the Result of Outcome.
     * @return the Result of Outcome.
     */
    @SerializedName("1")
    @Expose
    var result: String? = null
        private set


    override fun toString(): String {
        return "Outcome(placement=$placement, result=$result)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Outcome

        if (placement != other.placement) return false
        if (result != other.result) return false

        return true
    }

    override fun hashCode(): Int {
        var result1 = placement
        result1 = 31 * result1 + (result?.hashCode() ?: 0)
        return result1
    }


}