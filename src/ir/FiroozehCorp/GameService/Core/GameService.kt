/*
 * <copyright file="GameService.kt" company="Firoozeh Technology LTD">
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
package ir.FiroozehCorp.GameService.Core

import ir.FiroozehCorp.GameService.Builder.GameServiceClientConfiguration
import ir.FiroozehCorp.GameService.Core.ApiWebRequest.ApiRequest
import ir.FiroozehCorp.GameService.Models.BasicApi.Leaderboard
import ir.FiroozehCorp.GameService.Models.GameServiceException
import ir.FiroozehCorp.GameService.Models.Internal.Game
import ir.FiroozehCorp.GameService.Models.Internal.GameServiceResponse
import ir.FiroozehCorp.GameService.Utils.NetworkUtil

/**
 * Represents Game Service Main Initializer
 * @author Alireza Ghodrati
 */
object GameService {

    private const val Tag = "FiroozehGameService"
    internal var isAvailable = false
    internal var UserToken: String? = null
    internal var PlayToken: String? = null
    internal var CurrentGame: Game? = null
    internal var StartPlaying: Long = 0
    internal var IsGuest = false
    internal var Configuration: GameServiceClientConfiguration? = null
    //public static GSLive.GSLive GSLive { get; private set; }
    //private static DownloadManager _downloadManager;


    /**
     * Set configuration For Initialize Game Service.
     * @param configuration For Initialize Game Service(Not NULL)
     */
    @Throws(GameServiceException::class)
    fun configurationInstance(configuration: GameServiceClientConfiguration?) {
        if (configuration == null) throw GameServiceException("Configuration Cant Be Null")
        if (isAuthenticated()) throw GameServiceException("Must Logout First To ReConfiguration")
        Configuration = configuration
        //_downloadManager = new DownloadManager(Configuration);
        //GSLive = new GSLive.GSLive();
    }


    /*internal static void OnNotificationReceived(Notification notification)
            => NotificationReceived?.Invoke(null,notification);*/
    /**
     * With this command you can get list of all your game LeaderBoard
     * That you have registered in the Developer panel
     * @return GetLeaderBoards List
     */
    @Throws(GameServiceException::class)
    fun getLeaderBoards(): GameServiceResponse<MutableList<Leaderboard>> {
        if (!isAuthenticated()) throw GameServiceException("GameService Not Available")
        //return ApiRequest.getLeaderBoard()
    }


    /**
     * Normal Login (InFirstOnly) To Game Service
     * It May Throw Exception
     * @return return UserToken if Login Successfully
     */
    @Throws(GameServiceException::class)
    fun login(email: String, password: String): String {
        if (!NetworkUtil.isConnected()) throw GameServiceException("Network Unreachable")
        if (Configuration == null) throw GameServiceException("You Must Configuration First")
        if (email.isEmpty()) throw GameServiceException("Email Cant Be Empty")
        if (password.isEmpty()) throw GameServiceException("PassWord Cant Be Empty")
        if (isAuthenticated()) logout()

        val login = ApiRequest.login(email, password)
        this.UserToken = login.token
        val auth = ApiRequest.authorize()
        this.PlayToken = auth.token
        this.CurrentGame = auth.game
        this.StartPlaying = System.currentTimeMillis()
        this.isAvailable = true
        this.IsGuest = false
        //await Core.GSLive.GSLive.Init();
        return UserToken.toString()
    }


    /**
     * Normal Login With UserToken To Game Service
     * It May Throw Exception
     */
    @Throws(GameServiceException::class)
    fun login(userToken: String) {
        if (!NetworkUtil.isConnected()) throw GameServiceException("Network Unreachable")
        if (Configuration == null) throw GameServiceException("You Must Configuration First")
        if (userToken.isEmpty()) throw GameServiceException("userToken Cant Be Empty")
        if (isAuthenticated()) logout()

        this.UserToken = userToken
        val auth = ApiRequest.authorize()
        this.PlayToken = auth.token
        this.CurrentGame = auth.game
        this.StartPlaying = System.currentTimeMillis()
        this.isAvailable = true
        this.IsGuest = false
        //await Core.GSLive.GSLive.Init();
    }


    /**
     * Normal Login With GoogleSignInUser To Game Service
     * It May Throw Exception
     * @param idToken (Not NULL)Specifies the idToken From GoogleSignInUser Class
     * @return UserToken if Login Successfully
     */
    @Throws(GameServiceException::class)
    fun loginWithGoogle(idToken: String) {
        if (!NetworkUtil.isConnected()) throw GameServiceException("Network Unreachable")
        if (Configuration == null) throw GameServiceException("You Must Configuration First")
        if (idToken.isEmpty()) throw GameServiceException("userToken Cant Be Empty")
        if (isAuthenticated()) logout()

        val login = ApiRequest.loginWithGoogle(idToken)
        this.UserToken = login.token
        val auth = ApiRequest.authorize()
        this.PlayToken = auth.token
        this.CurrentGame = auth.game
        this.StartPlaying = System.currentTimeMillis()
        this.isAvailable = true
        this.IsGuest = false
        //await Core.GSLive.GSLive.Init();
    }


    /**
     * Login To Game Service As Guest
     * It May Throw Exception
     */
    @Throws(GameServiceException::class)
    fun login() {
        if (!NetworkUtil.isConnected()) throw GameServiceException("Network Unreachable")
        if (Configuration == null) throw GameServiceException("You Must Configuration First")
        if (isAuthenticated()) logout()

        val login = ApiRequest.loginAsGuest()
        this.UserToken = login.token
        val auth = ApiRequest.authorize()
        this.PlayToken = auth.token
        this.CurrentGame = auth.game
        this.StartPlaying = System.currentTimeMillis()
        this.isAvailable = true
        this.IsGuest = true
        //CoreEventHandlers.SuccessfullyLogined?.Invoke(null,null);
    }


    /**
     * Normal SignUp To Game Service
     * It May Throw Exception
     * @return return UserToken if signUp Successfully
     */
    @Throws(GameServiceException::class)
    fun signUp(nickName: String, email: String, password: String): String {
        if (!NetworkUtil.isConnected()) throw GameServiceException("Network Unreachable")
        if (Configuration == null) throw GameServiceException("You Must Configuration First")
        if (nickName.isEmpty()) throw GameServiceException("NickName Cant Be Empty")
        if (email.isEmpty()) throw GameServiceException("Email Cant Be Empty")
        if (password.isEmpty()) throw GameServiceException("PassWord Cant Be Empty")
        if (isAuthenticated()) logout()

        val login = ApiRequest.signUp(nickName, email, password)
        this.UserToken = login.token
        val auth = ApiRequest.authorize()
        this.PlayToken = auth.token
        this.CurrentGame = auth.game
        this.StartPlaying = System.currentTimeMillis()
        this.isAvailable = true
        this.IsGuest = false
        //await Core.GSLive.GSLive.Init();
        return UserToken.toString()
    }


    /**
     * Check if Current User Authenticated
     * @return True if Current User Authenticated Before
     */
    fun isAuthenticated(): Boolean {
        return isAvailable
    }

    /**
     * Get The Current GameService Version
     * @return The Current GameService Version
     */
    fun version(): String {
        return "2.0.3"
    }

    /**
     * Logout From Game Service
     */
    fun logout() {
        UserToken = null
        CurrentGame = null
        PlayToken = null
        isAvailable = false
        IsGuest = false
        //GSLive?.Dispose();
    }
}