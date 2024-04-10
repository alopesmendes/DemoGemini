package com.ippon.demogemini.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.ai.client.generativeai.GenerativeModel
import com.ippon.demogemini.generate_content.GenerateContentRoute
import com.ippon.demogemini.generate_content.GenerateContentViewModel
import com.ippon.demogemini.generate_content_image.GenerateContentImageRoute
import com.ippon.demogemini.generate_content_image.GenerateContentImageViewModel
import com.ippon.demogemini.streaming.GenerateContentStreamingRoute
import com.ippon.demogemini.streaming.GenerateContentStreamingViewModel

@Composable
fun NavigationHost(
    generativeModel: GenerativeModel,
    generativeModelVision: GenerativeModel,
) {
    val navController = rememberNavController()
    var selectedDestination: Destination? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(selectedDestination) {
        selectedDestination?.let {
            navController.navigate(it.route)
        }
    }

    NavHost(
        navController = navController,
        startDestination = Destination.GenerateContent.route
    ) {
        composable(
            route = Destination.GenerateContent.route,
        ) {
            val generateContentViewModel = GenerateContentViewModel(
                generativeModel = generativeModel
            )
            ContainerScreen(
                selectedDestination = selectedDestination,
                destinations = destinations,
                onClick = { d ->
                    selectedDestination = d
                    navController.navigate(d.route)
                },
            ) {
                GenerateContentRoute(generateContentViewModel)
            }
        }
        composable(
            route = Destination.GenerateContentImage.route,
        ) {
            val generateContentImageViewModel = GenerateContentImageViewModel(
                generativeModel = generativeModelVision
            )
            ContainerScreen(
                destinations = destinations,
                selectedDestination = selectedDestination,
                onClick = { d ->
                    selectedDestination = d
                },
            ) {
                GenerateContentImageRoute(generateContentImageViewModel)
            }
        }

        composable(
            route = Destination.Streaming.route,
        ) {
            val generateContentStreamingViewModel = GenerateContentStreamingViewModel(
                generativeModel = generativeModel,
                generativeModelVision = generativeModelVision,
            )
            ContainerScreen(
                destinations = destinations,
                selectedDestination = selectedDestination,
                onClick = { d ->
                    selectedDestination = d
                },
            ) {
                GenerateContentStreamingRoute(generateContentStreamingViewModel)
            }
        }

        composable(
            route = Destination.GenerateChat.fullRoute,
        ) {
            ContainerScreen(
                destinations = destinations,
                selectedDestination = selectedDestination,
                onClick = { d ->
                    selectedDestination = d
                },
            ) {
            }
        }

        composable(
            route = Destination.Bonus.fullRoute
        ) {
            ContainerScreen(
                destinations = destinations,
                selectedDestination = selectedDestination,
                onClick = { d ->
                    selectedDestination = d
                },
            ) {
            }
        }
    }
}