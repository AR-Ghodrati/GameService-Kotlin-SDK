/*
 * <copyright file="BucketCore.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.models.basicApi.bucket

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Represents BucketCore Data Model In Game Service Basic API
 * @author Alireza Ghodrati
 */
open class BucketCore : Serializable {

    /**
     * Gets the bucket Object ID.
     * @return the bucket Object ID</value>
     */
    @SerializedName("id")
    @Expose(serialize = false)
    var id: String? = null
        private set


    /**
     * Gets the bucket Owner User ID
     * @return the bucket Object ID</value>
     */
    @SerializedName("owner")
    @Expose(serialize = false)
    var ownerId: String? = null
        private set


    override fun toString(): String {
        return "BucketCore(id=$id, ownerId=$ownerId)"
    }


}