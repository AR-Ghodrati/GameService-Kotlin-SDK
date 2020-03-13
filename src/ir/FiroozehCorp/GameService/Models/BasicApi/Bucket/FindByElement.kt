/*
 * <copyright file="FindByElement.kt" company="Firoozeh Technology LTD">
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

import ir.FiroozehCorp.GameService.Models.GameServiceException

/**
 * Represents ElementOptionData Model In Game Service Basic API
 * @author Alireza Ghodrati
 */
class FindByElement<T>(private val name: String, val value: T) : BucketOption {

    init {
        if (name.isEmpty()) throw GameServiceException("Name Cant Be Empty")
        if (value == null) throw GameServiceException("Value Cant Be Null")
    }


    override fun getParsedData(): String {
        return "&conditionProperty=$name&conditionValue=$value"
    }
}