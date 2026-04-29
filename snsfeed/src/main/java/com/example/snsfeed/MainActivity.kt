package com.example.snsfeed

import android.R.drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SnsFeedScreen()
        }
    }
}

//Data class for story
data class StoryData(
    val userName: String,
    val userImage: Int,
    val isViewed: Boolean = false,
    val isMyStory: Boolean = false
)
//Data Class for post
data class PostData(
    val userName: String,
    val userProfileImage: Int,
    val postImage: Int,
    val likeCount: Int,
    val caption: String,
    val timeAgo: String
)

@Composable
fun SnsFeedScreen() {
    //instance of story data class
    val stories = listOf(
        StoryData("Your Story", R.drawable.hellokitty, isMyStory = true),
        StoryData("user1", R.drawable.profile),
        StoryData("user2", R.drawable.profile),
        StoryData("user3", R.drawable.profile),
        StoryData("user4", R.drawable.profile),
    )

    //instance of post data class
    val post = PostData(
        userName = "travel",
        userProfileImage = R.drawable.profilepic,
        postImage = R.drawable.gwangalli,
        likeCount = 123,
        caption = "광안리",
        timeAgo = "2 hours ago"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        //stories bar
        StoriesBar(stories = stories)
        //divider
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
        )

        //post section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            //profile image
            Image(
                painter = painterResource(id = post.userProfileImage),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Gray),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))

            //username
            Text(
                text = post.userName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            //see more icon
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More Options",
                modifier = Modifier.size(24.dp)
            )
        }

        //post image
        Image(
            painter = painterResource(id = post.postImage),
            contentDescription = "Post Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(Color.LightGray),
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //like button
            var isLiked by remember { mutableStateOf(false) }

            IconButton(onClick = { isLiked = !isLiked }) {
                Icon(
                    imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Like",
                    tint = if (isLiked) Color.Red else Color.Black,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            //comment icon
            Icon(
                painter = painterResource(id = drawable.ic_dialog_email),
                contentDescription = "Comment",
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            //share icon
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Share",
                modifier = Modifier.size(26.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            //bookmarks icon
            Icon(
                painter = painterResource(id = drawable.ic_menu_save),
                contentDescription = "Bookmark",
                modifier = Modifier.size(26.dp)
            )
        }

        //likes
        Text(
            text = "${post.likeCount} likes",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )

        //caption
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Text(
                text = post.userName,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = post.caption,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }

        //upload time
        Text(
            text = post.timeAgo,
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun StoriesBar(stories: List<StoryData>) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp, horizontal = 8.dp)
    ) {
        stories.forEach { story ->
            StoryItem(story = story)
        }
    }
}

//story items
@Composable
fun StoryItem(story: StoryData) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Box(contentAlignment = Alignment.BottomEnd) {
            //profile image
            Image(
                painter = painterResource(id = story.userImage),
                contentDescription = story.userName,
                modifier = Modifier
                    .size(70.dp)
                    .border(
                        width = 2.dp,
                        brush = if (story.isMyStory)
                            SolidColor(Color.Gray)
                         else
                            SolidColor(Color.Red),
                        shape = CircleShape
                    )
                    .padding(3.dp)

                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            //my story icon
            if (story.isMyStory) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Color.White) // Added white border effect for visibility
                        .padding(2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Story",
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .background(Color(0xC13584), CircleShape)
                            .padding(2.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = story.userName,
            fontSize = 12.sp,
            maxLines = 1,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSNSFeed() {
    SnsFeedScreen()
}