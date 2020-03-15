/*
 * <copyright file="RealTime.kt" company="Firoozeh Technology LTD">
 * Copyright (C) 2020. Firoozeh Technology LTD. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
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

package ir.firoozehcorp.gameservice.models.consts

/**
 * @author Alireza Ghodrati
 */
internal object RealTime {

    // Packet Actions
    const val ActionAuth = 1
    const val ActionData = 2
    const val ActionPublicMessage = 3
    const val ActionPrivateMessage = 4
    const val ActionJoin = 5
    const val ActionMembersDetail = 6
    const val ActionLeave = 7
    const val Error = 100

    // Limit Checker
    const val RealTimeLimit = 60 // 60 Request per sec
    const val RestLimit = 1000 //  RestLimit per sec in long
}