package com.example.android_study

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_study.ui.theme.AndroidstudyTheme

data class PostData(
    val userName: String,
    val location: String,
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val post = PostData(
                        userName = "charles_jh04",
                        location = "Pusan National University",
                        caption = "스터디 과제 중!",
                        timeAgo = "3시간 전",
                        likes = 128
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        FeedCard(post)
                    }
                }
            }
        }
    }
}

@Composable
fun FeedCard(post: PostData) {
    Column(modifier = Modifier.fillMaxWidth()) {

        // 프로필 행
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 아바타
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "avatar",
                modifier = Modifier.size(38.dp),
                tint = Color.Gray
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color(0xFFE0E0E0))
        )

        // 기능 버튼 행
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "좋아요",
                modifier = Modifier.size(26.dp)
            )
            Spacer(modifier = Modifier.width(14.dp))
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "댓글",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(14.dp))
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "공유",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "저장",
                modifier = Modifier.size(24.dp)
            )
        }

        // 좋아요 수
        Text(
            text = "좋아요 ${post.likes}개",
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
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidstudyTheme {
        FeedCard(
            PostData(
                userName = "charlie_jh04",
                location = "Pusan National University",
                caption = "스터디 과제 중!",
                timeAgo = "3시간 전",
                likes = 128
            )
        )
    }
}

