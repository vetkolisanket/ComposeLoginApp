package com.example.composeloginapp.ui.screens

import android.graphics.drawable.AdaptiveIconDrawable
import android.os.Build
import android.view.animation.OvershootInterpolator
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.composeloginapp.R
import com.example.composeloginapp.core.Route
import com.example.composeloginapp.ui.SplashViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val viewModel: SplashViewModel = hiltViewModel()
    val isUserLoggedIn by viewModel.isUserLoggedIn()
        .collectAsStateWithLifecycle(initialValue = null)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val scale = remember {
            Animatable(0f)
        }

        LaunchedEffect(key1 = Unit) {
            scale.animateTo(
                targetValue = 0.7f,
                animationSpec = tween(
                    durationMillis = 800,
                    easing = {
                        OvershootInterpolator(4f).getInterpolation(it)
                    })
            )
            delay(2500)
            if (isUserLoggedIn == true) {
                navController.navigate(Route.Home.route)
            } else if (isUserLoggedIn == false) {
                navController.navigate(Route.Login.route)
            }
        }
        Image(
            painter = adaptiveIconPainterResource(id = R.mipmap.ic_launcher_round),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(160.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Compose Login App",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
fun adaptiveIconPainterResource(@DrawableRes id: Int): Painter {
    val res = LocalContext.current.resources
    val theme = LocalContext.current.theme

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Android O supports adaptive icons, try loading this first (even though this is least likely to be the format).
        val adaptiveIcon = ResourcesCompat.getDrawable(res, id, theme) as? AdaptiveIconDrawable
        if (adaptiveIcon != null) {
            BitmapPainter(adaptiveIcon.toBitmap().asImageBitmap())
        } else {
            // We couldn't load the drawable as an Adaptive Icon, just use painterResource
            painterResource(id)
        }
    } else {
        // We're not on Android O or later, just use painterResource
        painterResource(id)
    }
}