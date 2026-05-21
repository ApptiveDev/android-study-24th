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
import androidx.compose.material3.* // [수정] Material 3 핵심 컴포넌트 일괄 지정
import androidx.compose.material3.ExperimentalMaterial3Api // [수정] TopAppBar 사용을 위한 필수 임포트
import androidx.compose.material3.HorizontalDivider // [수정] 구형 Divider 대체용 최신 컴포넌트 임포트
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
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize

// ───────── 데이터 모델 정의 ─────────
data class PostData(
    val id: Int, // [수정] 게시물 식별 및 실시간 전역 동기화를 위한 고유 ID 추가
    val userName: String,
    val userProfileRes: Int,
    val postImageRes: Int,
    val likeCount: Int,
    val description: String,
    val isLiked: Boolean = false, // [수정] 컴포저블 내부 remeber 대신 데이터 모델 자체에 좋아요 상태 포함
    val timeAgo: String = "방금 전"
)

data class MemberData(
    val name: String,
    val role: String,
    val profileUrl: String,
    val bio: String,
    val isFollowed: Boolean = false
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
fun HomeScreen(postList: List<PostData>, onLikeToggle: (Int) -> Unit) { // [수정] 좋아요 클릭 이벤트를 전역으로 전달하기 위한 매개변수 추가
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(postList) { post ->
            PostCard(post = post, onLikeToggle = { onLikeToggle(post.id) }) // [수정] 상위에서 내려온 람다 함수 매핑
        }
    }
}

// 게시물 카드
@Composable
fun PostCard(post: PostData, onLikeToggle: () -> Unit) { // [수정] 내부 remember 상태를 전부 제거하고 stateless 구조로 변경


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // 헤더 (프로필 + 이름)
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

            // 게시물 사진
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

            // 액션 버튼
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 좋아요 토글
                IconButton(onClick = { onLikeToggle() }) { // [수정] 클릭 시 최상위 전역 데이터 리스트를 수정하도록 위임
                    Icon(
                        imageVector = if (post.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (post.isLiked) Color.Red else Color.Black
                    )
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Share, contentDescription = null)
                }
                IconButton(onClick = { }) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null)
                }
            }

            // 좋아요 수 + 설명
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                Text(
                    text = "좋아요 ${post.likeCount}개", // [수정] 실시간 상태 변경이 바로 반영되도록 호이스팅된 값 사용
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun SearchScreen() {
    var keyword by remember { mutableStateOf("") }

    var members by remember {
        mutableStateOf(
            listOf(
                MemberData("여채언", "멘토", "https://picsum.photos/seed/101/200/200", "안드로이드 컴포즈 장인입니다."),
                MemberData("강준이", "멘토", "https://picsum.photos/seed/202/200/200", "안드로이드 컴포즈 장인입니다."),
                MemberData("엘간두르", "스터디원", "https://picsum.photos/seed/303/200/200", "..."),
                MemberData("신예나", "스터디원", "https://picsum.photos/seed/404/200/200", "..."),
                MemberData("안진형", "스터디원", "https://picsum.photos/seed/505/200/200", "..."),
                MemberData("정우영", "스터디원", "https://picsum.photos/seed/606/200/200", "..."),
                MemberData("인민에이", "스터디원", "https://picsum.photos/seed/707/200/200", "...")
            )
        )
    }
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

        AnimatedContent(
            targetState = keyword,
            label = "SearchStateAnimation",
            modifier = Modifier.fillMaxSize()
        ) { targetKeyword ->
            when {
                // 상태 1: 검색어가 비어있을 때 (전체 목록)
                targetKeyword.isEmpty() -> {
                    Column {
                        Text(
                            text = "전체 스터디원 ${members.size}명",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                        )
                        LazyColumn {
                            items(members) { member ->
                                MemberCard(
                                    member = member,
                                    onFollowToggle = {
                                        members = members.map {
                                            if (it.name == member.name) it.copy(isFollowed = !it.isFollowed) else it
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
                // 상태 2: 검색 결과가 존재하지 않을 때 (Empty View)
                filtered.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("\"$targetKeyword\" 검색 결과가 없어요", color = Color.Gray, fontSize = 14.sp)
                    }
                }
                // 상태 3: 검색 매칭 성공 시
                else -> {
                    Column {
                        Text(
                            text = "검색 결과 ${filtered.size}명",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                        )
                        LazyColumn {
                            items(filtered) { member ->
                                MemberCard(
                                    member = member,
                                    onFollowToggle = {
                                        members = members.map {
                                            if (it.name == member.name) it.copy(isFollowed = !it.isFollowed) else it
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MemberCard(member: MemberData, onFollowToggle: () -> Unit) {
    // 카드가 확장되었는지 여부를 관리하는 로컬 상태
    var isExpanded by remember { mutableStateOf(false) }

    // 팔로우 상태에 따라 배경색과 텍스트 색상이 스무스하게 변화함
    val buttonBgColor by animateColorAsState(
        targetValue = if (member.isFollowed) Color(0xFFEEEEEE) else Color(0xFF2196F3),
        label = "ButtonBgColor"
    )
    val buttonTextColor by animateColorAsState(
        targetValue = if (member.isFollowed) Color.DarkGray else Color.White,
        label = "ButtonTextColor"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { isExpanded = !isExpanded } // 카드를 누르면 확장 토글 발생

            .animateContentSize(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
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

                Button(
                    onClick = { onFollowToggle() },
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonBgColor)
                ) {
                    Text(
                        text = if (member.isFollowed) "팔로잉" else "팔로우",
                        fontSize = 12.sp,
                        color = buttonTextColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = Color(0xFFF0F0F0))
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = member.bio,
                    fontSize = 13.sp,
                    color = Color.DarkGray,
                    lineHeight = 18.sp,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
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

// ───────── 프로필 화면 ─────────
@Composable
fun ProfileScreen(postList: List<PostData>, onPostClick: (PostData) -> Unit) {
    var showProfileDialog by remember { mutableStateOf(false) }


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

// ───────── 메인 네비게이션 및 전역 상태 관리 ─────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SnsFeedScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route

    var postList by remember {
        mutableStateOf(
            listOf(
                PostData(1, "gxhyn_", R.drawable.profile, R.drawable.my_photo, 124, "Apptive Android"), // [수정] ID 값 추가 및 공백 우회 제거
                PostData(2, "gxhyn_", R.drawable.profile, R.drawable.ph, 89, "스터디 화이팅")          // [수정] ID 값 추가
            )
        )
    }

    val handleLikeToggle: (Int) -> Unit = { postId ->
        postList = postList.map { post ->
            if (post.id == postId) {
                val nextLiked = !post.isLiked
                val nextCount = if (nextLiked) post.likeCount + 1 else post.likeCount - 1
                post.copy(isLiked = nextLiked, likeCount = nextCount)
            } else {
                post
            }
        }
    }

    val items = listOf(Screen.Home, Screen.Search, Screen.Profile)

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
            if (currentRoute?.startsWith("detail") == false) {
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
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(postList = postList, onLikeToggle = handleLikeToggle)
            }
            composable(Screen.Search.route) { SearchScreen() }
            composable(Screen.Profile.route) {
                ProfileScreen(postList = postList, onPostClick = { post ->
                    val safeDescription = post.description.replace("#", "").trim().ifEmpty { "내용없음" }
                    navController.navigate("detail/${post.id}/$safeDescription")
                })
            }

            composable("detail/{postId}/{description}") { backStackEntry ->
                val postId = backStackEntry.arguments?.getString("postId")?.toIntOrNull() ?: 0
                val description = backStackEntry.arguments?.getString("description") ?: ""

                val post = postList.firstOrNull { it.id == postId } ?: postList[0]

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF5F5F5)),
                    contentAlignment = Alignment.TopCenter
                ) {
                    PostCard(post = post.copy(description = description), onLikeToggle = { handleLikeToggle(post.id) })
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