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

import android.animation.ValueAnimator
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieAnimationView
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.navigation.BottomNavTabs
import com.example.androiddevchallenge.ui.theme.blue
import com.example.androiddevchallenge.ui.theme.gray800
import com.example.androiddevchallenge.ui.theme.grayLight
import com.example.androiddevchallenge.ui.theme.taupe100
import com.example.androiddevchallenge.ui.theme.white

@Composable
fun SearchView(
    onSearchPerformed: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextFieldWeather(
            hint = stringResource(id = R.string.search),
            icon = {
                Icon(
                    modifier = Modifier
                        .size(25.dp),
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = "search city",
                    tint = blue,
                )
            },
            onSearchPerformed = onSearchPerformed
        )
    }
}

@Composable
fun BottomBarView(
    items: List<BottomNavTabs>,
    currentTab: BottomNavTabs,
    onTabClicked: (BottomNavTabs) -> Unit
) {
    BottomNavigation(
        backgroundColor = white
    ) {
        val selectedColor = if (isSystemInDarkTheme()) taupe100 else blue
        items.forEach { tab ->
            BottomNavigationItem(
                selected = currentTab == tab,
                selectedContentColor = selectedColor,
                unselectedContentColor = gray800,
                onClick = {
                    if (currentTab != tab) {
                        onTabClicked.invoke(tab)
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = tab.icon),
                        contentDescription = stringResource(id = tab.label),
                        tint = if (currentTab == tab) {
                            blue
                        } else {
                            grayLight
                        },
                        modifier = Modifier
                            .size(30.dp)
                    )
                },
                alwaysShowLabel = false
            )
        }
    }
}

@Composable
fun LottieView(
    resource: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lottieView = LottieAnimationView(context).apply {
        setAnimation(resource)
        repeatCount = ValueAnimator.INFINITE
    }
    AndroidView(
        { lottieView },
        modifier = modifier,
    ) {
        it.setAnimation(resource)
        it.repeatCount = ValueAnimator.INFINITE
        it.playAnimation()
    }
}

@Composable
fun LoadingView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.padding(top = 250.dp),
            color = blue
        )
    }
}
