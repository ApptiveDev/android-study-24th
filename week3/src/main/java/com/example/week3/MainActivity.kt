package com.example.week3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen()
        }
    }
}

data class User(val name: String, val age: Int)

@Composable
fun MainScreen() {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = "홈") },
                    label = { Text("홈") }
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
                    icon = { Icon(Icons.Default.Person, contentDescription = "마이페이지") },
                    label = { Text("마이") }
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
                0 -> HomeRootScreen()
                1 -> SearchScreen()
                2 -> ProfileScreen()
            }
        }
    }
}

@Composable
fun HomeRootScreen() {
    var selectedUser by remember { mutableStateOf<User?>(null) }

    if (selectedUser == null) {
        UserListScreen(
            onUserClick = { user ->
                selectedUser = user
            }
        )
    } else {
        UserDetailScreen(
            user = selectedUser!!,
            onBack = {
                selectedUser = null
            }
        )
    }
}

@Composable
fun UserListScreen(
    onUserClick: (User) -> Unit
) {
    val users = listOf(
        User("준이", 23),
        User("준삼", 33),
        User("준사", 43),
        User("준오", 53),
        User("준육", 63)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "사용자 목록",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(users) { user ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            onUserClick(user)
                        }
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = user.name,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "나이: ${user.age}"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UserDetailScreen(
    user: User,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "상세 화면",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "이름: ${user.name}",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "나이: ${user.age}"
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("뒤로가기")
        }
    }
}

@Composable
fun SearchScreen() {
    var keyword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "검색 화면",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = keyword,
            onValueChange = {
                keyword = it
            },
            label = {
                Text("검색어 입력")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("입력한 검색어: $keyword")
    }
}

@Composable
fun ProfileScreen() {
    var liked by remember { mutableStateOf(false) }
    var count by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "마이페이지",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text("이름: 준이")
                Text("직업: 학생")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                liked = !liked
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (liked) "좋아요 취소" else "좋아요")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                count++
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Count: $count")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MaterialTheme {
        MainScreen()
    }
}