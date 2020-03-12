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
import ir.FiroozehCorp.GameService.Core.GameService
import ir.FiroozehCorp.GameService.Core.GameService.Configuration
import ir.FiroozehCorp.GameService.Models.BasicApi.Error
import ir.FiroozehCorp.GameService.Models.BasicApi.Login
import ir.FiroozehCorp.GameService.Models.Consts.Api
import ir.FiroozehCorp.GameService.Models.GameServiceException
import ir.FiroozehCorp.GameService.Models.Internal.AssetsInfo

/**
 * @author Alireza Ghodrati
 */
internal object ApiRequest {

    private val gson: Gson = Gson()


    internal fun getDataPackInfo(gameId: String, Tag: String): AssetsInfo {
        GSWebRequest.get(Api.BaseUrl1 + "/game/" + gameId + "/datapack/?tag=" + Tag, createPlayTokenHeader())
                .execute()
                .apply {
                    if (isSuccessful)
                        return gson.fromJson(this.body?.string(), AssetsInfo::class.java)
                    throw GameServiceException(gson.fromJson(this.body?.string(), Error::class.java).message)
                }
    }


    internal fun login(email: String, password: String): Login {
        GSWebRequest.post(Api.LoginUser, gson.toJson(createLoginDictionary(email, password, isGuest = false)))
                .execute()
                .apply {
                    if (isSuccessful)
                        return gson.fromJson(this.body?.string(), Login::class.java)
                    throw GameServiceException(gson.fromJson(this.body?.string(), Error::class.java).message)
                }
    }


    internal fun loginAsGuest(): Login {
        GSWebRequest.post(Api.LoginUser, gson.toJson(createLoginDictionary(null, null, isGuest = true)))
                .execute()
                .apply {
                    if (isSuccessful)
                        return gson.fromJson(this.body?.string(), Login::class.java)
                    throw GameServiceException(gson.fromJson(this.body?.string(), Error::class.java).message)
                }
    }


    internal fun loginWithGoogle(idToken: String): Login {
        GSWebRequest.post(Api.LoginUser, gson.toJson(createGoogleLoginHeader(idToken)))
                .execute()
                .apply {
                    if (isSuccessful)
                        return gson.fromJson(this.body?.string(), Login::class.java)
                    throw GameServiceException(gson.fromJson(this.body?.string(), Error::class.java).message)
                }
    }


    internal fun authorize(): Login {
        GSWebRequest.post(Api.LoginUser, gson.toJson(createAuthorizationDictionary()))
                .execute()
                .apply {
                    if (isSuccessful)
                        return gson.fromJson(this.body?.string(), Login::class.java)
                    throw GameServiceException(gson.fromJson(this.body?.string(), Error::class.java).message)
                }
    }

    internal fun signUp(nickName: String, email: String, password: String): Login {
        GSWebRequest.post(Api.LoginUser, gson.toJson(createLoginDictionary(email, password, nickName, false)))
                .execute()
                .apply {
                    if (isSuccessful)
                        return gson.fromJson(this.body?.string(), Login::class.java)
                    throw GameServiceException(gson.fromJson(this.body?.string(), Error::class.java).message)
                }
    }


    private fun createLoginDictionary(email: String?, password: String?, nickname: String? = null, isGuest: Boolean)
            : MutableMap<String, String> {
        val map = mutableMapOf<String, String>()
        when {
            isGuest -> "mode" to "guest"
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
}