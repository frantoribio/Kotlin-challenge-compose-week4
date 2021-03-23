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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.remote.WeatherResponse
import com.example.androiddevchallenge.data.remote.test
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.blue
import com.example.androiddevchallenge.ui.theme.gray900
import com.example.androiddevchallenge.ui.theme.white
import com.example.androiddevchallenge.utils.getDayHour
import com.example.androiddevchallenge.utils.mapToWeatherAsset
import com.example.androiddevchallenge.utils.toReadableDate
import kotlin.math.roundToInt

@Composable
fun TodayScreen(
    weatherResponse: WeatherResponse,
    address: String,
    isLoading: Boolean
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (!isLoading) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    backgroundColor = blue,
                    elevation = 10.dp
                ) {
                    weatherResponse.current?.let {
                        Row(Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = it.dt.toReadableDate(),
                                    color = white,
                                    style = MaterialTheme.typography.subtitle2
                                )
                                Text(
                                    text = address.capitalize(),
                                    color = white,
                                    style = MaterialTheme.typography.h1
                                )
                                Text(
                                    text = "${it.temp.roundToInt()}º",
                                    color = white,
                                    style = MaterialTheme.typography.h1,
                                    fontSize = 50.sp
                                )
                                Text(
                                    text = it.weather[0].main.capitalize(),
                                    color = white,
                                    style = MaterialTheme.typography.subtitle2
                                )
                                Text(
                                    text = it.weather[0].description.capitalize(),
                                    color = white,
                                    style = MaterialTheme.typography.body1
                                )
                            }
                            LottieView(
                                it.weather[0].id.mapToWeatherAsset(),
                                Modifier.size(180.dp)
                            )
                        }
                    }
                }

                weatherResponse.current?.let {
                    SecondaryValues(
                        it.uvi,
                        it.sunrise,
                        it.sunset,
                        it.humidity
                    )
                }

                Text(
                    "Hourly",
                    color = gray900,
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, start = 16.dp)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    val hourly = weatherResponse.hourly
                    items(hourly.size) { index ->
                        ItemHourly(
                            time = hourly[index].dt.getDayHour(),
                            resourceAnimation = hourly[index].weather[0].id.mapToWeatherAsset(),
                            temp = hourly[index].temp.roundToInt().toString()
                        )
                    }
                }
            }
        } else {
            LoadingView()
        }
    }
}

@Composable
fun ItemHourly(
    time: String,
    resourceAnimation: String,
    temp: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(start = 16.dp, bottom = 20.dp),
        elevation = 8.dp,
        backgroundColor = blue
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                time,
                color = white,
                style = MaterialTheme.typography.h2,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            )
            LottieView(
                resource = resourceAnimation,
                modifier = Modifier
                    .size(50.dp)
            )
            Text(
                "${temp}º",
                color = white,
                style = MaterialTheme.typography.h2
            )
        }
    }
}

@Composable
fun SecondaryValues(
    uv: Float,
    sunrise: Long,
    sunset: Long,
    humidity: Int
) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp),
        elevation = 8.dp,
        backgroundColor = blue
    ) {
        Row(
            Modifier
                .fillMaxWidth(),
        ) {
            SecondaryItem(
                icon = R.drawable.ic_uv,
                value = "$uv %",
                modifier = Modifier.weight(1f)
            )
            SecondaryItem(
                icon = R.drawable.ic_sunrise,
                value = sunrise.getDayHour(),
                modifier = Modifier.weight(1f)
            )
            SecondaryItem(
                icon = R.drawable.ic_sunset,
                value = sunset.getDayHour(),
                modifier = Modifier.weight(1f)
            )
            SecondaryItem(
                icon = R.drawable.ic_humidity,
                value = "$humidity %",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun SecondaryItem(
    icon: Int,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier
                .size(40.dp)
                .padding(bottom = 8.dp),
            painter = painterResource(id = icon),
            tint = white,
            contentDescription = null
        )
        Text(
            value,
            color = white,
            style = MaterialTheme.typography.h2
        )
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun HomeScreenPreview() {
    MyTheme(darkTheme = false) {
        TodayScreen(
            test(),
            "Madrid",
            true
        )
    }
}
