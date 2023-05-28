package com.example.eventexpress.presentation

import android.content.Context
import android.util.Log
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
import com.example.eventexpress.data.mappers.toUserDataModel
import com.example.eventexpress.domain.models.EventModel
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
    var expandInfoCard by mutableStateOf(false)
    var offset by mutableStateOf(0)
    var searchHt by mutableStateOf(true)
    val brightColor = Color(255, 70, 85, 255)
    val lightColor = Color(88, 60, 53, 255)
    val transiColor = Color(255, 255, 255, 32)
    var color1 by mutableStateOf(Color.White)
    var color2 by mutableStateOf(transiColor)
    val navItemColors = mutableStateListOf(brightColor, lightColor, lightColor)
    var user by mutableStateOf(UserData())
    var mainListVisibility by mutableStateOf(true)

    init {
        viewModelScope.launch {
            _isLoading.value = false
        }
        loadEvents()
        state = state.copy(tempEventsList = state.eventsList)
    }

    fun changeLike(like: Boolean){
        val data = state.userData
        if(like){
            data.userFavourites.add(state.currentEvent.id)
        } else {
            data.userFavourites.remove(state.currentEvent.id)
        }
        state = state.copy(userData = data)
        Log.d("likestate", state.userData.userFavourites.toString())
        saveUserdata()
        loadUserData()
    }
    private fun saveUserdata(){
        viewModelScope.launch {
            state = state.copy(loading = true)
            database.saveUserData(state.userData)
            state = state.copy(loading = false)
        }
    }

    fun switchToLatest(){
        viewModelScope.launch {
            mainListVisibility = false
            delay(350L)
            val list  = state.eventsList.sortedByDescending { it.date }
            state = state.copy(tempEventsList = list)
            mainListVisibility = true
        }
    }
    fun switchToLiked(){
        viewModelScope.launch {
            mainListVisibility = false
            delay(350L)
            val list = state.eventsList.filter { bItem -> state.userData.userFavourites.any { aItem -> aItem == bItem.id } }
            state = state.copy(tempEventsList = list)
            mainListVisibility = true
        }
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
            state = state.copy(tempEventsList = state.eventsList)
        }
    }
    fun setupColors(){
        if(state.userData.userFavourites.contains(state.currentEvent.id)){
            color1 = brightColor
            color2 = Color.White
        } else {
            color1 = Color.White
            color2 = transiColor
        }
    }
    fun loadUserData(){
        val data = user
        viewModelScope.launch {
            state = state.copy(loading = true)
            database.getUserData(data.userId).collect{
                when(it){
                    is Resource.Success -> {
                        it.data?.let { userData ->
                            state = state.copy(userData = userData)
                        }
                    }
                    is Resource.Loading -> Unit
                    is Resource.Error -> {
                        if(it.message=="noData"){
                            database.saveUserData(user.toUserDataModel())
                            state = state.copy(userData = user.toUserDataModel())
                        }
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

    var openProfile by mutableStateOf(false)

    fun onSignInResult(result: SignInResult) {
        state = state.copy(loadingSignIn = false)
        openProfile = false
        if(result.data!=null){
            user = result.data
            loadUserData()
        }

    }
}
