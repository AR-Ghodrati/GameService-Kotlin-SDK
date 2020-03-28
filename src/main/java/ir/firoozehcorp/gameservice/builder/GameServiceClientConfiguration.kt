/*
 * <copyright file="GameServiceClientConfiguration.kt" company="Firoozeh Technology LTD">
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
package ir.firoozehcorp.gameservice.builder

import ir.firoozehcorp.gameservice.models.internal.SystemInfo

/**
 * Represents System Info In Game Service
 * @author Alireza Ghodrati
 */
class GameServiceClientConfiguration private constructor(val clientId: String?, val clientSecret: String?, val systemInfo: SystemInfo?) {

    class Builder {
        private var clientId: String? = null
        private var clientSecret: String? = null
        private var systemInfo: SystemInfo? = null


        fun setClientId(ClientId: String?): Builder {
            this.clientId = ClientId
            return this
        }

        fun setClientSecret(ClientSecret: String?): Builder {
            this.clientSecret = ClientSecret
            return this
        }

        fun setSystemInfo(SystemInfo: SystemInfo?): Builder {
            this.systemInfo = SystemInfo
            return this
        }

        fun build(): GameServiceClientConfiguration {
            return GameServiceClientConfiguration(clientId, clientSecret, systemInfo)
        }
    }

}