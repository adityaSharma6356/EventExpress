package com.example.eventexpress.presentation

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.eventexpress.R
import com.example.eventexpress.data.mappers.toUserDataModel
import com.example.eventexpress.presentation.sign_in.GoogleAuthUIClient
import com.example.eventexpress.presentation.sign_in.UserData
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileCard(mainViewModel: MainViewModel, googleAuthUIClient: GoogleAuthUIClient) {
    val lifecycleScope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if(result.resultCode == RESULT_OK) {
                lifecycleScope.launch {
                    val signInResult = googleAuthUIClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    mainViewModel.onSignInResult(signInResult)
                }
            }
        }
    )
    AlertDialog(onDismissRequest = { mainViewModel.openProfile = false }) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(400.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(googleAuthUIClient.getSignedInUser()==null){
                Button(
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(66, 133, 244, 255)),
                    onClick = {
                    lifecycleScope.launch {
                        mainViewModel.loginToGoogle(googleAuthUIClient, launcher)
                    }
                }) {
                    AsyncImage(model =  R.drawable.btn_google_dark_focus_xxxhdpi, contentDescription = "", modifier = Modifier
                        .padding(start = 0.dp, end = 14.dp)
                        .size(40.dp)
                        .align(Alignment.CenterVertically))
                    Text(text = "Sign in with Google", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.ExtraBold, modifier = Modifier.align(Alignment.CenterVertically))
                }
                if(mainViewModel.state.loadingSignIn){
                    Spacer(modifier = Modifier.height(20.dp))
                    CircularProgressIndicator()
                }
            } else {
                mainViewModel.state.userData = googleAuthUIClient.getSignedInUser()!!.toUserDataModel()
                AsyncImage(model = ImageRequest.Builder(LocalContext.current).data(mainViewModel.state.userData.profilePic).placeholder(
                    R.drawable.profile_placeholder).build(),
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .size(150.dp))
                Text(text =  mainViewModel.state.userData.name, fontSize = 20.sp, color = Color(
                    255,
                    255,
                    255,
                    255
                ))
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(255, 70, 85, 255)),
                    onClick = {
                    lifecycleScope.launch {
                        googleAuthUIClient.signOut()
                        mainViewModel.user = UserData()
                        mainViewModel.openProfile = false
                    }
                }) {
                    Text(text = "Sign out", color = Color.White)
                }
            }
        }
    }
}