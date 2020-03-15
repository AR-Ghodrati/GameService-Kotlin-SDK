/*
 * <copyright file="DataPayload.kt" company="Firoozeh Technology LTD">
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
import ir.firoozehcorp.gameservice.models.gsLive.Payload

/**
 * @author Alireza Ghodrati
 */
internal class DataPayload : Payload() {


    @SerializedName("0")
    @Expose
    var action: Int = -1


    @SerializedName("1")
    @Expose
    var id: String? = null


    @SerializedName("2")
    @Expose
    var data: String? = null


    @SerializedName("3")
    @Expose
    var nextId: String? = null


    @SerializedName("4")
    @Expose
    var outcomes: Outcome? = null


    @SerializedName("5")
    @Expose
    var isPrivate: Boolean = false


    override fun toString(): String {
        return "DataPayload(action=$action, id=$id, data=$data, nextId=$nextId, outcomes=$outcomes, isPrivate=$isPrivate)"
    }


}