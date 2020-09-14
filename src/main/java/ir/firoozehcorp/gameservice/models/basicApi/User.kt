/*
 * <copyright file="User.kt" company="Firoozeh Technology LTD">
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
 * Represents User Data Model In Game Service Basic API
 * @author Alireza Ghodrati
 */
class User : Serializable {


    /**
     * Gets the User id.
     * @return the User id</value>
     */
    @SerializedName("_id")
    @Expose
    var id: String? = null
        private set


    /**
     * Gets the User Name.
     * @return the User Name</value>
     */
    @SerializedName("name")
    @Expose
    var name: String? = null
        private set

    /**
     * Gets the User Email(NULLABLE).
     * @return the User Email</value>
     */
    @SerializedName("email")
    @Expose
    var email: String? = null
        private set



    /**
     * Gets the User PhoneNumber(NULLABLE).
     * @return the User PhoneNumber</value>
     */
    @SerializedName("mobile")
    @Expose
    var phoneNumber: String? = null
        private set


    /**
     * Gets the User Logo.
     * @return the User Logo</value>
     */
    @SerializedName("logo")
    @Expose
    var logo: String? = null
        private set


    /**
     * Gets the User Point.
     * @return Gets the User Point</value>
     */
    @SerializedName("point")
    @Expose
    var point: Int = 0
        private set


    /**
     * get this User Is Yours or Not.
     * @return this User Is Yours or Not</value>
     */
    @SerializedName("isMe")
    @Expose
    var isMe: Boolean = false
        private set

    /**
     * get this User Is Guest or Not.
     * (Note : Only Works on @see gameservice.GetCurrentPlayer)
     * @return this User Is Guest or Not</value>
     */
    @SerializedName("guest")
    @Expose
    var isGuest: Boolean = false
        private set


    override fun toString(): String {
        return "User(id=$id, name=$name, logo=$logo, point=$point, isMe=$isMe, isGuest=$isGuest)"
    }


}