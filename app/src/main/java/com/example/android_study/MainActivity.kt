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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
val PointColor = Color(0xFFFF5722)
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
    var selectedTab by remember {mutableStateOf(0)}
    val stories = remember{
        listOf(
            StoryData(1, "jeong"),
            StoryData(2, "woo"),
            StoryData(3, "young"),
            StoryData(4, "bong")
        )
    }

    val posts = remember{
        listOf(
            PostData(1, "jeong", "Busan", "10m ago"),
            PostData(2, "woo", "Seoul", "2h ago"),
            PostData(3, "young", "Ulsan", "Yesterday"),
            PostData(4, "bong", "Busan", "3 days ago")
        )
    }

    Scaffold(
        topBar = {//화면 젤 위에 부분
            Row(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 40.dp, bottom = 10.dp),
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
                            .offset(x = 4.dp, y = (-2).dp)
                            .size(16.dp)
                            .background(PointColor, CircleShape),
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
                //홈 버튼 부분
                NavigationBarItem(
                    selected = selectedTab ==0,
                    onClick = {selectedTab = 0},
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home")} ,
                    colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent
                            )
                )
                //좋아요탭 버튼
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = {selectedTab = 1},
                    icon = { Icon(Icons.Default.FavoriteBorder, contentDescription = "liked") },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent
                    )
                )
                //검색탭 버튼
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = {selectedTab = 2},
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") }
                )
                //계정탭 버튼
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = {selectedTab = 3},
                    icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Profile") }
                )
            }
        }
    ){ paddingValues ->
        Box(modifier=Modifier
            .fillMaxSize()
            .padding(paddingValues)){
            when(selectedTab){
              0 -> HomeScreen(stories = stories, posts = posts)
              1 -> LikedScreen()
              2 -> SearchScreen(posts = posts)
              3 -> ProfileScreen()
            }
        }
    }
}

@Composable
fun HomeScreen(stories : List<StoryData>, posts : List<PostData>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {//스토리 부분
            StorySection(stories=stories)
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        item { //recent, following, trendy 선택 탭
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(50.dp)
                    .background(PointColor, RoundedCornerShape(25.dp))
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text("Recent", color = Color.White, fontSize = 14.sp)
                    Text("Following", color = Color.White, fontSize = 14.sp)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable{}
                            .background(Color.White, RoundedCornerShape(20.dp))
                            .padding(horizontal = 20.dp, vertical = 8.dp)
                    ) {
                        Text("Trendy", color = PointColor, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
        //게시물 부분
        items(posts.size) { index ->
            val post = posts[index]
            PostItem(post=post)
        }
    }
}
@Composable
fun StorySection(stories: List<StoryData>){
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(vertical = 10.dp)
    ) {
        //본인 스토리 추가
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { }
                        .size(65.dp)
                        .background(Color(0xFF1E1E1E), CircleShape)
                        .border(2.dp, Color.LightGray, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Story", tint = Color.White)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("Add Story", fontSize = 12.sp, color = Color.DarkGray)
            }
        }
        //다른 사람 스토리
        items(stories.size) { index ->
            val story = stories[index]
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { }
                        .size(65.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val stroke = Stroke(
                        width = 4f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 10f), 0f)
                    )
                    Canvas(modifier = Modifier.matchParentSize()) {
                        drawCircle(color = PointColor, style = stroke)
                    }
                    Box(
                        modifier = Modifier
                            .size(55.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(story.userName, fontSize = 12.sp, color = Color.DarkGray)
            }
        }
    }
}

@Composable
fun PostItem(post : PostData){
    var liked by remember{ mutableStateOf(false)}
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            //게시물 위에 프로필이랑 이름 부분
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(40.dp)
                            .border(1.dp, PointColor, CircleShape)
                            .padding(2.dp)
                            .background(Color.Gray)
                    ) {
                        Icon(Icons.Default.Person, "profile", tint = Color.White, modifier = Modifier.fillMaxSize())
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(post.userName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("At ${post.location}, ${post.timeAgo}", color = Color.Gray, fontSize = 12.sp)
                    }
                    Icon(Icons.Default.Star, "bookmark", tint = Color.Gray)
                }
                //게시물 사진부분
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(Color(0xFFFFCDD2))
                ) {
                    Icon(Icons.Default.PlayArrow, "paly", tint = Color.White, modifier = Modifier.align(Alignment.Center).size(60.dp))
                }
                //좋아요등 게시물 아래부분
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(Icons.Default.Favorite, "like", tint = Color.Gray)
                    Icon(Icons.Default.Search, "search", tint = Color.Gray)
                    Icon(Icons.Default.Send, "send", tint = Color.Gray)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(Icons.Default.MoreVert, "option", tint = Color.Gray)
                }
            }
        }
    }
}
@Composable
fun LikedScreen(){


}
@Composable
fun SearchScreen(posts : List<PostData>){
    var name by remember{mutableStateOf("")}
    val filteredPosts = remember(name) {
        posts.filter { post -> post.userName == name }
    }
    LazyColumn(
        modifier=Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment=Alignment.CenterHorizontally,
    ){
        item{TextField(
            value = name,
            onValueChange = {name =it},
            label = {Text("이름 입력")},
        )}
        item{Spacer(modifier=Modifier.height(16.dp))}
        item{Text("입력한 검색어: $name")}
        item{Spacer(modifier=Modifier.height(16.dp))}
        items(filteredPosts.size) { index ->
            val post = filteredPosts[index]
            PostItem(post=post)
        }
    }
}
@Composable
fun ProfileScreen(){

}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidstudyTheme {
        FeedUI()
    }
}