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
package com.example.androiddevchallenge.data.remote

data class WeatherResponse(
    val lat: Float,
    val long: Float,
    val timezone: String,
    val current: Current?,
    val hourly: List<Hourly>,
    val daily: List<Daily>
) {
    constructor() : this(
        0f,
        0f,
        "",
        null,
        emptyList(),
        emptyList()
    )
}

data class Current(
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: Float,
    val pressure: Int,
    val humidity: Int,
    val uvi: Float,
    val wind_speed: Float,
    val weather: List<Weather>
)

data class Hourly(
    val dt: Long,
    val temp: Float,
    val pressure: Int,
    val humidity: Int,
    val uvi: Float,
    val wind_speed: Float,
    val weather: List<Weather>
)

data class Daily(
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val pressure: Int,
    val humidity: Int,
    val weather: List<Weather>,
    val temp: Temp
)

data class Temp(
    val day: Float,
    val night: Float,
    val min: Float,
    val max: Float,
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String
)

fun test(): WeatherResponse =
    WeatherResponse(
        0f,
        0f,
        "",
        Current(
            1616149116,
            1616134826,
            1616178358,
            9.21f,
            1015,
            49,
            2.08f,
            1.54f,
            listOf(
                Weather(
                    601,
                    "Clouds",
                    "few clouds"
                )
            )
        ),
        emptyList(),
        emptyList()
    )
