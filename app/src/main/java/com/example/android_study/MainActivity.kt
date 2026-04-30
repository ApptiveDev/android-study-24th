package com.example.android_study

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_study.ui.theme.AndroidstudyTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidstudyTheme() {
                    FeedUI()
            }
        }
    }
}

// 스토리 데이터 클래스
data class StoryData(
    val id: Int,
    val userName: String
)

// 게시물 데이터 클래스
data class PostData(
    val id: Int,
    val userName: String,
    val location: String,
    val timeAgo: String
)

@Composable
fun FeedUI(){
    val stories = listOf(
        StoryData(1, "jeong"),
        StoryData(2, "woo"),
        StoryData(3, "young"),
        StoryData(4, "bong")
    )

    val posts = listOf(
        PostData(1, "jeong", "Busan", "10m ago"),
        PostData(2, "woo", "Seoul", "2h ago"),
        PostData(3, "young", "Ulsan", "Yesterday"),
        PostData(4, "bong", "Busan", "3 days ago")
    )
    Scaffold(
        topBar = {//제일 위에 부분
            Row(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp,end = 16.dp, top = 40.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Row(verticalAlignment = Alignment.CenterVertically){
                    Icon(Icons.Default.Add, contentDescription = "Plus", modifier = Modifier
                        .clip(CircleShape)
                        .clickable { }
                        .size(28.dp))
                    Spacer(modifier = Modifier.width(16.dp))//위에 plus 해결하기
                    Text(text = "jwy", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Box(contentAlignment = Alignment.TopEnd){
                    Icon(Icons.Default.Email, contentDescription = "Messages", modifier = Modifier
                        .clip(CircleShape)
                        .clickable { }
                        .size(28.dp))
                    Box(
                        modifier = Modifier
                            .offset(x=4.dp,y=(-2).dp)
                            .size(16.dp)
                            .background(Color(0xFFFF5722), CircleShape),
                        contentAlignment = Alignment.Center
                    ){
                        Text(text = "2",color = Color.White,fontSize=9.sp , fontWeight = FontWeight.Bold,modifier = Modifier.offset(y = (-5).dp))
                    }
                }
            }
        },
        bottomBar = {//아래 홈,알림,좋아요,돋보기,프로필 부분
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ){
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = {
                        Icon(Icons.Default.Home, contentDescription = "Home",tint = Color(0xFFFF5722))
                    }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Icon(Icons.Default.Notifications, contentDescription = "Notifications") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Icon(Icons.Default.FavoriteBorder, contentDescription = "Activity") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Profile") }
                )
            }
        }
    ){ paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            item {Spacer(modifier = Modifier.height(16.dp))}

            item {//스토리 부분
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ){
                    item {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(//내스토리
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable { }
                                    .size(65.dp)
                                    .background(Color(0xFF1E1E1E), CircleShape)
                                    .border(2.dp, Color.LightGray, CircleShape),
                                contentAlignment = Alignment.Center
                            ){
                                Icon(Icons.Default.Add, contentDescription = "Add Story", tint = Color.White )
                            }
                            Spacer(modifier=Modifier.height(8.dp))
                            Text("Add Story",fontSize=12.sp,color=Color.DarkGray)
                        }
                    }
                    //다른사람들 스토리
                    items(stories.size){index ->
                        val story = stories[index]
                        Column(horizontalAlignment = Alignment.CenterHorizontally){
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable { }
                                    .size(65.dp),
                                contentAlignment = Alignment.Center
                            ){
                                val stroke = Stroke(
                                    width = 4f,
                                    pathEffect = PathEffect.dashPathEffect(
                                        floatArrayOf(15f, 10f),
                                        0f
                                    )
                                )
                                Canvas(modifier=Modifier.matchParentSize()){
                                    drawCircle(color = Color(0xFFFF5722),style=stroke)
                                }
                                Box(
                                    modifier= Modifier
                                        .size(55.dp)
                                        .clip(CircleShape)
                                        .background(Color.Gray)
                                )
                            }
                            Spacer(modifier=Modifier.height(8.dp))
                            Text(story.userName,fontSize=12.sp,color=Color.DarkGray)
                        }
                    }
                }
            }
            item {Spacer(modifier=Modifier.height(24.dp))}
            item {//recent, following, trendy 바
                Box(
                    modifier= Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .height(50.dp)
                        .background(Color(0xFFFF5722),RoundedCornerShape(25.dp))
                ){
                    Row(
                        modifier= Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { /* 클릭 시 동작할 코드 */ }
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text("Recent", color = Color.White, fontSize = 14.sp)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { }
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text("Following", color = Color.White, fontSize = 14.sp)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp)) // 클릭 효과가 버튼 모양에 맞게
                                .clickable { /* 클릭 시 동작할 코드 */ }
                                .background(Color.White, RoundedCornerShape(20.dp))
                                .padding(horizontal = 20.dp, vertical = 8.dp)
                        ) {
                            Text(
                                "Trendy",
                                color = Color(0xFFFF5722),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
            item {Spacer(modifier=Modifier.height(24.dp))}
            //게시물 부분
            items(posts.size){index ->
                val post = posts[index]
                Column(modifier = Modifier.padding(bottom=16.dp)){
                    Card(
                        modifier= Modifier
                            .fillMaxWidth()
                            .padding(horizontal =16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(16.dp)
                    ){
                        Column{
                            Row(//게시물 위 프로필, 지역, 시간 등 표시
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Box(
                                    //게시한 사람 프로필
                                    modifier= Modifier
                                        .clip(CircleShape)
                                        .clickable { }
                                        .size(40.dp)
                                        .border(1.dp,Color(0xFFFF5722),CircleShape)
                                        .padding(2.dp)
                                        .clip(CircleShape)
                                        .background(Color.Gray)

                                ){
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Profile Picture",
                                        tint = Color.White,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Spacer(modifier=Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)){
                                    Text(post.userName, fontWeight = FontWeight.Bold,fontSize=14.sp)
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text("At ${post.location}, ", color = Color.Gray, fontSize = 12.sp)
                                        Text(post.timeAgo, color = Color.LightGray, fontSize = 12.sp)
                                    }
                                }
                                Icon(Icons.Default.Star, contentDescription="Bookmark", tint=Color.Gray,modifier= Modifier
                                    .clip(CircleShape)
                                    .clickable { }
                                )
                            }
                        }
                        Box(//사진,영상 부분
                            modifier= Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .background(Color(0xFFFFCDD2))
                        ){
                            Icon(
                                Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint = Color.White,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable { }
                                    .align(Alignment.Center)
                                    .size(60.dp)
                            )

                        }
                        Row(//하단 좋아요, ,댓글, 메시지부분
                            modifier= Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp,vertical =12.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ){
                            Icon(Icons.Default.Favorite,contentDescription="Like",tint = Color.Gray,modifier=Modifier.clip(CircleShape).clickable { })
                            Icon(Icons.Default.Search,contentDescription="Comment",tint = Color.Gray,modifier=Modifier.clip(CircleShape).clickable { })
                            Icon(Icons.Default.Send,contentDescription="Share",tint = Color.Gray,modifier=Modifier.clip(CircleShape).clickable { })

                            Spacer(modifier = Modifier.weight(1f))

                            Icon(Icons.Default.MoreVert,contentDescription="More",tint = Color.Gray,modifier=Modifier.clip(CircleShape).clickable { })
                        }
                    }
                }
            }
            item {Spacer(modifier=Modifier.height(16.dp))}
        }
    }

}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidstudyTheme {
        FeedUI()
    }
}