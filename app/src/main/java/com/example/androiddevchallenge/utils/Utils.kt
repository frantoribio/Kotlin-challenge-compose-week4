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
package com.example.androiddevchallenge.utils

import android.annotation.SuppressLint
import java.time.Instant
import java.time.ZoneId
import java.util.Calendar

@SuppressLint("NewApi")
fun Long.toReadableDate(): String {
    val localDate = Instant.ofEpochMilli(this * 1000L)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
    return "${
    localDate.dayOfWeek.name.toLowerCase().capitalize()
    }, ${
    localDate.month.name.toLowerCase().capitalize()
    } ${localDate.dayOfMonth}, ${localDate.year}"
}

fun Long.getDayHour(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this * 1000L
    val hour = if (calendar.get(Calendar.HOUR_OF_DAY) < 10) {
        "0${calendar.get(Calendar.HOUR_OF_DAY)}"
    } else {
        calendar.get(Calendar.HOUR_OF_DAY).toString()
    }
    val minute = if (calendar.get(Calendar.MINUTE) < 10) {
        "0${calendar.get(Calendar.MINUTE)}"
    } else {
        calendar.get(Calendar.MINUTE).toString()
    }
    return "$hour:$minute"
}

fun Int.mapToWeatherAsset(): String =
    when (this) {
        in 200..232 -> "weather_storm.json"
        in 300..321 -> "weather_partly_shower.json"
        in 500..531 -> "weather_storm.json"
        in 600..622 -> "weather_snow.json"
        in 701..781 -> "weather_mist.json"
        800 -> "weather_sunny.json"
        in 801..802 -> "weather_partly_cloudy.json"
        in 803..804 -> "weather_windy.json"
        else -> "weather_sunny.json"
    }
