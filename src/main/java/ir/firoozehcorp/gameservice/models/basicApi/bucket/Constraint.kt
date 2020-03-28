/*
 * <copyright file="Constraint.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.models.basicApi.bucket

import ir.firoozehcorp.gameservice.models.GameServiceException

/**
 * Represents ConstraintOptionData Model In Game Service Basic API
 * @author Alireza Ghodrati
 */
class Constraint(private val skip: Int, private val limit: Int) : BucketOption {

    init {
        if (skip < 0) throw GameServiceException("Invalid Skip Value")
        if (limit <= 0 || limit > 200) throw GameServiceException("Invalid Limit Value")
    }


    override fun getParsedData(): String {
        return "&skip=$skip&limit=$limit"
    }

}