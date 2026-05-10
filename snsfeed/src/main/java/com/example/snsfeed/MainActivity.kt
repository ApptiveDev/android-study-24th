package com.example.snsfeed

import android.R.drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.MaterialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
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
fun MainScreen() {
    var selectedTab by remember {mutableStateOf(0)}

    Scaffold(
        bottomBar = {
            NavigationBar{
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = {selectedTab = 0},
                    icon = {Icon(Icons.Default.Home, contentDescription = null)},
                    label = {Text("홈")}
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = {selectedTab = 1},
                    icon = { Icon(Icons.Default.Search, contentDescription = null) },
                    label = { Text("검색") }
                )

                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = {selectedTab = 2},
                    icon = { Icon(Icons.Default.MailOutline, contentDescription = null) },
                    label = { Text("메시지") }
                )

                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = {selectedTab = 3},
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("마이") }
                )
            }
        }
    ) {
        innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                0 -> SnsFeedScreen()
                1 -> SearchScreen()
                2 -> MessageScreen()
                3 -> ProfileScreen()
            }
        }
    }
}

@Composable
fun SnsFeedScreen() {
    //instance of story data class
    val stories = remember {
        listOf(
            StoryData("Your Story", R.drawable.hellokitty, isMyStory = true),
            StoryData("user1", R.drawable.profile),
            StoryData("user2", R.drawable.profile),
            StoryData("user3", R.drawable.profile),
            StoryData("user4", R.drawable.profile),
            StoryData("user5", R.drawable.profile),
        )
    }

    //instance of post data class
    val posts = listOf(
        PostData(
            userName = "travel",
            userProfileImage = R.drawable.profilepic,
            postImage = R.drawable.gwangalli,
            likeCount = 123,
            caption = "광안리",
            timeAgo = "2 hours ago"
        ),
        PostData(
            userName = "travel2",
            userProfileImage = R.drawable.profilepic,
            postImage = R.drawable.gwangalli,
            likeCount = 123,
            caption = "광안리",
            timeAgo = "2 hours ago"
        ),
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item {
            //stories bar
            StoriesBar(stories = stories)
            //divider
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.LightGray)
            )
        }
        items(posts) { post ->
            PostCard(post = post)
        }
    }
}

@Composable
fun PostCard(post: PostData) {
    Column(modifier = Modifier.fillMaxWidth()) {
        PostHeader(post)
        PostImage(post)
        PostButtons()
        PostContent(post)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun PostHeader(post: PostData) {
    //post section
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
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
            modifier = Modifier.weight(1f),
        )

        //see more icon
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "More Options",
            modifier = Modifier.size(24.dp)
        )
    }
}
@Composable
fun PostImage(post: PostData) {    //post image
    Image(
        painter = painterResource(id = post.postImage),
        contentDescription = "Post Image",
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(Color.LightGray),
        contentScale = ContentScale.Crop
    )
}
@Composable
fun PostButtons() {
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
}
@Composable
fun PostContent(post: PostData) {
    //likes text
    Text(
        text = "${post.likeCount} likes",
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
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
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
    )
}

@Composable
fun StoriesBar(stories: List<StoryData>) {
    LazyRow(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp, horizontal = 8.dp)
    ) {
        items(stories){ story ->
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

@Composable
fun SearchScreen() {
    var keyword by remember { mutableStateOf("") }
    //val categories = listOf("food", "music", "study", "travel", "technology")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //검색창
            TextField(
                value = keyword,
                onValueChange = { keyword = it },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(8.dp)),
            )
        }

        if (keyword.isEmpty()) {
            //before search
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Search, contentDescription = null,  tint = Color.LightGray)
                    Text("검색어를 입력해주세요", color = Color.Gray)
                }
            }
        } else {
            //검색 결과
            LazyColumn {
                items(5) { index ->
                    Text(
                        text = "'$keyword'에 대한 결과 $index",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}
@Composable
fun CategoryCard(category: String) {
    Box(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Transparent)
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
    ) {
        Text(
            text = category,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun MessageScreen() {
    Text(text = "Message Screen")
}

//profile data class
data class ProfileData(
    val accName: String,
    val accId: String
)
@Composable
fun ProfileScreen() {
    //Text(text = "Profile Screen")
    val AccName = "Hello Kitty"
    val AccId = "hello_kitty"

    LazyColumn(modifier = Modifier.fillMaxSize()
    ) {
        item {ProfileTopBar(accId = AccId)}
        item {ProfileInfo(AccName = AccName)}
        item {ProfileActionButtons()}
        item {ProfileTapBar()}
    }
}

@Composable
fun ProfileTopBar(accId: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(28.dp))
        Spacer(modifier = Modifier.width(130.dp))
        Text(
            text = accId,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.width(130.dp))
        Icon(imageVector = Icons.Default.Menu, contentDescription = null, modifier = Modifier.size(28.dp))
    }
}

@Composable
fun ProfileInfo(AccName: String) {
    //Text(text = "Profile Information")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //profile image area
        Box {
            Image(
                painter = painterResource(id = R.drawable.hellokitty),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(80.dp).clip(CircleShape)
                    .border(
                        width = 2.dp,
                        brush = SolidColor(Color.White),
                        shape = CircleShape
                    ),
                contentScale = ContentScale.Crop
            )
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Story",
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.BottomEnd)
                    .clip(CircleShape)
                    .background(Color(0xC13584), CircleShape)
                    .padding(2.dp),
                tint = Color.White
            )
        }
        //Spacer(modifier = Modifier.width(8.dp))
        //AccName, posts, followers, following
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = AccName,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileStats("0", "개시물")
                Spacer(modifier = Modifier.width(50.dp))
                ProfileStats("0", "팔로워")
                Spacer(modifier = Modifier.width(50.dp))
                ProfileStats("1", "팔로잉")
            }
        }
    }
}
@Composable
fun ProfileStats(count: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = count, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(text = label, fontSize = 13.sp)
    }
}

@Composable
fun ProfileActionButtons() {
    //Text(text = "Profile action buttons")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        //edit profile button
        Button(
            onClick = { },
            enabled = false,
            modifier = Modifier.weight(1f).height(36.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEFEFEF))
        ) {
            Text("프로필 편집", color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }

        //share profile button
        Button(
            onClick = { },
            enabled = false,
            modifier = Modifier.weight(1f).height(36.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEFEFEF))
        ) {
            Text("프로필 공유", color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }

        //follower suggestion button
        IconButton(
            onClick = { },
            enabled = false,
            modifier = Modifier.size(36.dp).background(Color(0xFFEFEFEF), RoundedCornerShape(8.dp))
        ) {
            Icon(imageVector = Icons.Default.Person, contentDescription = null, modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun ProfileTapBar() {
    //Text(text = "Profile Tab Bar")

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf(Icons.Default.List, Icons.Default.AccountBox)

    //Tabs container
    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.White,
        contentColor = Color.Black,
    ) {
        tabs.forEachIndexed { index, icon ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { selectedTabIndex = index },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (selectedTabIndex == index) Color.Black else Color.Gray,
                        modifier = Modifier.size(26.dp)
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSNSFeed() {
    SnsFeedScreen()
}



