/*
 * <copyright file="GameServiceResponse.kt" company="Firoozeh Technology LTD">
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

package ir.FiroozehCorp.GameService.Models.Internal

import com.google.gson.Gson
import ir.FiroozehCorp.GameService.Core.ApiWebRequest.Interfaces.GameServiceCall
import ir.FiroozehCorp.GameService.Core.ApiWebRequest.Interfaces.GameServiceCallback
import ir.FiroozehCorp.GameService.Models.BasicApi.Error
import ir.FiroozehCorp.GameService.Models.GameServiceException
import ir.FiroozehCorp.GameService.Utils.InlineUtil
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

/**
 * @author Alireza Ghodrati
 */
class GameServiceResponse<T> internal constructor(call: Call) : GameServiceCall<T> {

    private var `object`: Call = call

    @Throws(Exception::class)
    override fun execute(): T {
        val res = `object`.execute()
        print(res.message)
        if (res.isSuccessful)
            res.body?.string().also {
                return InlineUtil.fromJson<T>(it)
            }
        else res.body?.string().also {
            throw GameServiceException(
                    Gson().fromJson(it, Error::class.java).message
            )
        }
    }

    override fun enqueue(callback: GameServiceCallback<T>) {
        `object`.enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure(GameServiceException(e.message))
            }


            override fun onResponse(call: Call, response: Response) {
                callback.onResponse(InlineUtil.fromJson(response.body?.string()))
            }

        })
    }
}