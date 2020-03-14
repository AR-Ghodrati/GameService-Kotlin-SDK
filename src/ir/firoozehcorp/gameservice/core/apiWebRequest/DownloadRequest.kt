/*
 * <copyright file="DownloadRequest.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.core.apiWebRequest

import ir.firoozehcorp.gameservice.models.GameServiceException
import ir.firoozehcorp.gameservice.models.internal.AssetsInfo
import ir.firoozehcorp.gameservice.models.internal.interfaces.GameServiceCallback
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okio.buffer
import okio.sink
import java.io.File
import java.io.IOException

/**
 * @author Alireza Ghodrati
 */
object DownloadRequest {

    fun downloadAsset(path: String, tag: String, clientId: String, callback: GameServiceCallback<Boolean>) {

        ApiRequest.getDataPackInfo(clientId, tag, object : GameServiceCallback<AssetsInfo> {

            override fun onResponse(response: AssetsInfo) {
                GSWebRequest.download(response.data?.link.toString())
                        .enqueue(object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                                callback.onFailure(GameServiceException(e.message))
                            }

                            override fun onResponse(call: Call, response: Response) {
                                response.body?.source()?.let {
                                    File("$path/$tag")
                                            .apply {
                                                if (!exists()) createNewFile()
                                            }
                                            .sink()
                                            .buffer()
                                            .apply {
                                                writeAll(it)
                                                close()
                                                callback.onResponse(true)
                                            }
                                }
                            }
                        })
            }

            override fun onFailure(error: GameServiceException) {
                callback.onFailure(error)
            }

        })

    }


}