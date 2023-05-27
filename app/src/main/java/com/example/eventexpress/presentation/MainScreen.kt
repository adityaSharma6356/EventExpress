package com.example.eventexpress.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.eventexpress.R
import com.example.eventexpress.presentation.sign_in.GoogleAuthUIClient
import com.example.eventexpress.ui.theme.dancingFamily

@Composable
fun MainScreen(mainViewModel: MainViewModel, googleAuthUiClient: GoogleAuthUIClient){
    if(mainViewModel.openProfile){
        UserProfileCard(mainViewModel = mainViewModel, googleAuthUIClient = googleAuthUiClient)
    }
    var iconColor by remember { mutableStateOf(mainViewModel.lightColor) }
    BackHandler(mainViewModel.sideNav) {
        mainViewModel.sideNav = false
        iconColor = mainViewModel.lightColor
        mainViewModel.offset = 0
    }
    val offX by animateDpAsState(targetValue = mainViewModel.offset.dp)
    Column(modifier = Modifier
        .fillMaxSize()
        .offset(x = offX), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.Transparent),
        ) {
            Icon(painter = painterResource(id = R.drawable.manu_icon), contentDescription = "", tint = Color.White, modifier = Modifier
                .padding(start = 20.dp)
                .size(40.dp)
                .clip(CircleShape)
                .background(iconColor)
                .padding(7.dp)
                .align(Alignment.BottomStart)
                .clickable {
                    if (mainViewModel.sideNav) {
                        mainViewModel.sideNav = false
                        iconColor = mainViewModel.lightColor
                        mainViewModel.offset = 0
                    } else {
                        mainViewModel.sideNav = true
                        iconColor = mainViewModel.brightColor
                        mainViewModel.offset = 250
                    }
                })
            Text(text = "EVENTS", fontWeight = FontWeight.ExtraBold, fontFamily = dancingFamily, fontSize = 22.sp, color = Color.White, modifier = Modifier
                .padding(bottom = 4.dp)
                .align(Alignment.BottomCenter))
            AsyncImage(model = ImageRequest.Builder(LocalContext.current).data(googleAuthUiClient.getSignedInUser()?.profilePictureUrl).placeholder(
                R.drawable.profile_placeholder).build(),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .padding(end = 20.dp)
                    .clip(CircleShape)
                    .background(mainViewModel.lightColor)
                    .align(Alignment.BottomEnd)
                    .size(40.dp)
                    .clickable {
                        mainViewModel.openProfile = true
                    })

        }
        Spacer(modifier = Modifier
            .height(10.dp)
            .fillMaxWidth())
        Surface(
            color = mainViewModel.lightColor,
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .padding(20.dp, 0.dp)
                .fillMaxWidth()
                .height(60.dp)
        ) {
        }
        Spacer(modifier = Modifier
            .height(10.dp)
            .fillMaxWidth())
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)){
            val cs0 by animateColorAsState(targetValue = mainViewModel.navItemColors[0])
            val cs1 by animateColorAsState(targetValue = mainViewModel.navItemColors[1])
            val cs2 by animateColorAsState(targetValue = mainViewModel.navItemColors[2])
            Row( modifier = Modifier
                .clip(RoundedCornerShape(50))
                .widthIn(min = 100.dp, max = 300.dp)
                .fillMaxHeight()
                .clickable {
                    mainViewModel.navItemColors[0] = mainViewModel.brightColor
                    mainViewModel.navItemColors[2] = mainViewModel.lightColor
                    mainViewModel.navItemColors[1] = mainViewModel.lightColor
                }
                .background(cs0),verticalAlignment = Alignment.CenterVertically,) {
                Icon(painter = painterResource(id = R.drawable.flash), contentDescription = "", tint = cs0, modifier = Modifier
                    .padding(10.dp)
                    .size(35.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(7.dp))
                Text(
                    text = "Latest",
                    fontSize = 15.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 10.dp)
                )
            }
            Row( modifier = Modifier
                .clip(RoundedCornerShape(50))
                .widthIn(min = 100.dp, max = 300.dp)
                .fillMaxHeight()
                .background(cs1)
                .clickable {
                    mainViewModel.navItemColors[1] = mainViewModel.brightColor
                    mainViewModel.navItemColors[2] = mainViewModel.lightColor
                    mainViewModel.navItemColors[0] = mainViewModel.lightColor
                },verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.fav_icon), contentDescription = "", tint = cs1, modifier = Modifier
                    .padding(10.dp)
                    .size(35.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(7.dp))
                Text(
                    text = "Favourites",
                    fontSize = 15.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 10.dp)
                )
            }
        }
        Spacer(modifier = Modifier
            .height(10.dp)
            .fillMaxWidth())
        LazyColumn(Modifier.weight(1f) , contentPadding = PaddingValues(bottom = 70.dp)){
            items(mainViewModel.state.eventsList.size){
                EventCard(it, mainViewModel)
            }
        }
    }
}












