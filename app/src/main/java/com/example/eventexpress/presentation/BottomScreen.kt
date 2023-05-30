package com.example.eventexpress.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import com.example.eventexpress.R
import com.example.eventexpress.presentation.sign_in.GoogleAuthUIClient
import com.example.eventexpress.ui.theme.tiltFont

@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
fun BottomScreen(mainViewModel: MainViewModel, googleAuthUIClient: GoogleAuthUIClient){
    val config = LocalConfiguration.current
    val context = LocalContext.current
    var ht by remember { mutableStateOf(config.screenHeightDp+100) }
    var target by remember { mutableStateOf(false) }
    val padding by animateDpAsState(targetValue = ht.dp, finishedListener = {target = !target})
    ht = if(!mainViewModel.expandInfoCard){
        config.screenHeightDp+100
    } else {
        0
    }
    Box(modifier = Modifier
        .offset(y = padding)
        .zIndex(3f)
        .background(Color(0, 0, 0, 218))
        .fillMaxSize()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 50.dp)) {
            AnimatedVisibility(visible = target) {
                Image(
                    painter = rememberAsyncImagePainter(model = mainViewModel.state.currentEvent.coverImage),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                )
            }
            AnimatedVisibility(visible = target) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())) {
                    Text(
                        text = mainViewModel.state.currentEvent.name,
                        modifier = Modifier
                            .zIndex(2f)
                            .basicMarquee()
                            .padding(25.dp, 10.dp),
                        color = Color.White,
                        maxLines = 1,
                        fontFamily = tiltFont,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = mainViewModel.state.currentEvent.time.toDate().toString(),
                        modifier = Modifier
                            .zIndex(2f)
                            .padding(25.dp, 10.dp),
                        color = Color(255, 255, 255, 200),
                        fontFamily = tiltFont,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Thin
                    )
                    Text(
                        buildAnnotatedString {
                            withStyle(SpanStyle(color = Color.White)){
                                append("Location and Venue : ")
                            }
                            append( mainViewModel.state.currentEvent.location+"\n"+mainViewModel.state.currentEvent.venue)
                        },
                        modifier = Modifier
                            .zIndex(2f)
                            .padding(25.dp, 10.dp),
                        color = Color(255, 255, 255, 200),
                        fontFamily = tiltFont,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Thin
                    )
                    Text(
                        buildAnnotatedString {
                            withStyle(SpanStyle(color = Color.White)){
                                append("Categories : ")
                            }
                            append(mainViewModel.state.currentEvent.categories.joinToString(", "))
                        },
                        modifier = Modifier
                            .zIndex(2f)
                            .padding(25.dp, 10.dp),
                        color = Color(255, 255, 255, 200),
                        fontFamily = tiltFont,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Thin
                    )
                    Text(
                        buildAnnotatedString {
                            withStyle(SpanStyle(color = Color.White)){
                                append("Description : \n")
                            }
                            append(mainViewModel.state.currentEvent.description)
                        },
                        modifier = Modifier
                            .zIndex(2f)
                            .padding(25.dp, 13.dp),
                        color = Color(255, 255, 255, 200),
                        fontFamily = tiltFont,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Thin
                    )
                    Text(
                        buildAnnotatedString {
                                             withStyle(SpanStyle(color = Color.White)){
                                                 append("Register by : ")
                                             }
                            append(mainViewModel.state.currentEvent.registrationDeadlines.toDate().toString())
                        },
                        modifier = Modifier
                            .zIndex(2f)
                            .padding(25.dp, 13.dp),
                        color = Color(255, 255, 255, 200),
                        fontFamily = tiltFont,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Thin
                    )
                    Text(
                        buildAnnotatedString {
                            withStyle(SpanStyle(color = Color.White)){
                                append("Organized by : ")
                            }
                            append(mainViewModel.state.currentEvent.organizers.joinToString(", "))
                        },
                        modifier = Modifier
                            .zIndex(2f)
                            .padding(25.dp, 13.dp),
                        color = Color(255, 255, 255, 200),
                        fontFamily = tiltFont,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Thin
                    )
                    Spacer(modifier = Modifier.height(200.dp))
                }
            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .align(Alignment.BottomCenter)
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Transparent,
                        Color(0, 0, 0, 211),
                        Color.Black
                    )
                )
            )) {
            val cs1 by animateColorAsState(targetValue = mainViewModel.color1)
            val cs2 by animateColorAsState(targetValue = mainViewModel.color2)

            Row(Modifier.fillMaxWidth().align(Alignment.BottomCenter), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.fav_icon), contentDescription = "", tint = cs1, modifier = Modifier
                    .padding(start = 0.dp, bottom = 60.dp)
                    .size(65.dp)
                    .clip(CircleShape)
                    .background(cs2)
                    .padding(7.dp)
                    .clickable {
                        if(googleAuthUIClient.getSignedInUser()==null){
                            Toast.makeText(context, "Login required", Toast.LENGTH_SHORT).show()
                        } else {
                            if (mainViewModel.state.userData.userFavourites.contains(mainViewModel.state.currentEvent.id)) {
                                mainViewModel.changeLike(false)
                                Log.d("likestate", "contains")
                                mainViewModel.color1 = Color.White
                                mainViewModel.color2 = mainViewModel.transiColor
                            } else {
                                mainViewModel.changeLike(true)
                                Log.d("likestate", "did not contain but now does")
                                mainViewModel.color1 = mainViewModel.brightColor
                                mainViewModel.color2 = Color.White
                            }
                        }
                    })
                Text(text = "REGISTER",
                    fontFamily = tiltFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier
                    .padding(end = 0.dp, bottom = 60.dp)
                    .height(65.dp)
                    .fillMaxWidth(0.8f)
                    .clip(RoundedCornerShape(50))
                    .background(mainViewModel.brightColor)
                    .padding(11.dp)
                        .clickable {

                        })
            }
        }
    }
}














