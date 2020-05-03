/*
 * <copyright file="Api.kt" company="Firoozeh Technology LTD">
 * Copyright (C) 2020. Firoozeh Technology LTD. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
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

package ir.firoozehcorp.gameservice.models.consts

/**
 * @author Alireza Ghodrati
 */
internal object Api {

    const val BaseUrl1 = "https://gamesservice.ir"
    private const val BaseUrl2 = "https://api.gamesservice.ir"

    const val LoginUser = "$BaseUrl2/auth/app/login"
    const val LoginWithGoogle = "$BaseUrl2/auth/g/callback"
    const val Start = "$BaseUrl2/auth/start"
    const val GetCurrentPlayerData = "$BaseUrl2/v1"
    const val GetUserData = "$BaseUrl2/v1/user/"

    const val GetMemberData = "$BaseUrl2/v1/member/"


    const val SaveGame = "$BaseUrl2/v1/savegame/"
    const val Achievements = "$BaseUrl2/v1/achievement/"
    const val Leaderboard = "$BaseUrl2/v1/leaderboard/"
    const val Bucket = "$BaseUrl2/v1/bucket/"

    const val CurrentTime = "$BaseUrl2/syncedtime"

    const val FaaS = "https://faas.gamesservice.ir/"

    const val UserProfileLogo = "$BaseUrl1/Application/image"
    const val UserProfile = "$BaseUrl1/Application"
}