package com.example.android_study

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

data class PostData(
    val userName: String,
    val userProfileRes: Int,
    val postImageRes: Int,
    val likeCount: Int,
    val description: String
)

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "홈", Icons.Default.Home)
    object Search : Screen("search", "검색", Icons.Default.Search)
    object Profile : Screen("profile", "프로필", Icons.Default.Person)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF5F5F5)
                ) {
                    SnsFeedScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen(postList: List<PostData>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(postList) { post ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = post.userProfileRes),
                            contentDescription = null,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = post.userName,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { }) {
                            Icon(Icons.Default.MoreVert, contentDescription = null)
                        }
                    }

                    Image(
                        painter = painterResource(id = post.postImageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(horizontal = 12.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { }) {
                            Icon(Icons.Default.Favorite, contentDescription = null, tint = Color.Red)
                        }
                        IconButton(onClick = { }) {
                            Icon(Icons.Default.Share, contentDescription = null)
                        }
                        IconButton(onClick = { }) {
                            Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null)
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    ) {
                        Text(
                            text = "좋아요 ${post.likeCount}개",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = post.description, fontSize = 14.sp, lineHeight = 20.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun SearchScreen() {
    var keyword by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        // 1. 현대적인 검색바 디자인
        OutlinedTextField(
            value = keyword,
            onValueChange = { keyword = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("관심사를 검색해보세요") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        // 2. 탐색 탭의 핵심: 3열 이미지 그리드
        val imageList = (1..20).toList() // 임시 데이터

        LazyVerticalGrid(
            columns = GridCells.Fixed(3), // 3열 고정
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(1.dp),
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            items(imageList) { index ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .background(Color.LightGray) // 실제로는 여기에 Image가 들어감
                ) {
                    Text(
                        text = "$index",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.DarkGray,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("프로필 화면")
    }
}

@Composable
fun SnsFeedScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // 더미 데이터 정의 (HomeScreen에 전달용)
    val postList = listOf(
        PostData("gxhyn_", R.drawable.profile, R.drawable.my_photo, 124, "#Apptive #Android"),
        PostData("gxhyn_", R.drawable.profile, R.drawable.ph, 89, "스터디 화이팅")
    )

    val items = listOf(Screen.Home, Screen.Search, Screen.Profile)

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen(postList) }
            composable(Screen.Search.route) { SearchScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SnsFeedScreenPreview() {
    MaterialTheme {
        SnsFeedScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    MaterialTheme {
        SearchScreen()
    }
}

@Preview(showBackground = true, name = "프로필 화면")
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen()
    }
}
