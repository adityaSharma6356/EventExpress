package com.example.eventexpress.presentation

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventexpress.domain.repository.DataRepository
import com.example.eventexpress.presentation.sign_in.GoogleAuthUIClient
import com.example.eventexpress.presentation.sign_in.SignInResult
import com.example.eventexpress.presentation.sign_in.UserData
import com.example.eventexpress.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val database: DataRepository
): ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()
    var state by mutableStateOf(UiStates())
    var sideNav by mutableStateOf(false)
    var offset by mutableStateOf(0)
    val brightColor = Color(255, 70, 85, 255)
    val lightColor = Color(88, 60, 53, 255)
    val transiColor = Color(255, 255, 255, 32)
    val navItemColors = mutableStateListOf(brightColor, lightColor, lightColor)


    init {
        viewModelScope.launch {
            delay(200)
            _isLoading.value = false
        }
        loadEvents()
    }

    private fun loadEvents(){
        viewModelScope.launch {
            state = state.copy(loading = true)
            database.getEvents(false).collect{
                when(it){
                    is Resource.Success -> {
                        it.data?.let { list ->
                            state = state.copy(eventsList = list)
                        }
                    }
                    is Resource.Loading -> Unit
                    is Resource.Error -> {
                    }
                }
            }
            state = state.copy(loading = false)
        }
    }

    fun loginToGoogle(
        googleAuthUIClient: GoogleAuthUIClient,
        launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>
    ){
        viewModelScope.launch {
            state = state.copy(loadingSignIn = true)
            val signInIntentSender = googleAuthUIClient.signIn()
            launcher.launch(
                IntentSenderRequest.Builder(
                    signInIntentSender ?: return@launch
                ).build()
            )
        }
    }
    var user by mutableStateOf(UserData())
    var openProfile by mutableStateOf(false)

    fun onSignInResult(result: SignInResult) {
        state = state.copy(loadingSignIn = false)
        openProfile = false
    }
}
