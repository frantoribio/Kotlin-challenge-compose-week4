/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.data

import com.example.androiddevchallenge.data.remote.WeatherApi
import com.example.androiddevchallenge.data.remote.WeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherApi
) {
    fun get(coordinates: Pair<Double, Double>): Flow<WeatherResponse> =
        flow {
            emit(
                api.getWeatherInfo(
                    lat = coordinates.first.toString(),
                    lon = coordinates.second.toString(),
                    appId = "2a33b3fa0ff4ec20a57e349f30d63b59",
                    units = "metric",
                    exclude = "minutely"
                )
            )
        }
}
