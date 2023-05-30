package com.example.eventexpress.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.example.eventexpress.R
import com.example.eventexpress.presentation.sign_in.GoogleAuthUIClient
import com.example.eventexpress.ui.theme.tiltFont
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventCard(it:Int, mainViewModel: MainViewModel, googleAuthUIClient: GoogleAuthUIClient){
    val scope = rememberCoroutineScope()
    Surface(
        modifier = Modifier
            .padding(0.dp, 5.dp)
            .fillMaxWidth(0.95f)
            .height(250.dp),
        shape = RoundedCornerShape(10.dp),
        color = mainViewModel.transiColor
    ) {
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
        Row(modifier = Modifier
            .clickable {
                mainViewModel.state.currentEvent =
                    mainViewModel.state.tempEventsList[it]
                mainViewModel.setupColors()
                mainViewModel.expandInfoCard = true
            }
            .fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Column(modifier = Modifier
                .weight(1f)
                .width(250.dp)) {
                Text(
                    text = mainViewModel.state.tempEventsList[it].name,
                    modifier = Modifier
                        .zIndex(2f)
                        .padding(top = 15.dp, start = 15.dp),
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    fontFamily = tiltFont,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = mainViewModel.state.tempEventsList[it].time.toDate().toString(),
                    modifier = Modifier
                        .zIndex(2f)
                        .padding(15.dp, 5.dp),
                    color = Color.White,
                    fontFamily = tiltFont,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Thin
                )
                Text(
                    text = mainViewModel.state.tempEventsList[it].location,
                    modifier = Modifier
                        .zIndex(2f)
                        .padding(15.dp, 3.dp),
                    color = Color.White,
                    fontFamily = tiltFont,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Thin
                )
            }
            Column(Modifier.fillMaxHeight()) {
                Image(
                    painter = rememberAsyncImagePainter(model = mainViewModel.state.tempEventsList[it].coverImage),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(top = 10.dp, end = 10.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .height(120.dp)
                        .width(170.dp)
                        .zIndex(1f)
                )
                val context = LocalContext.current
                var color by remember { mutableStateOf(
                    if (mainViewModel.state.userData.userFavourites.contains(mainViewModel.state.tempEventsList[it].id)) {
                        mainViewModel.brightColor
                    } else {
                        Color.Gray
                    }
                ) }
                IconButton(
                    onClick = {
                        scope.launch {
                            if(googleAuthUIClient.getSignedInUser()==null){
                                Toast.makeText(context, "Login required", Toast.LENGTH_SHORT).show()
                            } else {
                                mainViewModel.state.currentEvent = mainViewModel.state.tempEventsList[it]
                                if (mainViewModel.state.userData.userFavourites.contains(mainViewModel.state.currentEvent.id)) {
//                                    Toast.makeText(context, "Like removed", Toast.LENGTH_SHORT).show()
                                    mainViewModel.changeLike(false)
                                    color = Color.Gray
                                } else {
//                                    Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show()
                                    mainViewModel.changeLike(true)
                                    color = mainViewModel.brightColor
                                }
                            }
                        }
                },
                modifier = Modifier
                    .align(End)
                    .padding(top = 40.dp, end = 20.dp)
                    .size(40.dp)
                    .drawBehind {
                        drawCircle(Color.White)
                    }
                    .padding(5.dp)) {
                    Icon(
                    painter = painterResource(id = R.drawable.fav_icon),
                    contentDescription = "",
                    tint = color
                )
                }
            }
        }
    }
}











