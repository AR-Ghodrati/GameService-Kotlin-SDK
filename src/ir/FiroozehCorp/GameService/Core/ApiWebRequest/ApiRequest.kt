/*
 * <copyright file="ApiRequest.kt" company="Firoozeh Technology LTD">
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
package ir.FiroozehCorp.GameService.Core.ApiWebRequest

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ir.FiroozehCorp.GameService.Core.GameService
import ir.FiroozehCorp.GameService.Core.GameService.Configuration
import ir.FiroozehCorp.GameService.Models.BasicApi.*
import ir.FiroozehCorp.GameService.Models.BasicApi.Bucket.BucketOption
import ir.FiroozehCorp.GameService.Models.BasicApi.TResponse.*
import ir.FiroozehCorp.GameService.Models.Consts.Api
import ir.FiroozehCorp.GameService.Models.GameServiceException
import ir.FiroozehCorp.GameService.Models.Internal.AssetsInfo
import ir.FiroozehCorp.GameService.Models.Internal.GSTime
import ir.FiroozehCorp.GameService.Models.Internal.Interfaces.GameServiceCallback
import ir.FiroozehCorp.GameService.Models.Internal.ListOfJson
import ir.FiroozehCorp.GameService.Utils.URlUtil
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.*


/**
 * @author Alireza Ghodrati
 */
internal object ApiRequest {

    private val gson: Gson = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create()


    internal fun getDataPackInfo(gameId: String, Tag: String, callback: GameServiceCallback<AssetsInfo>) {
        GSWebRequest
                .get(Api.BaseUrl1 + "/game/" + gameId + "/datapack/?tag=" + Tag, createPlayTokenHeader())
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.onFailure(GameServiceException(e.message))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) callback.onResponse(gson.fromJson(response.body?.string(), AssetsInfo::class.java))
                        else callback.onFailure(GameServiceException(gson.fromJson(response.body?.string(), Error::class.java).message))
                    }

                })
    }


    internal fun login(email: String, password: String, callback: GameServiceCallback<Login>) {
        GSWebRequest
                .post(Api.LoginUser, gson.toJson(createLoginDictionary(email, password, isGuest = false)))
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.onFailure(GameServiceException(e.message))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) callback.onResponse(gson.fromJson(response.body?.string(), Login::class.java))
                        else callback.onFailure(GameServiceException(gson.fromJson(response.body?.string(), Error::class.java).message))
                    }

                })
    }


    internal fun loginAsGuest(callback: GameServiceCallback<Login>) {
        GSWebRequest
                .post(Api.LoginUser, gson.toJson(createLoginDictionary(null, null, isGuest = true)))
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.onFailure(GameServiceException(e.message))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) callback.onResponse(gson.fromJson(response.body?.string(), Login::class.java))
                        else callback.onFailure(GameServiceException(gson.fromJson(response.body?.string(), Error::class.java).message))
                    }

                })
    }


    internal fun loginWithGoogle(idToken: String, callback: GameServiceCallback<Login>) {
        GSWebRequest
                .post(Api.LoginWithGoogle, gson.toJson(createGoogleLoginHeader(idToken)))
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.onFailure(GameServiceException(e.message))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) callback.onResponse(gson.fromJson(response.body?.string(), Login::class.java))
                        else callback.onFailure(GameServiceException(gson.fromJson(response.body?.string(), Error::class.java).message))
                    }

                })
    }


    internal fun authorize(callback: GameServiceCallback<Login>) {
        GSWebRequest
                .post(Api.Start, gson.toJson(createAuthorizationDictionary()))
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.onFailure(GameServiceException(e.message))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) callback.onResponse(gson.fromJson(response.body?.string(), Login::class.java))
                        else callback.onFailure(GameServiceException(gson.fromJson(response.body?.string(), Error::class.java).message))
                    }

                })
    }


    internal fun signUp(nickName: String, email: String, password: String, callback: GameServiceCallback<Login>) {
        GSWebRequest
                .post(Api.LoginUser, gson.toJson(createLoginDictionary(email, password, nickName, false)))
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.onFailure(GameServiceException(e.message))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) callback.onResponse(gson.fromJson(response.body?.string(), Login::class.java))
                        else callback.onFailure(GameServiceException(gson.fromJson(response.body?.string(), Error::class.java).message))
                    }

                })
    }


    internal fun getLeaderBoards(callback: GameServiceCallback<MutableList<Leaderboard>>) {
        GSWebRequest
                .get(Api.Leaderboard, createPlayTokenHeader())
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.onFailure(GameServiceException(e.message))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) callback.onResponse(gson.fromJson(response.body?.string(), TLeaderBoard::class.java).leaderboards!!)
                        else callback.onFailure(GameServiceException(gson.fromJson(response.body?.string(), Error::class.java).message))
                    }

                })
    }


    internal fun getAchievements(callback: GameServiceCallback<MutableList<Achievement>>) {
        GSWebRequest
                .get(Api.Achievements, createPlayTokenHeader())
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.onFailure(GameServiceException(e.message))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) callback.onResponse(gson.fromJson(response.body?.string(), TAchievement::class.java).achievements!!)
                        else callback.onFailure(GameServiceException(gson.fromJson(response.body?.string(), Error::class.java).message))
                    }

                })
    }


    internal fun <T> getSaveGame(clazz: Class<T>, callback: GameServiceCallback<T>) {
        GSWebRequest
                .get(Api.SaveGame, createPlayTokenHeader())
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.onFailure(GameServiceException(e.message))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) callback.onResponse(gson.fromJson(response.body?.string(), clazz))
                        else callback.onFailure(GameServiceException(gson.fromJson(response.body?.string(), Error::class.java).message))
                    }

                })
    }


    internal fun getCurrentPlayer(callback: GameServiceCallback<User>) {
        GSWebRequest
                .get(Api.UserData, createPlayTokenHeader())
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.onFailure(GameServiceException(e.message))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) callback.onResponse(gson.fromJson(response.body?.string(), TUser::class.java).user!!)
                        else callback.onFailure(GameServiceException(gson.fromJson(response.body?.string(), Error::class.java).message))
                    }

                })
    }


    internal fun editCurrentPlayer(editUserProfile: EditUserProfile, callback: GameServiceCallback<User>) {

        editUserProfile.logoBuffer?.apply {
            GSWebRequest
                    .multiPart(Api.UserProfileLogo, this, createUserTokenHeader())
                    .enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            GSWebRequest
                                    .put(Api.UserProfile, gson.toJson(ir.FiroozehCorp.GameService.Models.Internal.EditUserProfile().apply {
                                        nickName = editUserProfile.nickName
                                        allowAutoAddToGame = editUserProfile.allowAutoAddToGame
                                        showGroupActivity = editUserProfile.showGroupActivity
                                        showPublicActivity = editUserProfile.showPublicActivity
                                    }), createUserTokenHeader())
                                    .enqueue(object : Callback {
                                        override fun onFailure(call: Call, e: IOException) {
                                            callback.onFailure(GameServiceException(e.message))
                                        }

                                        override fun onResponse(call: Call, response: Response) {
                                            if (response.isSuccessful) callback.onResponse(gson.fromJson(response.body?.string(), TEditUser::class.java).user)
                                            else callback.onFailure(GameServiceException(gson.fromJson(response.body?.string(), Error::class.java).message))
                                        }

                                    })
                        }

                        override fun onResponse(call: Call, response: Response) {
                            GSWebRequest
                                    .put(Api.UserProfile, gson.toJson(ir.FiroozehCorp.GameService.Models.Internal.EditUserProfile().apply {
                                        nickName = editUserProfile.nickName
                                        allowAutoAddToGame = editUserProfile.allowAutoAddToGame
                                        showGroupActivity = editUserProfile.showGroupActivity
                                        showPublicActivity = editUserProfile.showPublicActivity
                                    }), createUserTokenHeader())
                                    .enqueue(object : Callback {
                                        override fun onFailure(call: Call, e: IOException) {
                                            callback.onFailure(GameServiceException(e.message))
                                        }

                                        override fun onResponse(call: Call, response: Response) {
                                            if (response.isSuccessful) callback.onResponse(gson.fromJson(response.body?.string(), TEditUser::class.java).user)
                                            else callback.onFailure(GameServiceException(gson.fromJson(response.body?.string(), Error::class.java).message))
                                        }

                                    })
                        }
                    })
        }
    }


    internal fun <T> getBucketItems(bucketId: String, options: Array<BucketOption>? = null, bucketType: Class<T>, callback: GameServiceCallback<MutableList<T>>) {
        GSWebRequest
                .get(URlUtil.parseBucketUrl(bucketId, options), createPlayTokenHeader())
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.onFailure(GameServiceException(e.message))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) callback.onResponse(gson.fromJson(response.body?.string(), ListOfJson(bucketType)))
                        else callback.onFailure(GameServiceException(gson.fromJson(response.body?.string(), Error::class.java).message))
                    }

                })
    }


    internal fun <T> getBucketItem(bucketId: String, itemId: String, bucketType: Class<T>, callback: GameServiceCallback<T>) {
        GSWebRequest
                .get(Api.Bucket + bucketId + '/' + itemId, createPlayTokenHeader())
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.onFailure(GameServiceException(e.message))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) {
                            val res = response.body?.string()
                            val map: Map<String, Any> = HashMap()
                            val mapRes = gson.fromJson(res, map::class.java)
                            callback.onResponse(gson.fromJson(mapRes["data"].toString(), bucketType))
                        } else callback.onFailure(GameServiceException(gson.fromJson(response.body?.string(), Error::class.java).message))
                    }

                })
    }


    internal fun <T> addBucketItem(bucketId: String, newBucket: T, bucketType: Class<T>, callback: GameServiceCallback<T>) {
        GSWebRequest
                .post(Api.Bucket + bucketId, gson.toJson(newBucket), createPlayTokenHeader())
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.onFailure(GameServiceException(e.message))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) {
                            val res = response.body?.string()
                            val map: Map<String, Any> = HashMap()
                            val mapRes = gson.fromJson(res, map::class.java)
                            callback.onResponse(gson.fromJson(mapRes["data"].toString(), bucketType))
                        } else callback.onFailure(GameServiceException(gson.fromJson(response.body?.string(), Error::class.java).message))
                    }

                })
    }


    internal fun <T> updateBucketItem(bucketId: String, itemId: String, editedBucket: T, bucketType: Class<T>, callback: GameServiceCallback<T>) {
        GSWebRequest
                .put(Api.Bucket + bucketId + '/' + itemId, gson.toJson(editedBucket), createPlayTokenHeader())
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.onFailure(GameServiceException(e.message))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) {
                            val res = response.body?.string()
                            val map: Map<String, Any> = HashMap()
                            val mapRes = gson.fromJson(res, map::class.java)
                            callback.onResponse(gson.fromJson(mapRes["data"].toString().also { println(it) }, bucketType))
                        } else callback.onFailure(GameServiceException(gson.fromJson(response.body?.string(), Error::class.java).message))
                    }

                })
    }


    internal fun getLeaderBoardDetails(leaderBoardKey: String, scoreLimit: Int = 10, callback: GameServiceCallback<LeaderBoardDetails>) {
        GSWebRequest
                .get(Api.Leaderboard + leaderBoardKey + "?limit=" + scoreLimit, createPlayTokenHeader())
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.onFailure(GameServiceException(e.message))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) callback.onResponse(gson.fromJson(response.body?.string(), LeaderBoardDetails::class.java))
                        else callback.onFailure(GameServiceException(gson.fromJson(response.body?.string(), Error::class.java).message))
                    }

                })
    }


    internal fun saveGame(saveName: String, saveObj: Any, callback: GameServiceCallback<SaveDetails>) {
        GSWebRequest
                .post(Api.SaveGame, gson.toJson(createSaveGameMap(saveName, saveObj)), createPlayTokenHeader())
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.onFailure(GameServiceException(e.message))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) callback.onResponse(gson.fromJson(response.body?.string(), TSave::class.java).details!!)
                        else callback.onFailure(GameServiceException(gson.fromJson(response.body?.string(), Error::class.java).message))
                    }

                })
    }


    internal fun submitScore(leaderBoardKey: String, scoreValue: Int, callback: GameServiceCallback<SubmitScoreResponse>) {
        GSWebRequest
                .post(Api.Leaderboard + leaderBoardKey, gson.toJson(createSubmitScoreMap(scoreValue)), createPlayTokenHeader())
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.onFailure(GameServiceException(e.message))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) callback.onResponse(gson.fromJson(response.body?.string(), TSubmitScore::class.java).submitScoreResponse)
                        else callback.onFailure(GameServiceException(gson.fromJson(response.body?.string(), Error::class.java).message))
                    }

                })
    }


    internal fun unlockAchievement(achievementKey: String, callback: GameServiceCallback<Achievement>) {
        GSWebRequest
                .post(Api.Achievements + achievementKey, headers = createPlayTokenHeader())
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.onFailure(GameServiceException(e.message))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) callback.onResponse(gson.fromJson(response.body?.string(), TUnlockAchievement::class.java).achievement)
                        else callback.onFailure(GameServiceException(gson.fromJson(response.body?.string(), Error::class.java).message))
                    }

                })
    }


    internal fun removeLastSave(callback: GameServiceCallback<Boolean>) {
        GSWebRequest
                .delete(Api.SaveGame, createPlayTokenHeader())
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.onFailure(GameServiceException(e.message))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) callback.onResponse(gson.fromJson(response.body?.string(), TSave::class.java).status)
                        else callback.onFailure(GameServiceException(gson.fromJson(response.body?.string(), Error::class.java).message))
                    }

                })
    }


    internal fun deleteBucketItems(bucketId: String, callback: GameServiceCallback<Boolean>) {
        GSWebRequest
                .delete(Api.Bucket + bucketId, createPlayTokenHeader())
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.onFailure(GameServiceException(e.message))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) callback.onResponse(gson.fromJson(response.body?.string(), Error::class.java).status)
                        else callback.onFailure(GameServiceException(gson.fromJson(response.body?.string(), Error::class.java).message))
                    }

                })
    }


    internal fun deleteBucketItem(bucketId: String, itemId: String, callback: GameServiceCallback<Boolean>) {
        GSWebRequest
                .delete(Api.Bucket + bucketId + '/' + itemId, createPlayTokenHeader())
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.onFailure(GameServiceException(e.message))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) callback.onResponse(gson.fromJson(response.body?.string(), Error::class.java).status)
                        else callback.onFailure(GameServiceException(gson.fromJson(response.body?.string(), Error::class.java).message))
                    }

                })
    }


    internal fun getCurrentServerTime(callback: GameServiceCallback<GSTime>) {
        GSWebRequest
                .get(Api.CurrentTime)
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.onFailure(GameServiceException(e.message))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) callback.onResponse(GSTime().apply {
                            serverTime = convertEpochToOffsetDateTime(response.body?.string().toString().toLong())
                            deviceTime = OffsetDateTime.now()
                        })
                        else callback.onFailure(GameServiceException(gson.fromJson(response.body?.string(), Error::class.java).message))
                    }

                })
    }


    private fun createSubmitScoreMap(scoreValue: Int): MutableMap<String, Int> {
        return mutableMapOf(
                "value" to scoreValue
        )
    }

    private fun createSaveGameMap(saveName: String, saveObj: Any): MutableMap<String, String> {
        return mutableMapOf(
                "name" to saveName,
                "data" to gson.toJson(saveObj)
        )
    }

    private fun createLoginDictionary(email: String?, password: String?, nickname: String? = null, isGuest: Boolean)
            : MutableMap<String, String> {
        val map = mutableMapOf<String, String>()
        when {
            isGuest -> map["mode"] = "guest"
            nickname == null -> {
                map["mode"] = "login"
                map["email"] = email.toString()
                map["password"] = password.toString()
            }
            else -> {
                map["name"] = nickname
                map["email"] = email.toString()
                map["password"] = password.toString()
                map["mode"] = "register"
            }
        }
        map["device_id"] = Configuration?.systemInfo?.deviceUniqueId.toString()
        return map
    }

    private fun createGoogleLoginHeader(token: String): MutableMap<String, String> {
        return mutableMapOf(
                "token" to token,
                "device_id" to Configuration?.systemInfo?.deviceUniqueId.toString()
        )
    }

    private fun createAuthorizationDictionary(): MutableMap<String, String> {
        return mutableMapOf(
                "token" to GameService.UserToken.toString(),
                "game" to Configuration?.clientId.toString(),
                "secret" to Configuration?.clientSecret.toString(),
                "system_info" to gson.toJson(Configuration?.systemInfo)
        )
    }


    private fun createPlayTokenHeader(): MutableMap<String, String> {
        return mutableMapOf(
                "x-access-token" to GameService.PlayToken.toString()
        )
    }

    private fun createUserTokenHeader(): MutableMap<String, String> {
        return mutableMapOf(
                "x-access-token" to GameService.UserToken.toString()
        )
    }

    private fun convertEpochToOffsetDateTime(epochValue: Long): OffsetDateTime {
        return OffsetDateTime.of(LocalDateTime.ofEpochSecond(epochValue, 0, OffsetDateTime.now().offset), OffsetDateTime.now().offset)
    }
}