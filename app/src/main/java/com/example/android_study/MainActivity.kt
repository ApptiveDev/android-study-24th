package com.example.android_study

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage

// ───────── 데이터 모델 및 네비게이션 정의 ─────────
data class PostData(
    val userName: String,
    val userProfileRes: Int,
    val postImageRes: Int,
    val likeCount: Int,
    val description: String,
    val timeAgo: String = "방금 전"
)

data class MemberData(
    val name: String,
    val role: String,
    val profileUrl: String
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

// ───────── 홈 화면 ─────────
@Composable
fun HomeScreen(postList: List<PostData>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(postList) { post ->
            PostCard(post)
        }
    }
}

@Composable
fun PostCard(post: PostData) {
    var isLiked by remember { mutableStateOf(false) }
    var likeCount by remember { mutableIntStateOf(post.likeCount) }

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
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = post.userName,
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
                IconButton(onClick = {
                    isLiked = !isLiked
                    likeCount = if (isLiked) likeCount + 1 else likeCount - 1
                }) {
                    Icon(
                        imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (isLiked) Color.Red else Color.Black
                    )
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Share, contentDescription = null)
                }
                IconButton(onClick = { }) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null)
                }
            }

            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                Text(
                    text = "좋아요 ${likeCount}개",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = post.description, fontSize = 14.sp, lineHeight = 20.sp)
            }
        }
    }
}

// ───────── 검색 화면 ─────────
@Composable
fun SearchScreen() {
    var keyword by remember { mutableStateOf("") }

    val members = listOf(
        MemberData("여채언", "멘토", "https://picsum.photos/seed/101/200/200"),
        MemberData("강준이", "멘토", "https://picsum.photos/seed/202/200/200"),
        MemberData("엘간두르", "스터디원", "https://picsum.photos/seed/303/200/200"),
        MemberData("신예나", "스터디원", "https://picsum.photos/seed/404/200/200"),
        MemberData("안진형", "스터디원", "https://picsum.photos/seed/505/200/200"),
        MemberData("정우영", "스터디원", "https://picsum.photos/seed/606/200/200"),
        MemberData("인민에이", "스터디원", "https://picsum.photos/seed/707/200/200")
    )

    val filtered = members.filter { it.name.contains(keyword.trim(), ignoreCase = true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        OutlinedTextField(
            value = keyword,
            onValueChange = { keyword = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("스터디원 검색") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (keyword.isNotEmpty()) {
                    IconButton(onClick = { keyword = "" }) {
                        Icon(Icons.Default.Close, contentDescription = "지우기")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        if (keyword.isEmpty()) {
            Text(
                text = "스터디원 ${members.size}명",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
            )
            LazyColumn {
                items(members) { member ->
                    MemberCard(member)
                }
            }
        } else if (filtered.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("\"$keyword\" 검색 결과가 없어요", color = Color.Gray, fontSize = 14.sp)
            }
        } else {
            Text(
                text = "검색 결과 ${filtered.size}명",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
            )
            LazyColumn {
                items(filtered) { member ->
                    MemberCard(member)
                }
            }
        }
    }
}

@Composable
fun MemberCard(member: MemberData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = member.profileUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(member.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(member.role, fontSize = 13.sp, color = Color.Gray)
            }
            OutlinedButton(
                onClick = {},
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text("팔로우", fontSize = 12.sp, color = Color.Black)
            }
        }
    }
}

@Composable
fun ProfileStat(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(label, fontSize = 12.sp, color = Color.Gray)
    }
}

// ───────── 프로필 화면  ─────────
@Composable
fun ProfileScreen(postList: List<PostData>, onPostClick: (PostData) -> Unit) {
    var showProfileDialog by remember { mutableStateOf(false) }

    // 프로필 사진 확대 다이얼로그
    if (showProfileDialog) {
        Dialog(onDismissRequest = { showProfileDialog = false }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .clickable { showProfileDialog = false },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }

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
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color(0xFFDDDDDD), CircleShape)
                    .clickable { showProfileDialog = true },
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(24.dp))
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProfileStat(postList.size.toString(), "게시물")
                ProfileStat("1,024", "팔로워")
                ProfileStat("342", "팔로잉")
            }
        }

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("박강현", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text("스터디", fontSize = 13.sp, color = Color.DarkGray)
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("프로필 편집", fontWeight = FontWeight.SemiBold, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = Color(0xFFEEEEEE))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(1.dp),
            horizontalArrangement = Arrangement.spacedBy(1.5.dp),
            verticalArrangement = Arrangement.spacedBy(1.5.dp)
        ) {
            items(postList) { post ->
                Image(
                    painter = painterResource(id = post.postImageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxWidth()
                        .clickable { onPostClick(post) },
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

// ───────── 메인 네비게이션 메인 구조 ─────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SnsFeedScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route

    val postList = listOf(
        PostData("gxhyn_", R.drawable.profile, R.drawable.my_photo, 124, "Apptive Android"),
        PostData("gxhyn_", R.drawable.profile, R.drawable.ph, 89, "스터디 화이팅")
    )

    Scaffold(
        topBar = {

            when {
                currentRoute == Screen.Home.route -> {
                    TopAppBar(
                        title = { Text("Appstargram", fontWeight = FontWeight.Bold, fontSize = 22.sp) },
                        actions = {
                            IconButton(onClick = {}) { Icon(Icons.Default.FavoriteBorder, null) }
                            IconButton(onClick = {}) { Icon(Icons.AutoMirrored.Filled.Send, null) }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                    )
                }
                currentRoute == Screen.Profile.route -> {
                    TopAppBar(
                        title = { Text("gxhyn_", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                        actions = {
                            IconButton(onClick = {}) { Icon(Icons.Default.Settings, null) }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                    )
                }
                currentRoute?.startsWith("detail") == true -> {
                    TopAppBar(
                        title = { Text("게시물", fontWeight = FontWeight.Bold) },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.Default.ArrowBack, "뒤로가기")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                    )
                }
            }
        },
        bottomBar = {
            if (currentRoute?.startsWith("detail") == false || currentRoute == Screen.Home.route || currentRoute == Screen.Search.route || currentRoute == Screen.Profile.route) {
                if (currentRoute?.startsWith("detail") != true) {
                    NavigationBar(containerColor = Color.White) {
                        val items = listOf(Screen.Home, Screen.Search, Screen.Profile)
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

            composable(Screen.Profile.route) {
                ProfileScreen(postList = postList, onPostClick = { post ->
                    val safeDescription = post.description.replace("#", "").trim().ifEmpty { "내용없음" }
                    navController.navigate("detail/${post.userName}/$safeDescription")
                })
            }

            composable("detail/{userName}/{description}") { backStackEntry ->
                val userName = backStackEntry.arguments?.getString("userName") ?: ""
                val description = backStackEntry.arguments?.getString("description") ?: ""

                val post = postList.firstOrNull { it.userName == userName } ?: postList[0]

                Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F5)), contentAlignment = Alignment.TopCenter) {
                    PostCard(post = post.copy(description = description))
                }
            }
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