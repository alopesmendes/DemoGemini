package com.ippon.demogemini.core

import androidx.navigation.NamedNavArgument

sealed class Destination(
    val route: String,
    val label: String,
    arguments: List<NamedNavArgument> = emptyList(),
) {

    val fullRoute: String = "$route${
        arguments
            .map { it.name }
            .joinToString(prefix = "/", separator = "/") { "{$it}" }
    }"

    data object GenerateContent : Destination(
        route = "generate-content",
        label = "Generate Content",
    )

    data object GenerateContentImage : Destination(
        route = "generate-content-image",
        label = "Generate Content Image"
    )

    data object GenerateChat : Destination(
        route = "generate-chat",
        label = "Generate Chat",
    )

    data object Streaming : Destination(
        route = "streaming",
        // Continuos chat
        label = "Streaming",
    )

    data object Bonus : Destination(
        route = "bonus",
        label = "Bonus",
    )
}

val destinations = listOf(
    Destination.GenerateContent,
    Destination.GenerateContentImage,
    Destination.Streaming,
    Destination.GenerateChat,
    Destination.Bonus,
)