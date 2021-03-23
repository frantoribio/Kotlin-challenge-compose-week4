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
package com.example.androiddevchallenge.navigation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.presentation.WeatherViewModel
import com.example.androiddevchallenge.ui.BottomBarView
import com.example.androiddevchallenge.ui.SearchView
import com.example.androiddevchallenge.ui.TodayScreen
import com.example.androiddevchallenge.ui.WeeklyScreen
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding

sealed class Screen(val route: String) {
    object TodayScreen : Screen("today_screen")
}

enum class BottomNavTabs(val label: Int, val icon: Int) {
    TODAY(R.string.today, R.drawable.ic_today),
    WEEKLY(R.string.weekly, R.drawable.ic_weekly),
}

@Composable
fun BuildNavigation(
    viewModel: WeatherViewModel,
    startScreen: Screen = Screen.TodayScreen
) {
    val navController = rememberNavController()
    val selectedTab = remember { mutableStateOf(BottomNavTabs.TODAY) }
    NavHost(
        navController = navController,
        startDestination = startScreen.route
    ) {
        composable(route = Screen.TodayScreen.route) {
            NavScreen(
                viewModel = viewModel,
                selectedTab = selectedTab
            )
        }
    }
}

@Composable
fun NavScreen(
    viewModel: WeatherViewModel,
    selectedTab: MutableState<BottomNavTabs>
) {
    Scaffold(
        bottomBar = {
            BottomBarView(
                listOf(
                    BottomNavTabs.TODAY,
                    BottomNavTabs.WEEKLY
                ),
                currentTab = selectedTab.value,
                onTabClicked = {
                    selectedTab.value = it
                }
            )
        }

    ) { innerPadding ->
        ProvideWindowInsets {
            Box(
                Modifier
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .padding(innerPadding)
            ) {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .verticalScroll(scrollState)
                ) {
                    SearchView(
                        onSearchPerformed = { query ->
                            viewModel.getLocation(query)
                        }
                    )
                    Crossfade(selectedTab.value) { destination ->
                        when (destination) {
                            BottomNavTabs.TODAY -> TodayScreen(
                                weatherResponse = viewModel.weatherResponse,
                                address = viewModel.address,
                                isLoading = viewModel.isLoading
                            )
                            BottomNavTabs.WEEKLY -> WeeklyScreen(
                                weatherResponse = viewModel.weatherResponse,
                                isLoading = viewModel.isLoading
                            )
                        }
                    }
                }
            }
        }
    }
}
