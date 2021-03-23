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
package com.example.androiddevchallenge.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.data.WeatherRepository
import com.example.androiddevchallenge.data.remote.WeatherResponse
import com.example.androiddevchallenge.domain.GetAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val getAddressUseCase: GetAddressUseCase
) : ViewModel() {

    var weatherResponse by mutableStateOf(WeatherResponse())
    var address by mutableStateOf("Madrid")
    var coordinates by mutableStateOf(Pair(40.322769, -3.865740))
    var isLoading by mutableStateOf(false)

    init {
        isLoading = true
        getLocation(address)
    }

    fun getLocation(address: String) {
        isLoading = true
        getAddressUseCase.execute(address)
            .map {
                this.coordinates = it
                getWeather(coordinates, address)
            }
            .flowOn(Dispatchers.Default)
            .catch { e ->
                e.printStackTrace()
                isLoading = false
            }
            .launchIn(viewModelScope)
    }

    private fun getWeather(coordinates: Pair<Double, Double>, address: String) {
        repository.get(coordinates)
            .map {
                this.weatherResponse = it
                this.address = address
                this.isLoading = false
            }
            .flowOn(Dispatchers.Default)
            .catch { e ->
                e.printStackTrace()
                isLoading = false
            }
            .launchIn(viewModelScope)
    }
}
