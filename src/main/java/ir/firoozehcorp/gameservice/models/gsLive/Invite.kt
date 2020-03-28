/*
 * <copyright file="Invite.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.models.gsLive

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ir.firoozehcorp.gameservice.models.basicApi.User
import ir.firoozehcorp.gameservice.models.internal.Game
import java.io.Serializable

/**
 * Represents Invite Data Model In GameService MultiPlayer System (GSLive)
 * @author Alireza Ghodrati
 */
class Invite : Serializable {

    /**
     * Gets the Invite id.
     * You Can Use It in Accept Invite
     * @return the Invite id
     */
    @SerializedName("_id")
    @Expose
    lateinit var id: String
        private set


    /**
     * Gets the Inviter User.
     * @return the Inviter User
     */
    @SerializedName("inviter")
    @Expose
    lateinit var inviter: User
        private set


    /**
     * Gets the Invited User Id.
     * @return the Invited User Id
     */
    @SerializedName("inviter")
    @Expose
    lateinit var invited: String
        private set


    /**
     * Gets The Room Where You Are Invited.
     * @return  The Room Where You Are Invited
     */
    @SerializedName("room")
    @Expose
    lateinit var room: Room
        private set


    /**
     * Gets The Game Which You Are Invited.
     * @return  The Game Which You Are Invited.
     */
    @SerializedName("game")
    @Expose
    lateinit var game: Game
        private set
}