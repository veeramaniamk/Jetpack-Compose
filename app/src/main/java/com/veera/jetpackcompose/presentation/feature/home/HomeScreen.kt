package com.veera.jetpackcompose.presentation.feature.home

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)

@Composable
fun responsivePadding(maxWidth: Dp): Dp =
    when {
        maxWidth >= 420.dp -> 28.dp
        maxWidth >= 380.dp -> 24.dp
        maxWidth >= 340.dp -> 20.dp
        else -> 16.dp
    }
@Composable
fun HomePage(
    modifier: Modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues()),
    navController: NavController
) {

    Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
        CustomBottomBar(navController)
    }

}


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun CustomBottomBar(navController: NavController) {

    val items = listOf(
        BottomItem("home", "Home", Icons.Default.Home),
        BottomItem("login", "Notification", Icons.Default.Notifications),
        BottomItem("splash", "Reports", Icons.Default.List),
        BottomItem("home", "Settings", Icons.Default.Settings)
    )

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    var selectedIndex by remember { mutableIntStateOf(0) }

    val itemCenters = remember { mutableStateListOf<Float>() }

    val density = LocalDensity.current
    val pillWidthPx = with(density) { 60.dp.toPx() }

    // Normal target position (center - width/2)
    val baseOffset = itemCenters.getOrNull(selectedIndex)?.let { center ->
        with(density) { (center - pillWidthPx / 2f).toDp() }
    } ?: 0.dp

    // -----------------------------
    // OVERSHOOT SLIDE STATE
    // -----------------------------
    val animatedOffset = remember { Animatable(baseOffset.value) }
    var initialSet by remember { mutableStateOf(false) }
    var previousIndex by remember { mutableStateOf(selectedIndex) }

    LaunchedEffect(itemCenters) {
        val center = itemCenters.getOrNull(selectedIndex) ?: return@LaunchedEffect
        if (!initialSet) {
            val target = with(density) { (center - pillWidthPx / 2f).toDp().value }
            animatedOffset.snapTo(target)
            initialSet = true
        }
    }

    // Trigger overshoot animation when tab changes
    LaunchedEffect(selectedIndex, baseOffset) {

        val direction = when {
            selectedIndex > previousIndex -> 1f   // RIGHT
            selectedIndex < previousIndex -> -1f  // LEFT
            else -> 0f
        }

        previousIndex = selectedIndex

        if (direction == 0f) return@LaunchedEffect

        val overshootPx = with(density) { 4.dp.toPx() } * direction

        // -------------- ANIMATION ---------------
        animatedOffset.animateTo(
            // step 1: overshoot beyond target
            targetValue = baseOffset.value + with(density) { overshootPx.toDp().value },
            animationSpec = tween(
                durationMillis = 220,
                easing = LinearOutSlowInEasing
            )
        )

        animatedOffset.animateTo(
            // step 2: return & settle exactly at target
            targetValue = baseOffset.value,
            animationSpec = tween(
                durationMillis = 180,
                easing = LinearOutSlowInEasing
            )
        )
    }

    val interactionSource = remember { MutableInteractionSource() }

    BoxWithConstraints {
        val padd = responsivePadding(maxWidth)

        Surface(
            modifier = Modifier
                .wrapContentSize()
                .background(Color.Transparent)
                .padding(
                    start = 30.dp,
                    end = 30.dp,
                    bottom = WindowInsets.navigationBars
                        .asPaddingValues()
                        .calculateBottomPadding() + 16.dp
                ),
            shape = RoundedCornerShape(24.dp),
            shadowElevation = 2.dp,
            tonalElevation = 6.dp,
            color = Color.White
        )
        {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
            ) {

                // --------------------------
                // SMOOTH OVERSHOOT PILL
                // --------------------------

                Surface(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 14.dp, bottom = 12.dp)
                        .width(60.dp)
                        .height(45.dp)
                        .offset(x = animatedOffset.value.dp)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(16.dp),
                    shadowElevation = 2.dp,      // <-- elevation
                    tonalElevation = 6.dp,
                    color = Color(0xFF00BCD4)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    items.forEachIndexed { index, item ->

                        val targetScale = if (index == selectedIndex) 1.18f else 1f

                        val scale by animateFloatAsState(
                            targetValue = targetScale,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )

                        val animatedTint by animateColorAsState(
                            targetValue = if (index == selectedIndex) Color.White else Color.Gray,
                            animationSpec = tween(durationMillis = 350)
                        )

                        Column(
                            modifier = Modifier
                                .padding(horizontal = padd)
                                .onGloballyPositioned { coords ->
                                    val x = coords.positionInParent().x
                                    val width = coords.size.width
                                    val center = x + width / 2f

                                    if (itemCenters.size > index)
                                        itemCenters[index] = center
                                    else
                                        itemCenters.add(center)
                                }
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) {
                                   /* navController.navigate(item.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }*/
                                    selectedIndex = index
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = animatedTint,
                                modifier = Modifier
                                    .size(25.dp)
                                    .graphicsLayer {
                                        scaleX = scale
                                        scaleY = scale
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}
