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
import ir.FiroozehCorp.GameService.Core.ApiWebRequest.DownloadRequest
import ir.FiroozehCorp.GameService.Models.BasicApi.*
import ir.FiroozehCorp.GameService.Models.BasicApi.Bucket.BucketOption
import ir.FiroozehCorp.GameService.Models.GameServiceException
import ir.FiroozehCorp.GameService.Models.Internal.GSTime
import ir.FiroozehCorp.GameService.Models.Internal.Game
import ir.FiroozehCorp.GameService.Models.Internal.Interfaces.GameServiceCallback
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
     * @param callback returns GetLeaderBoards List
     */
    @Throws(GameServiceException::class)
    fun getLeaderBoards(callback: GameServiceCallback<MutableList<Leaderboard>>) {
        if (!isAuthenticated()) throw GameServiceException("GameService Not Available")
        ApiRequest.getLeaderBoards(callback)
    }


    /**
     * With this command you can get list of all your game achievements
     * That you have registered in the Developer panel
     * @param callback returns GetAchievements List
     * */
    @Throws(GameServiceException::class)
    fun getAchievements(callback: GameServiceCallback<MutableList<Achievement>>) {
        if (!isAuthenticated()) throw GameServiceException("GameService Not Available")
        ApiRequest.getAchievements(callback)
    }


    /**
     * With this command you can save your Current Status in Game
     * @param saveName (Not NULL)Specifies the saveGameName
     * @param saveObj the Object that you Want To Save it
     * @param callback returns SaveDetails
     */
    @Throws(GameServiceException::class)
    fun saveGame(saveName: String, saveObj: Any, callback: GameServiceCallback<SaveDetails>) {
        if (!isAuthenticated()) throw GameServiceException("GameService Not Available")
        ApiRequest.saveGame(saveName, saveObj, callback)
    }


    /**
     * This command allows you to Submit Player Score with the ID of the leaderBoard
     * you have Registered in the Developer panel
     * @param leaderBoardKey (Not NULL)Specifies the leaderBoardId
     * @param scoreValue scoreValue(The value must not exceed the maximum value Registered in the Developer Panel)
     * @param callback returns SubmitScoreResponse
     */
    @Throws(GameServiceException::class)
    fun submitScore(leaderBoardKey: String, scoreValue: Int, callback: GameServiceCallback<SubmitScoreResponse>) {
        if (!isAuthenticated()) throw GameServiceException("GameService Not Available")
        if (leaderBoardKey.isEmpty()) throw GameServiceException("leaderBoardKey Cant Be EmptyOrNull")
        if (scoreValue <= 0) throw GameServiceException("Invalid ScoreValue")

        ApiRequest.submitScore(leaderBoardKey, scoreValue, callback)
    }


    /**
     * With this command you can Unlock achievement with the achievement ID
     * you have Registered in the Developer panel
     * @param achievementKey (Not NULL)The ID of Achievement you Want To Unlock it
     * @param callback returns unlocked Achievement
     */
    @Throws(GameServiceException::class)
    fun unlockAchievement(achievementKey: String, callback: GameServiceCallback<Achievement>) {
        if (!isAuthenticated()) throw GameServiceException("GameService Not Available")
        if (achievementKey.isEmpty()) throw GameServiceException("achievementKey Cant Be EmptyOrNull")

        ApiRequest.unlockAchievement(achievementKey, callback)
    }


    /**
     * his command will get you the last save you saved
     * @param callback Player Last Save
     */
    @Throws(GameServiceException::class)
    fun <T> getSaveGame(saveType: Class<T>, callback: GameServiceCallback<T>) {
        if (!isAuthenticated()) throw GameServiceException("GameService Not Available")
        ApiRequest.getSaveGame(saveType, callback)
    }


    /**
     * With this command you can get a LeaderBoardDetails with the ID of the LeaderBoard list
     * you have Registered in the Developer panel
     * @param leaderBoardKey (Not NULL)The ID of leaderBoard you Want To get Detail
     * @param scoreLimit (Min = 10,Max = 50) The Score List Limits
     * @param callback returns LeaderBoardDetails
     */
    @Throws(GameServiceException::class)
    fun getLeaderBoardDetails(leaderBoardKey: String, scoreLimit: Int = 10, callback: GameServiceCallback<LeaderBoardDetails>) {
        if (!isAuthenticated()) throw GameServiceException("GameService Not Available")
        if (leaderBoardKey.isEmpty()) throw GameServiceException("leaderBoardKey Cant Be EmptyOrNull")
        if (scoreLimit < 10 || scoreLimit > 50) throw GameServiceException("Invalid scoreLimit")

        ApiRequest.getLeaderBoardDetails(leaderBoardKey, scoreLimit, callback)
    }


    /**
     * With this command you can get information about the current player is playing
     * @param callback returns CurrentPlayer Data
     */
    @Throws(GameServiceException::class)
    fun getCurrentPlayer(callback: GameServiceCallback<User>) {
        if (!isAuthenticated()) throw GameServiceException("GameService Not Available")
        ApiRequest.getCurrentPlayer(callback)
    }


    /**
     * With this command you can Edit information about the current player is playing
     * @param callback returns CurrentPlayer Data
     */
    @Throws(GameServiceException::class)
    fun editCurrentPlayerProfile(editUserProfile: EditUserProfile, callback: GameServiceCallback<User>) {
        if (!isAuthenticated()) throw GameServiceException("GameService Not Available")
        ApiRequest.editCurrentPlayer(editUserProfile, callback)
    }


    /**
     * This command can remove the last current user saved
     * @param callback returns true if Remove Successfully
     */
    @Throws(GameServiceException::class)
    fun removeLastSave(callback: GameServiceCallback<Boolean>) {
        if (!isAuthenticated()) throw GameServiceException("GameService Not Available")
        ApiRequest.removeLastSave(callback)
    }


    /**
     * This command will return all information about the bucket with a specific ID
     * @param bucketId (Not NULL)The ID of Bucket you Want To get Detail
     * @param options (Optional)The Bucket Options
     * @param callback return List of all Bucket Items
     */
    @Throws(GameServiceException::class)
    fun <T> getBucketItems(bucketId: String, bucketType: Class<T>, options: Array<BucketOption>? = null, callback: GameServiceCallback<MutableList<T>>) {
        if (!isAuthenticated()) throw GameServiceException("GameService Not Available")
        if (bucketId.isEmpty()) throw GameServiceException("BucketId Cant Be EmptyOrNull")
        ApiRequest.getBucketItems(bucketId, options, bucketType, callback)
    }


    /**
     * This command returns one of the Specific bucket information with a specific ID
     * @param bucketId (Not NULL)The ID of Bucket you Want To get Detail
     * @param itemId (Not NULL)The ID of BucketItem you Want To get Detail
     * @param callback return a Bucket Item
     */
    @Throws(GameServiceException::class)
    fun <T> getBucketItem(bucketId: String, itemId: String, bucketType: Class<T>, callback: GameServiceCallback<T>) {
        if (!isAuthenticated()) throw GameServiceException("GameService Not Available")
        if (bucketId.isEmpty()) throw GameServiceException("BucketId Cant Be EmptyOrNull")
        if (itemId.isEmpty()) throw GameServiceException("BucketItemId Cant Be EmptyOrNull")

        ApiRequest.getBucketItem(bucketId, itemId, bucketType, callback)
    }


    /**
     * This command will edit one of the bucket information with a specific ID
     * @param bucketId (Not NULL)The ID of Bucket you Want To get Detail
     * @param itemId (Not NULL)The ID of BucketItem you Want To get Detail
     * @param editedBucket (Not NULL)The Object of BucketItem you Want To Edit Detail
     * @param callback return Edited Bucket Item
     */
    @Throws(GameServiceException::class)
    fun <T> editBucketItem(bucketId: String, itemId: String, editedBucket: T, bucketType: Class<T>, callback: GameServiceCallback<T>) {
        if (!isAuthenticated()) throw GameServiceException("GameService Not Available")
        if (bucketId.isEmpty()) throw GameServiceException("BucketId Cant Be EmptyOrNull")
        if (itemId.isEmpty()) throw GameServiceException("BucketItemId Cant Be EmptyOrNull")

        ApiRequest.updateBucketItem(bucketId, itemId, editedBucket, bucketType, callback)
    }


    /**
     * This command will Add new bucket information with a specific ID
     * @param bucketId (Not NULL)The ID of Bucket you Want To get Detail
     * @param newBucket Not NULL)The Object of BucketItem you Want To Add
     * @param callback  return Added Bucket Item
     */
    @Throws(GameServiceException::class)
    fun <T> addBucketItem(bucketId: String, newBucket: T, bucketType: Class<T>, callback: GameServiceCallback<T>) {
        if (!isAuthenticated()) throw GameServiceException("GameService Not Available")
        if (bucketId.isEmpty()) throw GameServiceException("BucketId Cant Be EmptyOrNull")

        ApiRequest.addBucketItem(bucketId, newBucket, bucketType, callback)
    }


    /**
     * This command will delete All of the bucket Items information with a specific ID
     * @param bucketId (Not NULL)The ID of Bucket you Want To Delete All Items
     * @param callback Return true if Remove Successfully
     */
    @Throws(GameServiceException::class)
    fun deleteBucketItems(bucketId: String, callback: GameServiceCallback<Boolean>) {
        if (!isAuthenticated()) throw GameServiceException("GameService Not Available")
        if (bucketId.isEmpty()) throw GameServiceException("BucketId Cant Be EmptyOrNull")

        ApiRequest.deleteBucketItems(bucketId, callback)
    }


    /**
     * This command will delete one of the bucket information with a specific ID
     * @param bucketId (Not NULL)The ID of Bucket you Want To Delete one of Items
     * @param itemId (Not NULL)The ID of BucketItem you Want To Delete it
     * @param callback Return true if Remove Successfully
     */
    @Throws(GameServiceException::class)
    fun deleteBucketItem(bucketId: String, itemId: String, callback: GameServiceCallback<Boolean>) {
        if (!isAuthenticated()) throw GameServiceException("GameService Not Available")
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
     * Download Asset With Tag
     * @param assetTag (Not NULL)Specifies the Asset tag that Set in Developers Panel
     * @param dirPath (Not NULL)Specifies the Download File Directory Path
     * @param callback return True if Download Done
     */
    @Throws(GameServiceException::class)
    fun downloadAsset(assetTag: String, dirPath: String, callback: GameServiceCallback<Boolean>) {
        if (Configuration == null) throw GameServiceException("You Must Configuration First")
        if (assetTag.isEmpty()) throw GameServiceException("assetTag Cant Be EmptyOrNull")
        if (dirPath.isEmpty()) throw GameServiceException("DownloadDirPath Cant Be EmptyOrNull")

        DownloadRequest.downloadAsset(dirPath, assetTag, Configuration?.clientId.toString(), callback)
    }


    /**
     * Normal Login (InFirstOnly) To Game Service
     * It May Throw Exception
     * @param callback returns UserToken if Login Successfully
     */
    @Throws(GameServiceException::class)
    fun login(email: String, password: String, callback: GameServiceCallback<String>) {
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
                        StartPlaying = System.currentTimeMillis()
                        isAvailable = true
                        IsGuest = false
                        //await Core.GSLive.GSLive.Init();
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
    fun login(userToken: String, callback: GameServiceCallback<Boolean>) {
        if (!NetworkUtil.isConnected()) throw GameServiceException("Network Unreachable")
        if (Configuration == null) throw GameServiceException("You Must Configuration First")
        if (userToken.isEmpty()) throw GameServiceException("userToken Cant Be Empty")
        if (isAuthenticated()) logout()

        UserToken = userToken
        ApiRequest.authorize(object : GameServiceCallback<Login> {
            override fun onResponse(response: Login) {
                PlayToken = response.token
                CurrentGame = response.game
                StartPlaying = System.currentTimeMillis()
                isAvailable = true
                IsGuest = false
                //await Core.GSLive.GSLive.Init();
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
     * @param idToken (Not NULL)Specifies the idToken From GoogleSignInUser Class
     * @param callback returns UserToken if Login Successfully
     */
    @Throws(GameServiceException::class)
    fun loginWithGoogle(idToken: String, callback: GameServiceCallback<String>) {
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
                        StartPlaying = System.currentTimeMillis()
                        isAvailable = true
                        IsGuest = false
                        //await Core.GSLive.GSLive.Init();
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
                        StartPlaying = System.currentTimeMillis()
                        isAvailable = true
                        IsGuest = false
                        callback.onResponse(true)
                        //CoreEventHandlers.SuccessfullyLogined?.Invoke(null,null);
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
    fun signUp(nickName: String, email: String, password: String, callback: GameServiceCallback<String>) {
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
                        StartPlaying = System.currentTimeMillis()
                        isAvailable = true
                        IsGuest = false
                        //await Core.GSLive.GSLive.Init();
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