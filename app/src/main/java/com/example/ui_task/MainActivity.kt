package com.example.ui_task

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.benchmark.traceprocessor.Row
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui_task.ui.theme.UI_TaskTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.collections.listOf
import androidx.compose.foundation.lazy.items

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UI_TaskTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    "Loop",
                                    color = Color.Magenta,
                                    fontSize = 30.sp
                                )
                            },
                            actions = {
                                IconButton(onClick = { }) {
                                    Icon (
                                        imageVector = Icons.AutoMirrored.Filled.Chat,
                                        contentDescription = "Chat",
                                    )
                                }

                                IconButton(onClick = {}) {
                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = "Favorite"
                                    )
                                }
                            }

                        )
                    },
                    bottomBar = {
                        BottomAppBar(
                            modifier = Modifier.navigationBarsPadding()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                IconButton(onClick = { }) {
                                    Icon(Icons.Default.Home, contentDescription = "Home")
                                }
                                IconButton(onClick = { }) {
                                    Icon(Icons.Default.Search, contentDescription = "Search")
                                }
                                IconButton(onClick = { }) {
                                    Icon(Icons.Default.PlayArrow, contentDescription = "Loops")
                                }
                                IconButton(onClick = { }) {
                                    Icon(Icons.Default.Person, contentDescription = "Profile")
                                }
                            }
                        }
                    }
                ) { paddingValues ->

                    val posts: List<PostData> = listOf(
                        PostData(R.drawable.post_2, "Switzerland ❤️"),
                        PostData(R.drawable.post_3, "Football ❤️")
                    )

                    LazyColumn(
                        modifier = Modifier
                            .padding(paddingValues)   // 🔥 VERY IMPORTANT
                            .fillMaxSize()
                    ) {
                        items(posts) { post ->
                            UI_Design(
                                modifier = Modifier.fillMaxWidth(),
                                data = post
                            )
                        }
                    }
                }
            }
            }}}


@Composable
fun UI_Design(modifier: Modifier = Modifier, data: PostData) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(400.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, Color.Gray, RoundedCornerShape(16.dp))
        ) {
            Image(
                painter = painterResource(data.imageRes),
                contentDescription = "Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Like"
                )
            }

            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.ChatBubbleOutline,
                    contentDescription = "Comment"
                )
            }

            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send"
                )
            }
        }
        Text(data.caption)
    }
}
data class PostData (
    @DrawableRes val imageRes: Int,
    val caption: String
)

