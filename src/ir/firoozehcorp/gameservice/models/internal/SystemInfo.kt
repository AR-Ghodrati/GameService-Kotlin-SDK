/*
 * <copyright file="SystemInfo.java" company="Firoozeh Technology LTD">
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

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author Alireza Ghodrati
 */
class SystemInfo {

    @SerializedName("DeviceId")
    @Expose
    var deviceUniqueId: String? = null

    @SerializedName("DeviceName")
    @Expose
    var deviceName: String? = null

    @SerializedName("DeviceModel")
    @Expose
    var deviceModel: String? = null

    @SerializedName("DeviceType")
    @Expose
    var deviceType: String? = null

    @SerializedName("OperatingSystem")
    @Expose
    var operatingSystem: String? = null

    @SerializedName("NetworkType")
    @Expose
    var networkType: String? = null

    @SerializedName("ProcessorType")
    @Expose
    var processorType: String? = null

    @SerializedName("ProcessorCount")
    @Expose
    var processorCount: String? = null

    @SerializedName("ProcessorFrequency")
    @Expose
    var processorFrequency: String? = null

    @SerializedName("GraphicsDeviceName")
    @Expose
    var graphicsDeviceName: String? = null

    @SerializedName("GraphicsDeviceVendor")
    @Expose
    var graphicsDeviceVendor: String? = null

    @SerializedName("GraphicsMemorySize")
    @Expose
    var graphicsMemorySize: String? = null

    override fun toString(): String {
        return "SystemInfo{" +
                "DeviceUniqueId='" + deviceUniqueId + '\'' +
                ", DeviceName='" + deviceName + '\'' +
                ", DeviceModel='" + deviceModel + '\'' +
                ", DeviceType='" + deviceType + '\'' +
                ", OperatingSystem='" + operatingSystem + '\'' +
                ", NetworkType='" + networkType + '\'' +
                ", ProcessorType='" + processorType + '\'' +
                ", ProcessorCount='" + processorCount + '\'' +
                ", ProcessorFrequency='" + processorFrequency + '\'' +
                ", GraphicsDeviceName='" + graphicsDeviceName + '\'' +
                ", GraphicsDeviceVendor='" + graphicsDeviceVendor + '\'' +
                ", GraphicsMemorySize='" + graphicsMemorySize + '\'' +
                '}'
    }
}