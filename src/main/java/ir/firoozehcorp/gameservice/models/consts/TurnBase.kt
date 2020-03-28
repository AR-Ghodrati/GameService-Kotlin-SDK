/*
 * <copyright file="TurnBase.kt" company="Firoozeh Technology LTD">
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
internal object TurnBase {


    // Packet Actions
    const val ActionAuth = 1
    const val ActionPingPong = 2
    const val OnTakeTurn = 4
    const val OnChooseNext = 5
    const val OnLeave = 6
    const val OnFinish = 7
    const val OnComplete = 8
    const val GetUsers = 9
    const val OnJoin = 11
    const val OnCurrentTurnDetail = 12


    const val Errors = 100


    const val TurnBasedLimit = 10 // 10 Request per sec
    const val RestLimit = 1000 //  RestLimit per sec in long
}