/*
 * <copyright file="Area.kt" company="Firoozeh Technology LTD">
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
internal class Area {


    @SerializedName("0")
    @Expose
    var ip: String? = null
        internal set


    @SerializedName("1")
    @Expose
    var protocol: String? = null
        internal set


    @SerializedName("2")
    @Expose
    var port: Int = 0
        internal set


    @SerializedName("3")
    @Expose
    var connectToken: String? = null
        internal set


    @SerializedName("4")
    @Expose
    var cert: String? = null
        internal set


    override fun toString(): String {
        return "Area(ip='$ip', protocol='$protocol', port=$port, connectToken=$connectToken, cert=$cert)"
    }

}