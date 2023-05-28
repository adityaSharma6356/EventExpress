package com.example.eventexpress.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import com.example.eventexpress.R
import com.example.eventexpress.ui.theme.tiltFont

@Composable
fun EventCard(it:Int, mainViewModel: MainViewModel){
    Surface(
        modifier = Modifier
            .padding(0.dp, 5.dp)
            .fillMaxWidth(0.9f)
            .height(250.dp),
        shape = RoundedCornerShape(25.dp),
        color = mainViewModel.transiColor
    ) {
        var expand by remember {
            mutableStateOf(false)
        }
        Image(
            painter = rememberAsyncImagePainter(model = mainViewModel.state.tempEventsList[it].coverImage),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f)
                .blur(50.dp)
        )
        Spacer(modifier = Modifier
            .fillMaxSize()
            .background(Color(255, 255, 255, 32)))
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                AnimatedVisibility(visible = expand) {
                    Column(modifier = Modifier
                        .weight(1f)
                        .width(250.dp)
                        .clickable {
                            mainViewModel.state.currentEvent = mainViewModel.state.tempEventsList[it]
                            mainViewModel.setupColors()
                            mainViewModel.expandInfoCard = true
                        }) {
                        Text(
                            text = mainViewModel.state.tempEventsList[it].name,
                            modifier = Modifier
                                .zIndex(2f)
                                .padding(15.dp),
                            color = Color.White,
                            fontFamily = tiltFont,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = mainViewModel.state.tempEventsList[it].date +"  "+ mainViewModel.state.tempEventsList[it].timeHour+":"+ mainViewModel.state.tempEventsList[it].timeMinute,
                            modifier = Modifier
                                .zIndex(2f)
                                .padding(15.dp, 5.dp),
                            color = Color.White,
                            fontFamily = tiltFont,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Light
                        )
                        Text(
                            text = mainViewModel.state.tempEventsList[it].location,
                            modifier = Modifier
                                .zIndex(2f)
                                .padding(15.dp, 3.dp),
                            color = Color.White,
                            fontFamily = tiltFont,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Light
                        )
                    }
                }
                Image(
                    painter = rememberAsyncImagePainter(model = mainViewModel.state.tempEventsList[it].coverImage),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(25.dp))
                        .padding(0.dp)
                        .height(250.dp)
                        .fillMaxWidth()
                        .zIndex(1f)
                        .clickable {
                            expand = !expand
                        }
                )
            }
        }
    }
}











