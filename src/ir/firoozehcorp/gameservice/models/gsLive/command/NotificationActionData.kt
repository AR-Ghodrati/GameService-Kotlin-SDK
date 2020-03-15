/*
 * <copyright file="NotificationActionData.kt" company="Firoozeh Technology LTD">
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
import ir.firoozehcorp.gameservice.models.enums.gsLive.command.MarketType
import java.io.Serializable

/**
 * Represents NotificationActionData Model In Game Service Command
 * @author Alireza Ghodrati
 */
class NotificationActionData : Serializable {

    /**
     * Gets the Notification Action Type
     *  this Data may be Null if Not Set in Developer Panel
     * @return Notification Action Type
     */
    @SerializedName("1")
    @Expose
    var packageName: String? = null


    /**
     * Gets the MarketType Data
     * this Data may be Null if Not Set in Developer Panel
     * @return MarketType Data
     */
    @SerializedName("2")
    @Expose
    var marketType: MarketType = MarketType.NotSet


    /**
     * Gets the LinkAddress Data
     * this Data may be Null if Not Set in Developer Panel
     * @return the LinkAddress Data
     */
    @SerializedName("3")
    @Expose
    var linkAddress: String? = null


    /**
     * Gets the EmailAddress Data
     * this Data may be Null if Not Set in Developer Panel
     * @return the EmailAddress Data
     */
    @SerializedName("4")
    @Expose
    var emailAddress: String? = null


    /**
     * Gets the EmailTitle Data
     * this Data may be Null if Not Set in Developer Panel
     * @return the EmailTitle Data
     */
    @SerializedName("5")
    @Expose
    var emailTitle: String? = null


    /**
     * Gets the EmailBody Data
     * this Data may be Null if Not Set in Developer Panel
     * @return the EmailBody Data
     */
    @SerializedName("6")
    @Expose
    var emailBody: String? = null


    /**
     * Gets the TelegramChannel Data
     * this Data may be Null if Not Set in Developer Panel
     * @return the TelegramChannel Data
     */
    @SerializedName("7")
    @Expose
    var telegramChannel: String? = null


    /**
     * Gets the IntentAction Data
     * this Data may be Null if Not Set in Developer Panel
     * @return the IntentAction Data
     */
    @SerializedName("8")
    @Expose
    var intentAction: String? = null


    override fun toString(): String {
        return "NotificationActionData(packageName=$packageName, marketType=$marketType, linkAddress=$linkAddress, emailAddress=$emailAddress, emailTitle=$emailTitle, emailBody=$emailBody, telegramChannel=$telegramChannel, intentAction=$intentAction)"
    }


}