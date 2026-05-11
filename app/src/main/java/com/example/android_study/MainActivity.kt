package com.example.android_study

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_study.ui.theme.AndroidstudyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidstudyTheme {
                MainScreen()
            }
        }
    }
}

data class Post(
    val id: Int,
    val userName: String,
    val title: String,
    val mainImage: Int,
    val subImages: List<Int>
)

@Composable
fun MainScreen() {
    var selectedTab by remember { mutableStateOf(0) }

    val naviColor = NavigationBarItemDefaults.colors(
        selectedIconColor = Color.White,
        selectedTextColor = Color.White,
        unselectedIconColor = Color.LightGray,
        unselectedTextColor = Color.LightGray,
        indicatorColor = Color.LightGray.copy(0.4f)
    )

    Scaffold(
        bottomBar = {
            NavigationBar (
                containerColor = Color.Black
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Home") },
                    colors = naviColor
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
                    label = { Text("Friend") },
                    colors = naviColor
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("My") },
                    colors = naviColor
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)

        ) {
            when (selectedTab) {
                0 -> HomeScreen(onNavigateToPopular = { selectedTab = 1 })
                1 -> Popular()
                2 -> My()
            }
        }
    }
}

@Composable
fun HomeScreen(onNavigateToPopular: () -> Unit) {
    val dummy = listOf(
        Post(
            id = 1,
            userName = "player1",
            title = "bed",
            mainImage = R.drawable.bed,
            subImages = listOf(
                R.drawable.player1,
                R.drawable.sub1,
                R.drawable.sub2,
                R.drawable.sub3,
                R.drawable.sub4
            )
        ),
        Post(
            id = 2,
            userName = "player2",
            title = "clover",
            mainImage = R.drawable.clover,
            subImages = listOf(
                R.drawable.player2,
                R.drawable.sub1,
                R.drawable.sub2,
                R.drawable.sub3,
                R.drawable.sub4
            )
        ),
        Post(
            id = 3,
            userName = "player3",
            title = "flower",
            mainImage = R.drawable.flower,
            subImages = listOf(
                R.drawable.player3,
                R.drawable.sub1,
                R.drawable.sub2,
                R.drawable.sub3,
                R.drawable.sub4
            )
        ),
        Post(
            id = 4,
            userName = "player4",
            title = "sea",
            mainImage = R.drawable.sea,
            subImages = listOf(
                R.drawable.player4,
                R.drawable.sub1,
                R.drawable.sub2,
                R.drawable.sub3,
                R.drawable.sub4
            )
        )
    )

    var selectedPost by remember { mutableStateOf<Post?>(null) }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    if (selectedPost == null) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            verticalItemSpacing = 16.dp,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 100.dp)

        ) {
            item(span = StaggeredGridItemSpan.FullLine) {
                Box(
                    modifier = Modifier
                        .requiredWidth(screenWidth)
                        .height(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.apple),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                compositingStrategy =
                                    androidx.compose.ui.graphics.CompositingStrategy.Offscreen
                            }
                            .drawWithContent {
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
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.requiredWidth(screenWidth)
                    ) {
                        Text(
                            text = "Find your own taste",
                            color = Color.White
                        )
                    }
                }
            }

            item(span = StaggeredGridItemSpan.FullLine) {
                Column {
                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Popular",
                            color = Color.White
                        )
                        Text(
                            text = "see Friend >",
                            color = Color.White,
                            modifier = Modifier.clickable {
                                onNavigateToPopular()
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            items(dummy) { post ->
                PostItem(
                    post = post,
                    onClick = { selectedPost = post }
                )

            }
        }
    } else {
        DetailScreen(
            post = selectedPost!!,
            onBack = { selectedPost = null }
        )
    }
}

@Composable
fun PostItem(
    post: Post,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable { onClick() }
    ) {
        Spacer(modifier = Modifier.height(5.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 1.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = post.title,
                color = Color.Gray
            )
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
        ) {
            Text(
                text = post.userName,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
fun Popular() {
    val dummy = listOf(
        Post(
            id = 1,
            userName = "player1",
            title = "bed",
            mainImage = R.drawable.bed,
            subImages = listOf(
                R.drawable.player1,
                R.drawable.sub1,
                R.drawable.sub2,
                R.drawable.sub3,
                R.drawable.sub4
            )
        ),
        Post(
            id = 2,
            userName = "player2",
            title = "clover",
            mainImage = R.drawable.clover,
            subImages = listOf(
                R.drawable.player2,
                R.drawable.sub1,
                R.drawable.sub2,
                R.drawable.sub3,
                R.drawable.sub4
            )
        ),
        Post(
            id = 3,
            userName = "player3",
            title = "flower",
            mainImage = R.drawable.flower,
            subImages = listOf(
                R.drawable.player3,
                R.drawable.sub1,
                R.drawable.sub2,
                R.drawable.sub3,
                R.drawable.sub4
            )
        ),
        Post(
            id = 4,
            userName = "player4",
            title = "sea",
            mainImage = R.drawable.sea,
            subImages = listOf(
                R.drawable.player4,
                R.drawable.sub1,
                R.drawable.sub2,
                R.drawable.sub3,
                R.drawable.sub4
            )
        )
    )
    var selectedFriend by remember {  mutableStateOf<Post?>(null) }

    if(selectedFriend == null){
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(horizontal = 16.dp),
            verticalItemSpacing = 16.dp,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {

            items(dummy) { post ->
                PostItem(
                    post = post,
                    onClick = { selectedFriend = post }
                )
            }
        }
    } else {
        DetailScreen(
            post = selectedFriend!!,
            onBack = { selectedFriend = null }
        )

    }
}

@Composable
fun DetailScreen(
    post: Post,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = painterResource(id = post.subImages[0]),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )

        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Text(
                text = "X",
                color = Color.White,
                fontSize = 24.sp
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
                .width(140.dp)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            post.subImages.drop(1).forEach { imageRes ->
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "sub image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            width = 2.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(12.dp)
                        ),
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

@Composable
fun My() {
    val myData = Post(
        id = 0,
        userName = "me",
        title = "home",
        mainImage = R.drawable.my,
        subImages = listOf(
            R.drawable.my,
            R.drawable.rilakkuma,
            R.drawable.cat,
            R.drawable.nail,
            R.drawable.withcat,
            R.drawable.earphone

        )
    )

    var showDetail by remember { mutableStateOf(false) }
    if (showDetail ==  false) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)

        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.my),
                        contentDescription = "My",
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                compositingStrategy =
                                    androidx.compose.ui.graphics.CompositingStrategy.Offscreen
                            }
                            .drawWithContent {
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
                    ) {
                        Text(
                            text = "My Archive",
                            color = Color.White
                        )
                    }

                }
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(10.dp)

                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "내 정보",
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                    Divider(
                        modifier = Modifier.padding(horizontal = 2.dp),
                        color = Color.DarkGray,
                        thickness = 2.dp
                    )
                    Text(
                        text = "설정",
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                    Divider(
                        modifier = Modifier.padding(horizontal = 2.dp),
                        color = Color.DarkGray,
                        thickness = 2.dp
                    )
                    Text(
                        text = "미리보기  > ",
                        color = Color.White,
                        modifier = Modifier.clickable {
                            showDetail = true
                        }
                            .padding(16.dp)
                    )
                    Divider(
                        modifier = Modifier.padding(horizontal = 2.dp),
                        color = Color.DarkGray,
                        thickness = 2.dp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "M y   P h o t o",
                            color = Color.Gray
                        )
                        Text(
                            text = "수정하기 >",
                            color = Color.Gray
                        )


                    }
                    Spacer(modifier = Modifier.height(10.dp))

                }
            }
            items(myData.subImages) { post ->
                Image(
                    painter = painterResource(id = post),
                    contentDescription = "My Photo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
            }
        }


    } else{
        DetailScreen(
            post = myData,
            onBack = { showDetail = false}
        )
    }
}