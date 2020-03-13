/*
 * <copyright file="URlUtil.kt" company="Firoozeh Technology LTD">
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

package ir.FiroozehCorp.GameService.Utils

import ir.FiroozehCorp.GameService.Models.BasicApi.Bucket.BucketOption
import ir.FiroozehCorp.GameService.Models.Consts.Api

/**
 * @author Alireza Ghodrati
 */
object URlUtil {

    fun parseBucketUrl(bucketId: String, options: Array<BucketOption>?): String {
        var first = true
        var url = Api.Bucket + bucketId
        if (options == null) return url

        url += "?"

        options.forEach {
            if (first) {
                // To Remove first &
                url += it.getParsedData().removePrefix("&")
                first = false
            } else url += it.getParsedData()
        }

        return url
    }
}