/*
 * <copyright file="$this.kt" company="Firoozeh Technology LTD">
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
 * Represents MemberInfo Data Model In GameService MultiPlayer System (GSLive)
 * @author Alireza Ghodrati
 */
class MemberInfo : Serializable {

    /**
     * Gets the Member ID
     * You Can Use it In MultiPlayer Functions that Needs Member id
     * @return the Member ID.
     */
    @SerializedName("_id")
    @Expose
    lateinit var id: String
        private set


    /**
     * Gets the Member Name.
     * @return the Member Name</value>
     */
    @SerializedName("name")
    @Expose
    var name: String? = null
        private set


    /**
     * Gets the Member Logo.
     * @return the Member Logo</value>
     */
    @SerializedName("logo")
    @Expose
    var logo: String? = null
        private set



    /**
     * Gets the Member Email.
     * @return the Member Email</value>
     */
    @SerializedName("email")
    @Expose
    var email: String? = null
        private set



    /**
     * Gets the Member PhoneNumber.
     * @return the Member PhoneNumber</value>
     */
    @SerializedName("phone_number")
    @Expose
    var phoneNumber: String? = null
        private set





}