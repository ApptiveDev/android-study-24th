package com.example.android_study

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                MainScreen()
            }
        }
    }}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val posts = remember { fakePosts() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {AppTopBar()},
        bottomBar = {AppBottomBar()}
    ) {
        paddingValues ->
           LazyColumn(
               modifier = Modifier.fillMaxSize().padding(paddingValues)
           ){
               item {
                   Row(
                   horizontalArrangement = Arrangement.Start
               ) {
                   fakeStories().forEach {
                       StoryRing(it)
                   }
               }}
               items(posts) {
                   post -> PostItem(post)
               }
           }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Loop",
                color = Color.Magenta,
                fontSize = 30.sp,
            )
        },
        actions = {
            TopBarIcon(Icons.AutoMirrored.Filled.Chat, "Chat")
            TopBarIcon(Icons.Default.Favorite, "Favorite")
        }
    )
}

@Composable
fun AppBottomBar() {
    BottomAppBar(
        modifier = Modifier.navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BottomBarIcon(Icons.Default.Home, "Home")
            BottomBarIcon(Icons.Default.Search, "Search")
            BottomBarIcon(Icons.Default.PlayArrow, "Loops")
            BottomBarIcon(Icons.Default.Person, "Profile")
        }
    }
}

@Composable
fun TopBarIcon(icon: ImageVector, desc: String) {
    IconButton(onClick = { }) {
        Icon(icon, contentDescription = desc)
    }
}

@Composable
fun BottomBarIcon(icon: ImageVector, desc: String) {
    IconButton(onClick = {}) {
        Icon(icon, contentDescription = desc)
    }
}
@Composable
fun PostItem(data: PostData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Box(
            modifier = Modifier
                .size(400.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, Color.Gray, RoundedCornerShape(16.dp))
        ) {
            Image(
                painter = painterResource(data.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Row {
            PostActionIcon(Icons.Default.FavoriteBorder, "Like")
            PostActionIcon(Icons.Outlined.ChatBubbleOutline, "Comment")
            PostActionIcon(Icons.AutoMirrored.Filled.Send, "Send")
        }

        Text(text = data.caption)
    }
}
@Composable
fun PostActionIcon(icon: ImageVector, desc: String) {
    IconButton(onClick = { }) {
        Icon(icon, contentDescription = desc)
    }
}

@Composable
fun StoryRing(
    imageRes: Int,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(80.dp)
    ) {
        // Gradient ring
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFf9ce34), Color(0xFFee2a7b), Color(0xFF6228d7))
                    )
                )
        )

        // White gap between ring and avatar
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(Color.White)
        )

        // Avatar image
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(65.dp)
                .clip(CircleShape)
        )
    }
}

fun fakePosts() = listOf(
    PostData(R.drawable.post_2, "Switzerland ❤️"),
    PostData(R.drawable.post_3, "Football ❤️")
)

fun fakeStories(): List<Int> = listOf(
    R.drawable.story1,
    R.drawable.story2,
    R.drawable.story3,
)
data class PostData (
    @DrawableRes val imageRes: Int,
    val caption: String
)
