package com.example.android_study

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.android_study.ui.theme.AndroidstudyTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidstudyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class Post(
    var id : Int,
    var userName : String,
    var title : String,
    var mainImage : Int,
    var subImages : List<Int>
)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val dummy = listOf(
        Post(1, "player1", "bed", R.drawable.bed, listOf(R.drawable.player1, R.drawable.sub1,R.drawable.sub2, R.drawable.sub3, R.drawable.sub4)),
        Post(1, "player2", "clover", R.drawable.clover, listOf(R.drawable.player2, R.drawable.sub1,R.drawable.sub2, R.drawable.sub3, R.drawable.sub4)),
        Post(1, "player3", "flower", R.drawable.flower, listOf(R.drawable.player3, R.drawable.sub1,R.drawable.sub2, R.drawable.sub3, R.drawable.sub4)),
        Post(1, "player4", "sea", R.drawable.sea, listOf(R.drawable.player4, R.drawable.sub1,R.drawable.sub2, R.drawable.sub3, R.drawable.sub4))

    )
    var selected by remember { mutableStateOf<Post?>(null) }
    if(selected == null){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .verticalScroll(rememberScrollState())
        ){
            //배너
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(id = R.drawable.apple),
                    contentDescription = "Banner",
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            compositingStrategy = androidx.compose.ui.graphics.CompositingStrategy.Offscreen
                        }
                        .drawWithContent{
                            val gradient = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black,
                                    Color.Black.copy(alpha = 0.5f),
                                    Color.Black.copy(alpha = 0.3f),
                                    Color.Transparent
                                )
                            )
                            drawContent()
                            drawRect(
                                brush = gradient,
                                blendMode = BlendMode.DstIn
                            )
                        },
                    contentScale = ContentScale.Crop
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(text = "Find your own taste", color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            //중간 글
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text( "Popular", color = Color.White)
                Text("see All >", color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))
            //두 줄로 게시물 보여주기
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Column(
                    modifier = Modifier.weight(1f),
                ){
                    dummy.filterIndexed { index,  _ -> index % 2 == 1}.forEach {post ->
                        Column (
                            modifier = Modifier.clickable { selected = post }
                        ){
                            Spacer(modifier = Modifier.height(5.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 1.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Text(post.title, color = Color.Gray)
                            }
                            Image(
                                painter = painterResource(id = post.mainImage),
                                contentDescription = post.title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.FillWidth
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 1.dp),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Text(post.userName, color = Color.DarkGray)
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy((16.dp))
                ){

                    dummy.filterIndexed { index,  _ -> index % 2 == 0}.forEach {post ->
                        Column (
                            modifier = Modifier.clickable { selected = post }
                        ){
                            Spacer(modifier = Modifier.height(5.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 1.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Text(post.title, color = Color.Gray)
                            }
                            Image(
                                painter = painterResource(id = post.mainImage),
                                contentDescription = post.title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.FillWidth
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 1.dp),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Text(post.userName, color = Color.DarkGray)
                            }
                        }
                    }
                }
            }

        }
    }
    //계정 상세
    else{
        val post = selected!!
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ){
            Image(
                painter = painterResource(id = post.subImages[0]),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background((Color.Black.copy(alpha = 0.4f)))
            )
            IconButton(
                onClick = { selected = null },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ){
                Text("X", color = Color.White, fontSize = 24.sp)
            }

            Column (
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
                    .width(140.dp)
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Spacer(modifier = Modifier.height(40.dp))

                //사진들 옆에 나오는 거
                post.subImages.drop(1).forEach { imageRes ->
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = "sub image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            Text(
                text = "@${post.userName}",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 24.dp, bottom = 24.dp)
            )
        }
    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidstudyTheme {
        Greeting("Android")
    }
}