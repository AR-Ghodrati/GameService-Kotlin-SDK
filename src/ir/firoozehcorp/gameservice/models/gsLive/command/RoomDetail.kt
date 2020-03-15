/*
 * <copyright file="RoomDetail.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.models.gsLive.command

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author Alireza Ghodrati
 */
internal class RoomDetail {


    @SerializedName("0")
    @Expose
    var id: String? = null


    @SerializedName("1")
    @Expose
    var user: String? = null


    @SerializedName("2")
    @Expose
    var invite: String? = null


    @SerializedName("3")
    @Expose
    var name: String? = null


    @SerializedName("4")
    @Expose
    var type: Int = -1


    @SerializedName("5")
    @Expose
    var min: Int = -1


    @SerializedName("6")
    @Expose
    var max: Int = -1


    @SerializedName("7")
    @Expose
    var gsLiveType: Int = -1


    @SerializedName("8")
    @Expose
    var role: String? = null


    @SerializedName("9")
    @Expose
    var isPrivate: Boolean = false


    @SerializedName("10")
    @Expose
    var isPersist: Boolean = false


    override fun toString(): String {
        return "RoomDetail(id=$id, user=$user, invite=$invite, name=$name, type=$type, min=$min, max=$max, gsLiveType=$gsLiveType, role=$role, isPrivate=$isPrivate, isPersist=$isPersist)"
    }


}