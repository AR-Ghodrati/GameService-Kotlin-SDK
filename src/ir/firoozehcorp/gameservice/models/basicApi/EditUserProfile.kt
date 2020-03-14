/*
 * <copyright file="EditUserProfile.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.models.basicApi

/**
 * Represents EditUserProfile In Game Service Basic API
 * @author Alireza Ghodrati
 */
class EditUserProfile(
        /**
         * Set New NickName For CurrentPlayer.
         */
        var nickName: String? = null,
        /**
         * Set New ProfileLogo(BytesBuffer) For CurrentPlayer.
         */
        var logoBuffer: ByteArray? = null,
        /**
         * Set Allow Auto Add To Game For CurrentPlayer.
         */
        var allowAutoAddToGame: Boolean = false,
        /**
         * Set Show Public Activity For CurrentPlayer.
         */
        var showPublicActivity: Boolean = false,
        /**
         * Set Show Group Activity For CurrentPlayer.
         */
        var showGroupActivity: Boolean = false)