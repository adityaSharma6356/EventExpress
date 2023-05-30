package com.example.eventexpress.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.eventexpress.R
import com.example.eventexpress.presentation.sign_in.GoogleAuthUIClient
import com.example.eventexpress.ui.theme.tiltFont
import com.example.eventexpress.util.clearFocusOnKeyboardDismiss

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
    BackHandler(mainViewModel.expandInfoCard) {
        mainViewModel.expandInfoCard = false
        if(mainViewModel.sideNav){
            mainViewModel.sideNav = false
            iconColor = mainViewModel.lightColor
            mainViewModel.offset = 0
        }
    }
    val offX by animateDpAsState(targetValue = mainViewModel.offset.dp)
    val state = rememberLazyListState()
    var prev by remember { mutableStateOf(0) }
    LaunchedEffect(key1 = state){
        snapshotFlow {state.firstVisibleItemIndex}
            .collect {
                if(!state.canScrollBackward){
                    mainViewModel.searchHt = true
                } else if(it > prev){
                    mainViewModel.searchHt = false
                    prev = it
                } else if(it < prev){
                    mainViewModel.searchHt = true
                    prev = it
                }
            }
    }
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color(79, 27, 18, 255))
        .zIndex(2f)
        .offset(offX), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
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
//                        mainViewModel.offset = 0
                    } else {
                        mainViewModel.sideNav = true
                        iconColor = mainViewModel.brightColor
//                        mainViewModel.offset = 250
                    }
                })
            Text(
                text = "EVENTS",
                fontWeight = FontWeight.ExtraBold,
                fontFamily = tiltFont,
                fontSize = 22.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .align(Alignment.BottomCenter)
            )
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
        AnimatedVisibility(visible = mainViewModel.searchHt) {
            Surface(
                color = mainViewModel.lightColor,
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .padding(5.dp, 0.dp)
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                var tf by remember { mutableStateOf("") }
                OutlinedTextField(
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    value = tf,
                    onValueChange = { tf = it },
                    modifier = Modifier
                        .fillMaxSize()
                        .clearFocusOnKeyboardDismiss(),
                    leadingIcon = {
                        Icon(painter = painterResource(id = R.drawable.search_icon), contentDescription = null, tint = Color.White)
                    }
                )
            }
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
                    mainViewModel.switchToLatest()
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
                    fontFamily = tiltFont,
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
                    mainViewModel.switchToLiked()
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
                    fontFamily = tiltFont,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 10.dp)
                )
            }
            Row( modifier = Modifier
                .clip(RoundedCornerShape(50))
                .widthIn(min = 100.dp, max = 300.dp)
                .fillMaxHeight()
                .background(cs2)
                .clickable {
                    mainViewModel.switchToLatest()
                    mainViewModel.navItemColors[2] = mainViewModel.brightColor
                    mainViewModel.navItemColors[1] = mainViewModel.lightColor
                    mainViewModel.navItemColors[0] = mainViewModel.lightColor
                },verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.ongoing__events_icon), contentDescription = "", tint = cs2, modifier = Modifier
                    .padding(10.dp)
                    .size(35.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(7.dp))
                Text(
                    text = "Ongoing",
                    fontSize = 15.sp,
                    color = Color.White,
                    fontFamily = tiltFont,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 10.dp)
                )
            }
        }
        Spacer(modifier = Modifier
            .height(10.dp)
            .fillMaxWidth())
    }
    AnimatedVisibility(visible = mainViewModel.mainListVisibility , enter = fadeIn(), exit = fadeOut()) {
        LazyColumn(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .zIndex(1f) , horizontalAlignment = Alignment.CenterHorizontally, contentPadding = PaddingValues(bottom = 70.dp, top = 245.dp), state = state){
            items(mainViewModel.state.tempEventsList.size){
                EventCard(it, mainViewModel, googleAuthUiClient)
            }
        }
    }
}












