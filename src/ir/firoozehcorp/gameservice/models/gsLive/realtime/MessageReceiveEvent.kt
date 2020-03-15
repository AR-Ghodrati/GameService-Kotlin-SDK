/*
 * <copyright file="MessageReceiveEvent.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.models.gsLive.realtime

import java.io.Serializable

/**
 * @author Alireza Ghodrati
 */
class MessageReceiveEvent : Serializable {


    /**
     * Gets the Received Message
     * @return the Received Message
     */
    var newMessage: Message? = null


    /**
     * Gets the Type of Received Message
     * @return Type of Received Message
     */
    var messageType: MessageType? = null


    /**
     * Gets the Type of Protocol Send Message
     * if is Reliable , the Packet Loss is Minimized
     * @return the Type of Protocol Send Message
     */
    var sendType: GProtocolSendType? = null


}