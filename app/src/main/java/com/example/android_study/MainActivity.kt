package com.example.android_study

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android_study.ui.theme.AndroidstudyTheme

/**
 * 안드로이드 앱의 대문.
 * 사용자가 앱 아이콘을 눌렀을 때 가장 먼저 실행되는 클래스
 * onCreate(): 앱이 처음 생성될 때 딱 한 번 호출되는 함수
 * setContent : 앱의 UI를 설정하는 함수, 이 안에 있는 내용으로 화면을 채우겠다.
 * Scaffold: 상단바, 하단바 같은 기본 레이아웃 구조를 쉽게 잡을 수 있게 도와주는 틀
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidstudyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "앱티브 안드로이드 스터디",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

/**
 * UI의 최소 단위: @Composable 함수
 */

/**
 * modifier 순서에 따라 ui가 다르게 그려질 수 있습니다!
 */
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = "Hello $name!")
//        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "배경 먼저!!!",
            modifier = Modifier
                .background(Color.Yellow) // 1. 노란색으로 칠한다
                .padding(20.dp) // 2. 그 상태에서 안쪽 여백을 준다
        )

        Text(
            text = "여백 먼저!",
            modifier = Modifier
                .padding(20.dp) // 1. 투명한 여백을 먼저 만든다
                .background(Color.Yellow) // 2. 남은 영역에 노란색을 칠한다
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidstudyTheme {
        Greeting("Android")
    }
}