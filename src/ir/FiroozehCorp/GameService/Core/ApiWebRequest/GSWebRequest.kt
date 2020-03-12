/*
 * <copyright file="GSWebRequest.kt" company="Firoozeh Technology LTD">
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

import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException


/**
 * @author Alireza Ghodrati
 */
object GSWebRequest {

    private var client = OkHttpClient()
    private val JSON = "application/json; charset=utf-8".toMediaType()

    @Throws(IOException::class)
    fun get(URL: String, headers: MutableMap<String, String>?): Call {
        val request = addHeaders(Request.Builder().url(URL), headers).build()
        return client.newCall(request)
    }

    @Throws(IOException::class)
    fun post(URL: String, body: String? = null, headers: MutableMap<String, String>? = null): Call {
        val request: Request = if (body != null)
            addHeaders(Request
                    .Builder()
                    .url(URL)
                    .post(body.toRequestBody(JSON))
                    , headers).build()
        else addHeaders(Request
                .Builder()
                .url(URL)
                , headers).build()

        return client.newCall(request)
    }


    @Throws(IOException::class)
    fun put(URL: String, body: String, headers: MutableMap<String, String>? = null): Call {
        val request = addHeaders(Request
                .Builder()
                .url(URL)
                .put(body.toRequestBody(JSON))
                , headers).build()
        return client.newCall(request)
    }

    @Throws(IOException::class)
    fun delete(URL: String, headers: MutableMap<String, String>? = null): Call {
        val request = addHeaders(Request
                .Builder()
                .url(URL)
                .delete()
                , headers).build()
        return client.newCall(request)
    }


    private fun addHeaders(builder: Request.Builder, headers: MutableMap<String, String>?)
            : Request.Builder {
        headers?.forEach { (name, value) -> builder.addHeader(name, value) }
        return builder
    }
}