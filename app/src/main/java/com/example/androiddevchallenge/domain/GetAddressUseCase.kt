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
package com.example.androiddevchallenge.domain

import android.content.Context
import android.location.Geocoder
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Locale
import javax.inject.Inject

class GetAddressUseCase @Inject constructor(
    @ApplicationContext context: Context
) {
    private val geocoder = Geocoder(context, Locale.getDefault())

    fun execute(address: String): Flow<Pair<Double, Double>> =
        flow {
            val address = geocoder.getFromLocationName(address, 1)
            var lat = 40.3228
            var lon = -3.8657
            if (address.size > 0) {
                lat = address[0].latitude
                lon = address[0].longitude
            }
            emit(Pair(lat, lon))
        }
}
