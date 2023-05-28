package com.example.eventexpress

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.eventexpress.data.mappers.toUserDataModel
import com.example.eventexpress.domain.models.UserDataModel
import com.example.eventexpress.presentation.BottomScreen
import com.example.eventexpress.presentation.MainScreen
import com.example.eventexpress.presentation.MainViewModel
import com.example.eventexpress.presentation.sign_in.GoogleAuthUIClient
import com.example.eventexpress.ui.theme.EventExpressTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUIClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepVisibleCondition{
                mainViewModel.isLoading.value
            }
        }


        WindowCompat.setDecorFitsSystemWindows(window, false)

        lifecycleScope.launch {
            val temp = googleAuthUiClient.getSignedInUser()
            if(temp!=null){
                mainViewModel.user = temp
                mainViewModel.loadUserData()
            }
        }

        setContent {
            EventExpressTheme {

                val systemUiController = rememberSystemUiController()
                SideEffect {
                    val window = (this as Activity).window
                    window.statusBarColor = Color.Transparent.toArgb()
                    window.navigationBarColor = Color.Transparent.toArgb()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        window.isNavigationBarContrastEnforced = false
                    }
                    systemUiController.statusBarDarkContentEnabled = false
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),color = Color.Black,
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color(79, 27, 18, 255), Color.Transparent)
                                )
                            ),
                        color = Color.Transparent
                    ) {
                        MainScreen(mainViewModel = mainViewModel, googleAuthUiClient)
                        BottomScreen(mainViewModel, googleAuthUiClient)
                    }
                }
            }
        }
    }
}












