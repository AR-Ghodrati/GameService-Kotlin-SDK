/*
 * <copyright file="EventHandler.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.models.internal


/**
 * @author Alireza Ghodrati
 */
class EventHandler<I : EventHandler.IEventHandler<T>, T> {

    interface IEventHandler<T> {
        fun invoke(element: T)
    }

    private var listeners: MutableList<I>? = null


    operator fun plusAssign(other: I) {
        addEventListener(other)
    }


    operator fun minusAssign(other: I) {
        removeEventListener(other)
    }


    private fun addEventListener(listener: I) {
        if (listeners == null) listeners = mutableListOf(listener)
        else listeners?.let { element ->
            if (!element.contains(listener)) element.add(listener)
        }
    }

    private fun removeEventListener(listener: I) {
        listeners?.let { element ->
            if (element.contains(listener)) element.remove(listener)
        }
    }


    internal fun invokeListeners(element: T) {
        listeners?.forEach {
            it.invoke(element)
        }
    }


}