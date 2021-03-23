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
package com.example.androiddevchallenge.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.remote.Daily
import com.example.androiddevchallenge.data.remote.WeatherResponse
import com.example.androiddevchallenge.data.remote.test
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.blue
import com.example.androiddevchallenge.ui.theme.white
import com.example.androiddevchallenge.utils.mapToWeatherAsset
import com.example.androiddevchallenge.utils.toReadableDate
import kotlin.math.roundToInt

@Composable
fun WeeklyScreen(
    weatherResponse: WeatherResponse,
    isLoading: Boolean
) {
    if (!isLoading) {
        Column(Modifier.fillMaxSize()) {
            weatherResponse.daily.map {
                ExpandableItem(
                    day = it
                )
            }
        }
    } else {
        LoadingView()
    }
}

@Composable
fun ExpandableItem(
    day: Daily
) {
    val weather = day.weather[0]
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = blue,
        elevation = 10.dp
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(3f)) {
                Text(
                    day.dt.toReadableDate(),
                    color = white,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = weather.description,
                    color = white,
                    style = MaterialTheme.typography.body2
                )
            }
            Row(modifier = Modifier.weight(1.1f)) {
                LottieView(
                    weather.id.mapToWeatherAsset(),
                    Modifier.size(50.dp)
                )
                Column(Modifier.padding(start = 5.dp)) {
                    Text(
                        "${day.temp.max.roundToInt()}º",
                        color = white,
                        style = MaterialTheme.typography.h2
                    )
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = "${day.temp.min.roundToInt()}º",
                        color = white,
                        style = MaterialTheme.typography.h2
                    )
                }
            }
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun WeeklyScreenPreview() {
    MyTheme(darkTheme = false) {
        WeeklyScreen(
            test(),
            false
        )
    }
}
