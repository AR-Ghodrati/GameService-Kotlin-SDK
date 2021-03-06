/*
 * <copyright file="gameservice.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.core

import ir.firoozehcorp.gameservice.builder.GameServiceClientConfiguration
import ir.firoozehcorp.gameservice.core.apiWebRequest.ApiRequest
import ir.firoozehcorp.gameservice.core.apiWebRequest.DownloadRequest
import ir.firoozehcorp.gameservice.core.gslive.GSLive
import ir.firoozehcorp.gameservice.models.GameServiceException
import ir.firoozehcorp.gameservice.models.annotations.NotNull
import ir.firoozehcorp.gameservice.models.annotations.Nullable
import ir.firoozehcorp.gameservice.models.basicApi.*
import ir.firoozehcorp.gameservice.models.basicApi.bucket.BucketOption
import ir.firoozehcorp.gameservice.models.consts.Command
import ir.firoozehcorp.gameservice.models.gsLive.Member
import ir.firoozehcorp.gameservice.models.gsLive.command.Notification
import ir.firoozehcorp.gameservice.models.internal.AssetInfo
import ir.firoozehcorp.gameservice.models.internal.EventHandler
import ir.firoozehcorp.gameservice.models.internal.GSTime
import ir.firoozehcorp.gameservice.models.internal.Game
import ir.firoozehcorp.gameservice.models.internal.interfaces.GameServiceCallback
import ir.firoozehcorp.gameservice.models.listeners.CoreListeners
import ir.firoozehcorp.gameservice.utils.NetworkUtil

/**
 * Represents Game Service Main Initializer
 * @author Alireza Ghodrati
 */
object GameService {

    private const val Tag = "FiroozehGameService"

    internal var isAvailable = false
        private set
    internal var UserToken: String? = null
        private set
    internal var PlayToken: String? = null
        private set
    internal var CurrentGame: Game? = null
        private set
    internal var StartPlaying: Long = 0
        private set
    internal var IsGuest = false
        private set
    internal var CommandInfo: CommandInfo? = null
        private set
    internal var Configuration: GameServiceClientConfiguration? = null
        private set


    interface NotificationListener : EventHandler.IEventHandler<Notification> {
        override fun invoke(element: Notification, from: Class<*>?)
    }


    val GSLive: GSLive = GSLive()
    val OnNotificationReceived: EventHandler<NotificationListener, Notification> = EventHandler()

    /**
     * Set configuration For Initialize Game Service.
     * @param configuration For Initialize Game Service(Not NULL)
     */
    @Throws(GameServiceException::class)
    fun configurationInstance(configuration: GameServiceClientConfiguration?) {
        if (configuration == null) throw GameServiceException("Configuration Cant Be Null")
        if (isAuthenticated()) throw GameServiceException("Must Logout First To ReConfiguration")
        Configuration = configuration
    }



    /**
     * With this command you can get list of all your game LeaderBoard
     * That you have registered in the Developer panel
     * @param callback returns GetLeaderBoards List
     */
    @Throws(GameServiceException::class)
    fun getLeaderBoards(callback: GameServiceCallback<MutableList<Leaderboard>>) {
        if (!isAuthenticated()) throw GameServiceException("gameservice Not Available")
        ApiRequest.getLeaderBoards(callback)
    }


    /**
     * With this command you can get list of all your game achievements
     * That you have registered in the Developer panel
     * @param callback returns GetAchievements List
     * */
    @Throws(GameServiceException::class)
    fun getAchievements(callback: GameServiceCallback<MutableList<Achievement>>) {
        if (!isAuthenticated()) throw GameServiceException("gameservice Not Available")
        ApiRequest.getAchievements(callback)
    }


    /**
     * With this command you can save your Current Status in Game
     * @param saveName Specifies the saveGameName
     * @param saveObj the Object that you Want To Save it
     * @param callback returns SaveDetails
     */
    @Throws(GameServiceException::class)
    fun saveGame(@NotNull saveName: String, saveObj: Any, callback: GameServiceCallback<SaveDetails>) {
        if (!isAuthenticated()) throw GameServiceException("gameservice Not Available")
        ApiRequest.saveGame(saveName, saveObj, callback)
    }


    /**
     * This command allows you to Submit Player Score with the ID of the leaderBoard
     * you have Registered in the Developer panel
     * @param leaderBoardKey Specifies the leaderBoardId
     * @param scoreValue scoreValue(The value must not exceed the maximum value Registered in the Developer Panel)
     * @param callback returns SubmitScoreResponse
     */
    @Throws(GameServiceException::class)
    fun submitScore(@NotNull leaderBoardKey: String, scoreValue: Int, callback: GameServiceCallback<SubmitScoreResponse>) {
        if (!isAuthenticated()) throw GameServiceException("gameservice Not Available")
        if (leaderBoardKey.isEmpty()) throw GameServiceException("leaderBoardKey Cant Be EmptyOrNull")
        if (scoreValue <= 0) throw GameServiceException("Invalid ScoreValue")

        ApiRequest.submitScore(leaderBoardKey, scoreValue, callback)
    }


    /**
     * With this command you can Unlock achievement with the achievement ID
     * you have Registered in the Developer panel
     * @param achievementKey The ID of Achievement you Want To Unlock it
     * @param callback returns unlocked Achievement
     */
    @Throws(GameServiceException::class)
    fun unlockAchievement(@NotNull achievementKey: String, callback: GameServiceCallback<Achievement>) {
        if (!isAuthenticated()) throw GameServiceException("gameservice Not Available")
        if (achievementKey.isEmpty()) throw GameServiceException("achievementKey Cant Be EmptyOrNull")

        ApiRequest.unlockAchievement(achievementKey, callback)
    }


    /**
     * This command will get you the last save you saved
     * @param callback Player Last Save
     */
    @Throws(GameServiceException::class)
    fun <T> getSaveGame(saveType: Class<T>, callback: GameServiceCallback<T>) {
        if (!isAuthenticated()) throw GameServiceException("gameservice Not Available")
        ApiRequest.getSaveGame(saveType, callback)
    }


    /**
     * With this command you can get a LeaderBoardDetails with the ID of the LeaderBoard list
     * you have Registered in the Developer panel
     * @param leaderBoardKey The ID of leaderBoard you Want To get Detail
     * @param scoreLimit (Min = 10,Max = 50) The Score List Limits
     * @param callback returns LeaderBoardDetails
     */
    @Throws(GameServiceException::class)
    fun getLeaderBoardDetails(@NotNull leaderBoardKey: String, scoreLimit: Int = 10, callback: GameServiceCallback<LeaderBoardDetails>) {
        if (!isAuthenticated()) throw GameServiceException("gameservice Not Available")
        if (leaderBoardKey.isEmpty()) throw GameServiceException("leaderBoardKey Cant Be EmptyOrNull")
        if (scoreLimit < 10 || scoreLimit > 50) throw GameServiceException("Invalid scoreLimit")

        ApiRequest.getLeaderBoardDetails(leaderBoardKey, scoreLimit, callback)
    }


    /**
     *  With this command you can get Current Player Score with the ID of the LeaderBoard id
     *   you registered in the Developer panel.
     * @param leaderBoardKey the Key of leaderBoard you Want To get Score
     * @param callback returns Score Data
     */
    @Throws(GameServiceException::class)
    fun getCurrentPlayerScore(@NotNull leaderBoardKey : String,callback: GameServiceCallback<Score>) {
        if (!isAuthenticated()) throw GameServiceException("gameservice Not Available")
        if (leaderBoardKey.isEmpty()) throw GameServiceException("leaderBoardKey Cant Be EmptyOrNull")
        ApiRequest.getCurrentPlayerScore(leaderBoardKey,callback)
    }



    /**
     * With this command you can get information about the current player is playing
     * @param callback returns CurrentPlayer Data
     */
    @Throws(GameServiceException::class)
    fun getCurrentPlayer(callback: GameServiceCallback<Member>) {
        if (!isAuthenticated()) throw GameServiceException("gameservice Not Available")
        ApiRequest.getCurrentPlayer(callback)
    }


    /**
     *  With this command you can get a User Data with the User ID
     * @param userId The ID of User you Want To get Detail
     * @param callback returns User Data
     */
    @Throws(GameServiceException::class)
    @Deprecated("This Method is Deprecated,Use GetMemberData() Instead", ReplaceWith("getMemberData()"))
    fun getUserData(@NotNull userId: String, callback: GameServiceCallback<User>) {
        if (!isAuthenticated()) throw GameServiceException("gameservice Not Available")
        ApiRequest.getUserData(userId, callback)
    }


    /**
     *  With this command you can get a Member Data with the Member ID
     * @param memberId The ID of Member you Want To get Detail
     * @param callback returns Member Data
     */
    @Throws(GameServiceException::class)
    fun getMemberData(@NotNull memberId: String, callback: GameServiceCallback<Member>) {
        if (!isAuthenticated()) throw GameServiceException("gameservice Not Available")
        ApiRequest.getMemberData(memberId, callback)
    }


    /**
     *  With this command you can get The Last Login Member Info
     * @param callback returns Member Data
     */
    @Throws(GameServiceException::class)
    fun getLastLoginMemberInfo(callback: GameServiceCallback<MemberInfo>) {
        if (!isAuthenticated()) throw GameServiceException("gameservice Not Available")
        ApiRequest.getLastLoginMemberInfo(callback)
    }


    /**
     * With this command you can Edit information about the current player is playing
     * @param callback returns CurrentPlayer Data
     */
    @Throws(GameServiceException::class)
    fun editCurrentPlayerProfile(editUserProfile: EditUserProfile, callback: GameServiceCallback<Member>) {
        if (!isAuthenticated()) throw GameServiceException("gameservice Not Available")
        ApiRequest.editCurrentPlayer(editUserProfile, callback)
    }


    /**
     * This command can remove the last current user saved
     * @param callback returns true if Remove Successfully
     */
    @Throws(GameServiceException::class)
    fun removeLastSave(callback: GameServiceCallback<Boolean>) {
        if (!isAuthenticated()) throw GameServiceException("gameservice Not Available")
        ApiRequest.removeLastSave(callback)
    }


    /**
     * This command will return all information about the bucket with a specific ID
     * @param bucketId The ID of bucket you Want To get Detail
     * @param options (Optional)The bucket Options
     * @param callback return List of all bucket Items
     */
    @Throws(GameServiceException::class)
    fun <T> getBucketItems(@NotNull bucketId: String, bucketType: Class<T>, @Nullable options: Array<BucketOption>? = null, callback: GameServiceCallback<MutableList<T>>) {
        if (!isAuthenticated()) throw GameServiceException("gameservice Not Available")
        if (bucketId.isEmpty()) throw GameServiceException("BucketId Cant Be EmptyOrNull")
        ApiRequest.getBucketItems(bucketId, options, bucketType, callback)
    }


    /**
     * This command returns one of the Specific bucket information with a specific ID
     * @param bucketId The ID of bucket you Want To get Detail
     * @param itemId The ID of BucketItem you Want To get Detail
     * @param callback return a bucket Item
     */
    @Throws(GameServiceException::class)
    fun <T> getBucketItem(@NotNull bucketId: String, @NotNull itemId: String, bucketType: Class<T>, callback: GameServiceCallback<T>) {
        if (!isAuthenticated()) throw GameServiceException("gameservice Not Available")
        if (bucketId.isEmpty()) throw GameServiceException("BucketId Cant Be EmptyOrNull")
        if (itemId.isEmpty()) throw GameServiceException("BucketItemId Cant Be EmptyOrNull")

        ApiRequest.getBucketItem(bucketId, itemId, bucketType, callback)
    }


    /**
     * This command will edit one of the bucket information with a specific ID
     * @param bucketId The ID of bucket you Want To get Detail
     * @param itemId The ID of BucketItem you Want To get Detail
     * @param editedBucket The Object of BucketItem you Want To Edit Detail
     * @param callback return Edited bucket Item
     */
    @Throws(GameServiceException::class)
    fun <T> editBucketItem(@NotNull bucketId: String, @NotNull itemId: String, @NotNull editedBucket: T, bucketType: Class<T>, callback: GameServiceCallback<T>) {
        if (!isAuthenticated()) throw GameServiceException("gameservice Not Available")
        if (bucketId.isEmpty()) throw GameServiceException("BucketId Cant Be EmptyOrNull")
        if (itemId.isEmpty()) throw GameServiceException("BucketItemId Cant Be EmptyOrNull")

        ApiRequest.updateBucketItem(bucketId, itemId, editedBucket, bucketType, callback)
    }


    /**
     * This command will Add new bucket information with a specific ID
     * @param bucketId The ID of bucket you Want To get Detail
     * @param newBucket The Object of BucketItem you Want To Add
     * @param callback  return Added bucket Item
     */
    @Throws(GameServiceException::class)
    fun <T> addBucketItem(@NotNull bucketId: String, @NotNull newBucket: T, bucketType: Class<T>, callback: GameServiceCallback<T>) {
        if (!isAuthenticated()) throw GameServiceException("gameservice Not Available")
        if (bucketId.isEmpty()) throw GameServiceException("BucketId Cant Be EmptyOrNull")

        ApiRequest.addBucketItem(bucketId, newBucket, bucketType, callback)
    }


    /**
     * This command will delete All of the bucket Items information with a specific ID
     * @param bucketId The ID of bucket you Want To Delete All Items
     * @param callback Return true if Remove Successfully
     */
    @Throws(GameServiceException::class)
    fun deleteBucketItems(@NotNull bucketId: String, callback: GameServiceCallback<Boolean>) {
        if (!isAuthenticated()) throw GameServiceException("gameservice Not Available")
        if (bucketId.isEmpty()) throw GameServiceException("BucketId Cant Be EmptyOrNull")

        ApiRequest.deleteBucketItems(bucketId, callback)
    }


    /**
     * This command will delete one of the bucket information with a specific ID
     * @param bucketId The ID of bucket you Want To Delete one of Items
     * @param itemId The ID of BucketItem you Want To Delete it
     * @param callback Return true if Remove Successfully
     */
    @Throws(GameServiceException::class)
    fun deleteBucketItem(@NotNull bucketId: String, @NotNull itemId: String, callback: GameServiceCallback<Boolean>) {
        if (!isAuthenticated()) throw GameServiceException("gameservice Not Available")
        if (bucketId.isEmpty()) throw GameServiceException("BucketId Cant Be EmptyOrNull")

        ApiRequest.deleteBucketItem(bucketId, itemId, callback)
    }


    /**
     * This command Gets The Current Times
     * @param callback return The Current Times
     */
    @Throws(GameServiceException::class)
    fun getCurrentTime(callback: GameServiceCallback<GSTime>) {
        ApiRequest.getCurrentServerTime(callback)
    }


    /**
     *  This command Check Can Login With Phone Number
     * @param callback returns The Status
     */
    @Throws(GameServiceException::class)
    fun canLoginWithPhoneNumber(callback: GameServiceCallback<Boolean>) {
        if (Configuration == null) throw GameServiceException("You Must Configuration First")
        ApiRequest.checkPhoneLoginStatus(callback)
    }



    /**
     * Gets Asset Info With AssetTag
     * @param assetTag Specifies the Asset tag that Set in Developers Panel
     * @param callback return The AssetInfo
     */
    @Throws(GameServiceException::class)
    fun getAssetInfo(@NotNull assetTag: String, callback: GameServiceCallback<AssetInfo>) {
        if (Configuration == null) throw GameServiceException("You Must Configuration First")
        if (assetTag.isEmpty()) throw GameServiceException("assetTag Cant Be EmptyOrNull")

        ApiRequest.getAssetInfo(Configuration?.clientId.toString(), assetTag, callback)
    }


    /**
     * Download Asset With Tag
     * @param assetTag Specifies the Asset tag that Set in Developers Panel
     * @param dirPath Specifies the Download File Directory Path
     * @param callback return True if Download Done
     */
    @Throws(GameServiceException::class)
    fun downloadAsset(@NotNull assetTag: String, @NotNull dirPath: String, callback: GameServiceCallback<Boolean>) {
        if (Configuration == null) throw GameServiceException("You Must Configuration First")
        if (assetTag.isEmpty()) throw GameServiceException("assetTag Cant Be EmptyOrNull")
        if (dirPath.isEmpty()) throw GameServiceException("DownloadDirPath Cant Be EmptyOrNull")

        DownloadRequest.downloadAsset(dirPath, assetTag, Configuration?.clientId.toString(), callback)
    }


    /**
     * Download Asset With AssetInfo
     * @param assetInfo Specifies the Asset Info
     * @param dirPath Specifies the Download File Directory Path
     * @param callback return True if Download Done
     */
    @Throws(GameServiceException::class)
    fun downloadAsset(@NotNull assetInfo: AssetInfo, @NotNull dirPath: String, callback: GameServiceCallback<Boolean>) {
        if (Configuration == null) throw GameServiceException("You Must Configuration First")
        if (dirPath.isEmpty()) throw GameServiceException("DownloadDirPath Cant Be EmptyOrNull")

        DownloadRequest.downloadAssetWithInfo(dirPath, assetInfo, callback)
    }


    /**
     * Execute Cloud Function
     * note : if Function is public , You Can Call it without Login
     * @param functionId Specifies the function Id that Set in Developers Panel
     * @param functionParameters Specifies the Function Input Parameter Class that Set in Developers Panel
     * @param callback return The AssetInfo
     */
    @Throws(GameServiceException::class)
    fun executeCloudFunction(@NotNull functionId: String, @Nullable functionParameters: Any? = null, isPublic: Boolean = false, callback: GameServiceCallback<String>) {
        if (!isPublic && !isAuthenticated()) throw GameServiceException("You Must Login First In Private Mode")
        if (functionId.isEmpty()) throw GameServiceException("functionId Cant Be EmptyOrNull")

        ApiRequest.executeCloudFunction(functionId, functionParameters, isPublic, callback)
    }


    /**
     *  This command Check Can Login With Phone Number
     * @param phoneNumber Specifies the Phone Number
     * @param callback returns The Status
     */
    @Throws(GameServiceException::class)
    fun sendLoginCodeWithSms(@NotNull phoneNumber : String, callback: GameServiceCallback<Boolean>) {
        if (!NetworkUtil.isConnected()) throw GameServiceException("Network Unreachable")
        if (Configuration == null) throw GameServiceException("You Must Configuration First")
        if (phoneNumber.isEmpty()) throw GameServiceException("phoneNumber Cant Be Empty")
        if (isAuthenticated()) logout()
        ApiRequest.checkPhoneLoginStatus(phoneNumber,callback)
    }



    /**
     * Normal Login (InFirstOnly) To Game Service
     * It May Throw Exception
     * @param callback returns UserToken if Login Successfully
     */
    @Throws(GameServiceException::class)
    fun login(@NotNull email: String, @NotNull password: String, callback: GameServiceCallback<String>) {
        if (!NetworkUtil.isConnected()) throw GameServiceException("Network Unreachable")
        if (Configuration == null) throw GameServiceException("You Must Configuration First")
        if (email.isEmpty()) throw GameServiceException("Email Cant Be Empty")
        if (password.isEmpty()) throw GameServiceException("PassWord Cant Be Empty")
        if (isAuthenticated()) logout()

        ApiRequest.login(email, password, object : GameServiceCallback<Login> {
            override fun onResponse(response: Login) {
                UserToken = response.token
                ApiRequest.authorize(object : GameServiceCallback<Login> {
                    override fun onResponse(response: Login) {
                        PlayToken = response.token
                        CurrentGame = response.game
                        CommandInfo = response.commandInfo
                        StartPlaying = System.currentTimeMillis()
                        isAvailable = true
                        IsGuest = false
                        GSLive.init()
                        callback.onResponse(UserToken.toString())
                    }

                    override fun onFailure(error: GameServiceException) {
                        callback.onFailure(error)
                    }

                })

            }

            override fun onFailure(error: GameServiceException) {
                callback.onFailure(error)
            }

        })
    }


    /**
     * Normal Login With UserToken To Game Service
     * It May Throw Exception
     */
    @Throws(GameServiceException::class)
    fun login(@NotNull userToken: String, callback: GameServiceCallback<Boolean>) {
        if (!NetworkUtil.isConnected()) throw GameServiceException("Network Unreachable")
        if (Configuration == null) throw GameServiceException("You Must Configuration First")
        if (userToken.isEmpty()) throw GameServiceException("userToken Cant Be Empty")
        if (isAuthenticated()) logout()

        UserToken = userToken
        ApiRequest.authorize(object : GameServiceCallback<Login> {
            override fun onResponse(response: Login) {
                PlayToken = response.token
                CurrentGame = response.game
                CommandInfo = response.commandInfo
                StartPlaying = System.currentTimeMillis()
                isAvailable = true
                IsGuest = false
                GSLive.init()
                callback.onResponse(true)
            }

            override fun onFailure(error: GameServiceException) {
                callback.onFailure(error)
            }

        })
    }


    /**
     * Normal Login With GoogleSignInUser To Game Service
     * It May Throw Exception
     * @param idToken Specifies the idToken From GoogleSignInUser Class
     * @param callback returns UserToken if Login Successfully
     */
    @Throws(GameServiceException::class)
    fun loginWithGoogle(@NotNull idToken: String, callback: GameServiceCallback<String>) {
        if (!NetworkUtil.isConnected()) throw GameServiceException("Network Unreachable")
        if (Configuration == null) throw GameServiceException("You Must Configuration First")
        if (idToken.isEmpty()) throw GameServiceException("userToken Cant Be Empty")
        if (isAuthenticated()) logout()

        ApiRequest.loginWithGoogle(idToken, object : GameServiceCallback<Login> {
            override fun onResponse(response: Login) {
                UserToken = response.token
                ApiRequest.authorize(object : GameServiceCallback<Login> {
                    override fun onResponse(response: Login) {
                        PlayToken = response.token
                        CurrentGame = response.game
                        CommandInfo = response.commandInfo
                        StartPlaying = System.currentTimeMillis()
                        isAvailable = true
                        IsGuest = false
                        GSLive.init()
                        callback.onResponse(UserToken.toString())
                    }

                    override fun onFailure(error: GameServiceException) {
                        callback.onFailure(error)
                    }

                })
            }

            override fun onFailure(error: GameServiceException) {
                callback.onFailure(error)
            }

        })
    }



    /**
     * Normal Login With Phone Number To Game Service
     * You Must Call SendLoginCodeWithSms First, to get SMS Code.
     * It May Throw Exception
     * @param nickName Specifies Nick Name
     * @param phoneNumber Specifies Phone Number
     * @param smsCode Specifies sms Code
     */
    @Throws(GameServiceException::class)
    fun loginWithPhoneNumber(@NotNull nickName: String,@NotNull phoneNumber: String,@NotNull smsCode: String, callback: GameServiceCallback<String>) {
        if (Configuration == null) throw GameServiceException("You Must Configuration First")
        if (!NetworkUtil.isConnected()) throw GameServiceException("Network Unreachable")
        if (nickName.isEmpty()) throw GameServiceException("nickName Cant Be Empty")
        if (phoneNumber.isEmpty()) throw GameServiceException("phoneNumber Cant Be Empty")
        if (smsCode.isEmpty()) throw GameServiceException("smsCode Cant Be Empty")
        if (isAuthenticated()) logout()

        ApiRequest.loginWithPhoneNumber(nickName,phoneNumber,smsCode, object : GameServiceCallback<Login> {
            override fun onResponse(response: Login) {
                UserToken = response.token
                ApiRequest.authorize(object : GameServiceCallback<Login> {
                    override fun onResponse(response: Login) {
                        PlayToken = response.token
                        CurrentGame = response.game
                        CommandInfo = response.commandInfo
                        StartPlaying = System.currentTimeMillis()
                        isAvailable = true
                        IsGuest = false
                        GSLive.init()
                        callback.onResponse(UserToken.toString())
                    }

                    override fun onFailure(error: GameServiceException) {
                        callback.onFailure(error)
                    }

                })
            }

            override fun onFailure(error: GameServiceException) {
                callback.onFailure(error)
            }

        })
    }


    /**
     * Login To Game Service As Guest
     * It May Throw Exception
     */
    @Throws(GameServiceException::class)
    fun login(callback: GameServiceCallback<Boolean>) {
        if (!NetworkUtil.isConnected()) throw GameServiceException("Network Unreachable")
        if (Configuration == null) throw GameServiceException("You Must Configuration First")
        if (isAuthenticated()) logout()

        ApiRequest.loginAsGuest(object : GameServiceCallback<Login> {
            override fun onResponse(response: Login) {
                UserToken = response.token
                ApiRequest.authorize(object : GameServiceCallback<Login> {
                    override fun onResponse(response: Login) {
                        PlayToken = response.token
                        CurrentGame = response.game
                        CommandInfo = response.commandInfo
                        StartPlaying = System.currentTimeMillis()
                        isAvailable = true
                        IsGuest = false
                        callback.onResponse(true)
                        CoreListeners.SuccessfullyLogined.invokeListeners(null)
                    }

                    override fun onFailure(error: GameServiceException) {
                        callback.onFailure(error)
                    }

                })
            }

            override fun onFailure(error: GameServiceException) {
                callback.onFailure(error)
            }

        })
    }


    /**
     * Normal SignUp To Game Service
     * It May Throw Exception
     * @return return UserToken if signUp Successfully
     */
    @Throws(GameServiceException::class)
    fun signUp(@NotNull nickName: String, @NotNull email: String, @NotNull password: String, callback: GameServiceCallback<String>) {
        if (!NetworkUtil.isConnected()) throw GameServiceException("Network Unreachable")
        if (Configuration == null) throw GameServiceException("You Must Configuration First")
        if (nickName.isEmpty()) throw GameServiceException("NickName Cant Be Empty")
        if (email.isEmpty()) throw GameServiceException("Email Cant Be Empty")
        if (password.isEmpty()) throw GameServiceException("PassWord Cant Be Empty")
        if (isAuthenticated()) logout()

        ApiRequest.signUp(nickName, email, password, object : GameServiceCallback<Login> {
            override fun onResponse(response: Login) {
                UserToken = response.token
                ApiRequest.authorize(object : GameServiceCallback<Login> {
                    override fun onResponse(response: Login) {
                        PlayToken = response.token
                        CurrentGame = response.game
                        CommandInfo = response.commandInfo
                        StartPlaying = System.currentTimeMillis()
                        isAvailable = true
                        IsGuest = false
                        GSLive.init()
                        callback.onResponse(UserToken.toString())
                    }

                    override fun onFailure(error: GameServiceException) {
                        callback.onFailure(error)
                    }

                })
            }

            override fun onFailure(error: GameServiceException) {
                callback.onFailure(error)
            }

        })

    }


    /**
     * Check if Current User Authenticated
     * @return True if Current User Authenticated Before
     */
    fun isAuthenticated(): Boolean {
        return isAvailable
    }

    /**
     * Get The Current gameservice Version
     * @return The Current gameservice Version
     */
    fun version(): String {
        return "5.4.1"
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
        GSLive.dispose()
    }
}