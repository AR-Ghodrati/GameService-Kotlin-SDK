/*
 * <copyright file="$this.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.testUnit;

import ir.firoozehcorp.gameservice.builder.GameServiceClientConfiguration;
import ir.firoozehcorp.gameservice.core.GameService;
import ir.firoozehcorp.gameservice.models.GameServiceException;
import ir.firoozehcorp.gameservice.models.internal.SystemInfo;
import ir.firoozehcorp.gameservice.models.internal.interfaces.GameServiceCallback;
import org.jetbrains.annotations.NotNull;


public class TestUnit {


    public static void main(String... a) {


        SystemInfo info = new SystemInfo();
        info.setDeviceUniqueId("*");

        GameServiceClientConfiguration config = new GameServiceClientConfiguration.Builder()
                .setClientId("*")
                .setClientSecret("*")
                .setSystemInfo(info)
                .build();

        try {

            GameService.INSTANCE.configurationInstance(config);
            GameService.INSTANCE.login("*@gmail.com", "*", new GameServiceCallback<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Bucket bucket = new Bucket();
                        bucket.Name = "ff";

                        GameService.INSTANCE.addBucketItem("*", bucket
                                , Bucket.class
                                , new GameServiceCallback<>() {
                                    @Override
                                    public void onResponse(Bucket response) {
                                        System.out.println(response);
                                    }

                                    @Override
                                    public void onFailure(@NotNull GameServiceException error) {
                                        System.out.println("getLeaderBoardsE1 " + error.getMessage());

                                    }
                                });
                    } catch (GameServiceException e) {
                        System.out.println("loginE1 " + e.getMessage());
                    }

                }

                @Override
                public void onFailure(@NotNull GameServiceException error) {
                    System.out.println("loginE2 " + error.getMessage());

                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


}
