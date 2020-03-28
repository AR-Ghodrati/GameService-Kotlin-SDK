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

package ir.firoozehcorp.gameservice.models.gsLive.realtime

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Represents Message Data Model In GameService RealTime MultiPlayer System
 * @author Alireza Ghodrati
 */
class Message : Serializable {


    /**
     * Gets the RoomId Receive This Message.
     * @return the RoomId Receive This Message.
     */
    @SerializedName("1")
    @Expose
    lateinit var roomId: String
        internal set


    /**
     * Gets the Sender Id.
     * @return the Sender Id.
     */
    @SerializedName("2")
    @Expose
    lateinit var senderId: String
        internal set


    /**
     * Gets the Receiver Id.
     * @return the Receiver Id.
     */
    @SerializedName("3")
    @Expose
    lateinit var receiverId: String
        internal set


    /**
     * Gets the Message Data
     * @return the Message Data
     */
    @SerializedName("4")
    @Expose
    var data: String? = null
        internal set


    override fun toString(): String {
        return "Message(roomId=$roomId, senderId=$senderId, receiverId=$receiverId, data=$data)"
    }


}