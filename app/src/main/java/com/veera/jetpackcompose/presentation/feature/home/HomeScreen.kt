package com.veera.jetpackcompose.presentation.feature.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

data class BottomItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)

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
//        CustomBottomBar(navController)
        CustomBottomBarTest()
    }

}


@Preview(showBackground = true, device = "spec:width=320dp,height=850.9dp,dpi=440")
@UiComposable
@Composable
fun CustomBottomBarTest() {

    val items = listOf(
        BottomItem("home", "Home", Icons.Default.Home),
        BottomItem("login", "Notification", Icons.Default.Notifications),
        BottomItem("splash", "Reports", Icons.Default.List),
        BottomItem("home", "Settings", Icons.Default.Settings)
    )

    val itemCenters = remember { mutableStateListOf<Float>() }
    var selectedIndex by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current
    val pillWidthPx = with(density) { 60.dp.toPx() }

    // Normal target position (center - width/2)
    val baseOffset = itemCenters.getOrNull(selectedIndex)?.let { center ->
        with(density) {
            (center - pillWidthPx / 2f).toDp()
        }
    } ?: 0.dp
    var animatedOffset = remember { Animatable(0f) }
    var previousIndex by remember { mutableStateOf(selectedIndex) }
    var dragOffset by remember { mutableStateOf(0f) }

    /*LaunchedEffect(itemCenters) {
        val center = itemCenters.getOrNull(selectedIndex) ?: return@LaunchedEffect
        if (!initialSet) {
            val target = with(density) { (center - pillWidthPx / 2f).toDp().value }
            animatedOffset.snapTo(target)
            initialSet = true
        }
    }*/

    // Trigger overshoot animation when tab changes
    LaunchedEffect(selectedIndex, baseOffset) {

        val center = itemCenters.getOrNull(selectedIndex) ?: return@LaunchedEffect
        val target = center - pillWidthPx / 2f

        val direction = when {
            selectedIndex > previousIndex -> 1f
            selectedIndex < previousIndex -> -1f
            else -> 0f
        }
        previousIndex = selectedIndex

        val overshoot = with(density) { 4.dp.toPx() } * direction

        animatedOffset.animateTo(target + overshoot, tween(220, easing = LinearOutSlowInEasing))
        animatedOffset.animateTo(target, tween(180, easing = LinearOutSlowInEasing))

        dragOffset = 0f
    }

//    BoxWithConstraints {

        val shape = RoundedCornerShape(24.dp)
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
            shape = shape ,
            shadowElevation = 2.dp,
            tonalElevation = 6.dp,
            color = Color.White
        )
        {

            val scope = rememberCoroutineScope()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .clip(shape)
                    .background(Color.White)
                    .pointerInput(Unit) {
                        awaitEachGesture {

                            // 1️⃣ Wait for touch down
                            val down = awaitFirstDown()

                            val touchX = down.position.x

                            // 2️⃣ Find nearest item center
                            val nearestIndex = itemCenters
                                .mapIndexed { index, center ->
                                    index to kotlin.math.abs(center - touchX)
                                }
                                .minByOrNull { it.second }
                                ?.first

                            // 3️⃣ If touch is reasonably close, select
                            if (nearestIndex != null) {
                                selectedIndex = nearestIndex

                                // Optional: animate pill immediately
                                scope.launch {
                                    val target =
                                        itemCenters[nearestIndex] - pillWidthPx / 2f

                                    animatedOffset.animateTo(
                                        target,
                                        animationSpec = tween(
                                            durationMillis = 220,
                                            easing = LinearOutSlowInEasing
                                        )
                                    )
                                }
                            }
                        }
                    }
            )
            {

                /**
                 * Tracker behind the bottom tab bar item
                 */

                var dragging by remember { mutableStateOf(false) }

                val targetScale = if (dragging) 1.18f else 1f

                val scale by animateFloatAsState(
                    targetValue = targetScale,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )

                fun targetOffsetFor(index: Int): Float {
                    val center = itemCenters.getOrNull(index) ?: return animatedOffset.value
                    return center - pillWidthPx / 2f
                }

                val minOffsetPx =
                    itemCenters.firstOrNull()?.minus(pillWidthPx / 2f) ?: 0f

                val maxOffsetPx =
                    itemCenters.lastOrNull()?.minus(pillWidthPx / 2f) ?: 0f

                Surface(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 14.dp, bottom = 12.dp)
                        .width(60.dp).offset {
                            IntOffset(
                                (animatedOffset.value + dragOffset).roundToInt(),
                                0
                            )
                        }
                        .pointerInput(Unit) {
                            detectHorizontalDragGestures (
                                onDragStart = {
                                    dragging = true
                                },
                                onHorizontalDrag = { _, dragAmount ->
                                    // amplify slightly for lighter feel
//                                    dragOffset += dragAmount * 0.6f
                                    // 1️⃣ STOP animation & move pill directly
                                    val resistanceZone = 40f // px, how far it can stretch

                                    val newOffset = animatedOffset.value + dragAmount * 1.3f
                                    val clampedOffset = when {
                                        newOffset < minOffsetPx ->
                                            minOffsetPx - (minOffsetPx - newOffset).coerceAtMost(resistanceZone) * 0.3f

                                        newOffset > maxOffsetPx ->
                                            maxOffsetPx + (newOffset - maxOffsetPx).coerceAtMost(resistanceZone) * 0.3f

                                        else -> newOffset
                                    }

                                    scope.launch {
                                        animatedOffset.snapTo(clampedOffset)
                                    }

                                    /*scope.launch {
                                        animatedOffset.snapTo(
                                            animatedOffset.value + dragAmount * 1.3f
                                        )
                                    }*/

                                    // 2️⃣ Find pill center
                                    val pillCenter =
                                        animatedOffset.value + pillWidthPx / 2f

                                    // 3️⃣ Update selection when near item
                                    val nearestIndex = itemCenters
                                        .mapIndexed { index, center ->
                                            index to kotlin.math.abs(center - pillCenter)
                                        }
                                        .minByOrNull { it.second }
                                        ?.first ?: return@detectHorizontalDragGestures

                                    if (nearestIndex != selectedIndex) {
                                        selectedIndex = nearestIndex
                                    }
                                },
                                onDragEnd = {
                                    dragging = false
                                    dragOffset = 0f
                                    scope.launch {
                                        val target = targetOffsetFor(selectedIndex)

                                        animatedOffset.animateTo(
                                            target,
                                            animationSpec = tween(
                                                durationMillis = 220,
                                                easing = LinearOutSlowInEasing
                                            )
                                        )
                                    }
                                },
                                onDragCancel = {
                                    dragging = false
                                    dragOffset = 0f
                                }
                            )
                        }
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(16.dp),
                    shadowElevation = 2.dp,      // <-- elevation
                    tonalElevation = 6.dp,
                    color = Color(0xFF00BCD4)
                ) {

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
                            modifier = Modifier.weight(1f)
                                .onGloballyPositioned { coords ->
                                    val x = coords.positionInParent().x
                                    val width = coords.size.width
                                    val center = x + width / 2f
                                    if (itemCenters.size > index)
                                        itemCenters[index] = center
                                    else
                                        itemCenters.add(center)
                                }
                            ,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        )
                        {
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
//    }
}
