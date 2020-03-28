/*
 * <copyright file="Achievement.kt" company="Firoozeh Technology LTD">
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
 * Represents Achievement Data Model In Game Service Basic API
 * @author Alireza Ghodrati
 */
class Achievement : Serializable {

    /**
     * Gets the Achievement Name.
     * @return the Achievement Name
     */
    @SerializedName("name")
    @Expose
    var name: String? = null
        private set


    /**
     * Gets the Achievement Key.
     * You Can Use It To Unlock Achievement
     * @return the Achievement Key
     */
    @SerializedName("key")
    @Expose
    var key: String? = null
        private set


    /**
     * Gets the Achievement Point.
     * @return the Achievement Point
     */
    @SerializedName("point")
    @Expose
    var point: Int = 0
        private set


    /**
     * Gets the Achievement Description.
     * @return the Achievement Description
     */
    @SerializedName("desc")
    @Expose
    var description: String? = null
        private set


    /**
     * Gets the Achievement Cover URL.
     * @return the Achievement Cover URL
     */
    @SerializedName("image")
    @Expose
    var image: String? = null
        private set


    /**
     * Gets the Achievement status.
     * if the status is True You Can Unlock Achievement
     * @return the Achievement status
     */
    @SerializedName("status")
    @Expose
    var status: Boolean = false
        private set


    /**
     * Gets the Status of Earned Achievement
     * if the status is True You Earned this Achievement Before
     * @return the Status of Earned Achievement
     */
    @SerializedName("earned")
    @Expose
    var earned: Boolean = false
        private set


    /**
     * Gets the Game id.
     * @return the Game id
     */
    @SerializedName("game")
    @Expose
    var game: String? = null
        private set


    override fun toString(): String {
        return "Achievement(name=$name, key=$key, point=$point, description=$description, image=$image, status=$status, earned=$earned, game=$game)"
    }


}