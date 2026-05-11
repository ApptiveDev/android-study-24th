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
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.runtime.setValue


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                App()
            }
        }
    }}

@Composable
fun App() {

    var currentScreen by remember {
        mutableStateOf("home")
    }

    when (currentScreen) {

        "home" -> MainScreen(
            // The lambda is created inside app so it has access to the variables inside app
            // This is called closure in programming
            onScreenChange = {
                currentScreen = it
            }
        )

        "search" -> SearchScreen(
            onScreenChange = {
                currentScreen = it
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onScreenChange: (String) -> Unit
) {
    val posts = remember { fakePosts() }
    val stories = remember { fakeStories() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AppTopBar() },
        bottomBar = {
            AppBottomBar(
                onScreenChange = onScreenChange
            )
        }    ) {
        paddingValues ->
           LazyColumn(
               modifier = Modifier.fillMaxSize().padding(paddingValues)
           ){
               item {
                   LazyRow( // Horizontal Scroll
                   horizontalArrangement = Arrangement.Start
               ) {
                   items(stories) {
                       story -> StoryRing(story)
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
fun AppBottomBar(
    onScreenChange: (String) -> Unit
) {

    BottomAppBar(
        modifier = Modifier.navigationBarsPadding()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            BottomBarIcon(
                Icons.Default.Home,
                "Home",
                onClick = {
                    onScreenChange("home")
                }
            )

            BottomBarIcon(
                Icons.Default.Search,
                "Search",
                onClick = {
                    onScreenChange("search")
                }
            )

            BottomBarIcon(
                Icons.Default.PlayArrow,
                "Loops",
                onClick = {}
            )

            BottomBarIcon(
                Icons.Default.Person,
                "Profile",
                onClick = {}
            )
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
fun BottomBarIcon(icon: ImageVector, desc: String, onClick: () -> Unit) {
    IconButton(onClick =  onClick) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onScreenChange: (String) -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),

        bottomBar = {
            AppBottomBar(
                onScreenChange = onScreenChange
            )
        }

    ) { paddingValues ->

        SearchBar(
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }
    TextField(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        value = name,
        onValueChange = { name = it },
        label = { Text("Search") },
        placeholder = {
            Text("Search")
        },

        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },

        singleLine = true,

        shape = RoundedCornerShape(24.dp),

        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF2F2F2),
            unfocusedContainerColor = Color(0xFFF2F2F2),

            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )

    )
}



fun fakePosts() = listOf(
    PostData(R.drawable.post_2, "Switzerland ❤️"),
    PostData(R.drawable.post_3, "Football ❤️")
)

fun fakeStories(): List<Int> = listOf(
    R.drawable.story1,
    R.drawable.story2,
    R.drawable.story3,
    R.drawable.story3,
    R.drawable.story3,
    R.drawable.story3,
    R.drawable.story3,
    R.drawable.story3,

)
data class PostData (
    @DrawableRes val imageRes: Int,
    val caption: String
)
