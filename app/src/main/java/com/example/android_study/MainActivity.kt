package com.example.android_study

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_study.ui.theme.AndroidstudyTheme

data class PostData(
    val profileImage: Int,
    val userName: String,
    val location: String,
    val feedImage: Int,
    val caption: String,
    val timeAgo: String,
    val likes: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidstudyTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = "피드") },
                    label = { Text("피드") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.Search, contentDescription = "검색") },
                    label = { Text("검색") }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.Notifications, contentDescription = "알림") },
                    label = { Text("알림") }
                )
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = { Icon(Icons.Default.Person, contentDescription = "프로필") },
                    label = { Text("프로필") }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> HomeScreen()
                1 -> SearchScreen()
                2 -> NotiScreen()
                3 -> ProfileScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    val posts = listOf(
        PostData(
            profileImage = R.drawable.pnu,
            userName = "charles_jh04",
            location = "Pusan National University",
            feedImage = R.drawable.pnu,
            caption = "스터디 과제 중!",
            timeAgo = "3시간 전",
            likes = 128
        ),
        PostData(
            profileImage = R.drawable.bridge,
            userName = "apptive_study",
            location = "PBL2",
            feedImage = R.drawable.bridge,
            caption = "연습중",
            timeAgo = "어제",
            likes = 256
        ),
        PostData(
            profileImage = R.drawable.night,
            userName = "compose_dev",
            location = "Busan",
            feedImage = R.drawable.night,
            caption = "하단 바 기능을 추가하는 중",
            timeAgo = "2일 전",
            likes = 32
        )
    )

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(posts) { post ->
            FeedCard(post)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun FeedCard(post: PostData) {
    var liked by remember { mutableStateOf(false) }
    var starred by remember { mutableStateOf(false) }
    var showCommentDialog by remember { mutableStateOf(false) }
    var commentText by remember { mutableStateOf("") }
    var savedComment by remember { mutableStateOf("") }
    var showShareDialog by remember { mutableStateOf(false) }
    val likeCount = if (liked) post.likes + 1 else post.likes

    Column(modifier = Modifier.fillMaxWidth()) {

        // 프로필 행
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 아바타
            Image(
                painter = painterResource(id = post.profileImage),
                contentDescription = "프로필 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = post.userName,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Text(
                    text = post.location,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        // 피드 이미지
        Image(
            painter = painterResource(id = post.feedImage),
            contentDescription = "피드 이미지",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            liked = true
                        }
                    )
                }
        )

        // 기능 버튼 행
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = if (liked) "좋아요 취소" else "좋아요",
                modifier = Modifier
                    .size(26.dp)
                    .clickable { liked = !liked },
                tint = if (liked) Color.Red else Color.Black
            )
            Spacer(modifier = Modifier.width(14.dp))
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "댓글",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { showCommentDialog = true }
            )
            Spacer(modifier = Modifier.width(14.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "공유",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { showShareDialog = true }
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = if (starred) "저장 취소" else "저장",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { starred = !starred },
                tint = if (starred) Color(0xFFFFC107) else Color.Black
            )
        }

        // 좋아요 수
        Text(
            text = "좋아요 ${likeCount}개",
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 14.dp)
        )

        // 내용
        Row(modifier = Modifier.padding(horizontal = 14.dp, vertical = 2.dp)) {
            Text(
                text = post.userName,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = post.caption,
                fontSize = 14.sp
            )
        }

        // 시간
        Text(
            text = post.timeAgo,
            fontSize = 11.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp)
        )
    }

    if (showCommentDialog) {
        AlertDialog(
            onDismissRequest = { showCommentDialog = false },
            title = { Text("댓글") },
            text = {
                Column {
                    if (savedComment.isNotBlank()) {
                        Text("최근 댓글: $savedComment")
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    TextField(
                        value = commentText,
                        onValueChange = { commentText = it },
                        label = { Text("댓글 입력") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (commentText.isNotBlank()) {
                            savedComment = commentText
                            commentText = ""
                        }
                        showCommentDialog = false
                    }
                ) {
                    Text("등록")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCommentDialog = false }) {
                    Text("닫기")
                }
            }
        )
    }

    if (showShareDialog) {
        AlertDialog(
            onDismissRequest = { showShareDialog = false },
            title = { Text("공유") },
            text = { Text("게시물이 공유되었습니다.") },
            confirmButton = {
                TextButton(onClick = { showShareDialog = false }) {
                    Text("확인")
                }
            }
        )
    }
}

@Composable
fun SearchScreen() {
    var keyword by remember { mutableStateOf("") }
    val trimmedKeyword = keyword.trim()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("검색", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = keyword,
            onValueChange = { keyword = it },
            label = { Text("여기에 검색어를 입력하세요.") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            if (trimmedKeyword.isEmpty()) {
                "검색어를 입력하면 결과가 표시됩니다."
            } else {
                "\"$trimmedKeyword\" 검색 결과입니다."
            }
        )
    }
}

@Composable
fun NotiScreen() {
    var showDialog by remember { mutableStateOf(false) }
    var isRead by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("알림", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        Text(if (isRead) "새로운 알림이 없습니다." else "새로운 알림이 있습니다.")

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                isRead = true
                showDialog = true
            }
        ) {
            Text(if (isRead) "알림 다시 보기" else "알림 보기")
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("알림") },
            text = { Text("앱이 업데이트되었습니다.") },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("확인")
                }
            }
        )
    }
}

@Composable
fun ProfileScreen() {
    var profileName by remember { mutableStateOf("charles_jh04") }
    var inputName by remember { mutableStateOf(profileName) }
    var count by remember { mutableStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("내 프로필", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        Text("닉네임: $profileName")

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = inputName,
            onValueChange = { inputName = it },
            label = { Text("닉네임 입력") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (inputName.isNotBlank()) {
                    profileName = inputName.trim()
                }
            }
        ) {
            Text("이름 수정")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { count++ }) {
            Text("방문자 버튼")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { showDialog = true }) {
            Text("방문자 수: $count")
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("방문자 수: $count") },
                text = { Text("방문자 수를 초기화하시겠습니까?") },
                confirmButton = {
                    TextButton(onClick = { count = 0; showDialog = false }) {
                        Text("확인")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("취소")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidstudyTheme {
        MainScreen()
    }
}
