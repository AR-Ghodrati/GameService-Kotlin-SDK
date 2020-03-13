/*
 * <copyright file="Ownership.kt" company="Firoozeh Technology LTD">
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

package ir.FiroozehCorp.GameService.Models.BasicApi.Bucket

import ir.FiroozehCorp.GameService.Models.Enums.BucketOwnershipTypes
import ir.FiroozehCorp.GameService.Models.GameServiceException


/**
 * Represents OwnershipOptionData Model In Game Service Basic API
 * @author Alireza Ghodrati
 */
class Ownership(private val ownershipTypes: BucketOwnershipTypes, private val ownerUserId: String?) : BucketOption {


    init {
        if (ownershipTypes == BucketOwnershipTypes.Another && ownerUserId.isNullOrEmpty())
            throw GameServiceException("OwnerUserId Cant Be EmptyOrNull When OwnershipType is Another")
    }


    override fun getParsedData(): String {
        val owner = if (ownershipTypes === BucketOwnershipTypes.Me) "me" else ownerUserId
        return "&owner=$owner"
    }
}