/*
 * <copyright file="Notification.kt" company="Firoozeh Technology LTD">
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
import ir.firoozehcorp.gameservice.models.enums.gsLive.command.TapActionType
import java.io.Serializable

/**
 * Represents Notification Model In Game Service Command
 * @author Alireza Ghodrati
 */
class Notification : Serializable {

    /**
     * Gets the Notification Title
     * @return the Notification Title
     */
    @SerializedName("1")
    @Expose
    lateinit var title: String
        private set


    /**
     * Gets the Notification Description
     * @return the Notification Description
     */
    @SerializedName("2")
    @Expose
    lateinit var description: String
        private set


    /**
     * Gets the Notification Action Type
     * @return the Notification Action Type
     */
    @SerializedName("4")
    @Expose
    var tapAction: TapActionType = TapActionType.CloseNotification
        private set


    /**
     * Gets the Notification Action Data
     * @return the Notification Action Data
     */
    @SerializedName("5")
    @Expose
    lateinit var actionData: NotificationActionData
        private set


    /**
     * Gets the Notification Json Data
     * @return the Notification Json Data
     */
    @SerializedName("6")
    @Expose
    var jsonData: String? = null
        private set


    override fun toString(): String {
        return "Notification(title='$title', description='$description', tapAction=$tapAction, actionData=$actionData, jsonData=$jsonData)"
    }


}