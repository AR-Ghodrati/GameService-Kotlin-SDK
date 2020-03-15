/*
 * <copyright file="Chat.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.models.gsLive.chat

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ir.firoozehcorp.gameservice.models.basicApi.User
import java.io.Serializable

/**
 * Represents Chat Data Model In GameService Command System
 * @author Alireza Ghodrati
 */
class Chat : Serializable {

    /**
     * Gets the Chat Privacy.
     * @return the Chat Privacy
     */
    @SerializedName("0")
    @Expose
    var isPrivate: Boolean = false


    /**
     * Gets the Chat Receiver Id.
     * @return the Chat Receiver Id
     */
    @SerializedName("1")
    @Expose
    var receiverId: String? = null


    /**
     * Gets the Chat Sender User.
     * @return the Chat Sender User
     */
    @SerializedName("2")
    @Expose
    var sender: User? = null


    /**
     *  Gets the Chat Message Data
     * @return the Chat Message Data
     */
    @SerializedName("3")
    @Expose
    var message: String? = null


    /**
     * Gets the Chat Message Send Time in Unix Time
     * @return the Chat Message Send Time in Unix Time
     */
    @SerializedName("4")
    @Expose
    var sendTime: Long = 0


    override fun toString(): String {
        return "Chat(isPrivate=$isPrivate, receiverId=$receiverId, sender=$sender, message=$message, sendTime=$sendTime)"
    }


}