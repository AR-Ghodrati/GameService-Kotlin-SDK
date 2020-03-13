/*
 * <copyright file="TSave.kt" company="Firoozeh Technology LTD">
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

package ir.FiroozehCorp.GameService.Models.BasicApi.TResponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ir.FiroozehCorp.GameService.Models.BasicApi.SaveDetails

/**
 * @author Alireza Ghodrati
 */
internal class TSave {

    @SerializedName("status")
    @Expose
    var status: Boolean = false


    @SerializedName("game")
    @Expose
    var game: String? = null


    @SerializedName("new")
    @Expose
    var details: SaveDetails? = null

}