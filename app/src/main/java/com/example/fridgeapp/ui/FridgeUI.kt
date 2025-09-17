package com.example.fridgeapp.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import com.example.fridgeapp.R

@Composable
fun FridgeScreen(navController: NavController) {
    var opened by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,   // 👈 ripple 제거
                interactionSource = remember { MutableInteractionSource() }
            ) {
                if (!opened) opened = true
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (opened) {
                // 열린 냉장고 (내부 화면)
                FridgeInsideScreen(navController)
            } else {
                // 닫힌 냉장고
                Image(
                    painter = painterResource(R.drawable.fridge_closed),
                    contentDescription = "닫힌 냉장고",
                    modifier = Modifier.size(300.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("냉장고를 열어보려면 클릭하세요")
            }
        }
    }
}

// ✅ 2. 내부 냉장고 화면 (기존 HomeScreen)
@Composable
fun FridgeInsideScreen(navController: NavController?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("MY 냉장고", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(12.dp))

        // 냉장고 내부 + 버튼 겹치기
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp) // 냉장고 크기
        ) {
            // 1. 냉장고 이미지 (배경)
            Image(
                painter = painterResource(id = R.drawable.fridgeinside),
                contentDescription = "열린 냉장고 내부",
                modifier = Modifier.fillMaxSize()
            )

            // 2. 신선칸 버튼 (왼쪽 위에 배치)
            Button(
                onClick = { /* 신선칸 이동 */ },
                modifier = Modifier
                    .align(Alignment.TopStart) // 위치 지정
                    .padding(24.dp)
                    .size(width = 120.dp, height = 60.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0x66E3F2FD) // 반투명 배경
                )
            ) {
                Text("신선", color = Color(0xFF1565C0))
            }

            // 3. 과일칸 버튼 (오른쪽 위)
            Button(
                onClick = { /* 과일칸 이동 */ },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(24.dp)
                    .size(width = 120.dp, height = 60.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0x66E3F2FD)
                )
            ) {
                Text("과일", color = Color(0xFF1565C0))
            }

            // 4. 냉동칸 버튼 (아래쪽 가운데)
            Button(
                onClick = { /* 냉동칸 이동 */ },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(24.dp)
                    .size(width = 200.dp, height = 80.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0x66E3F2FD)
                )
            ) {
                Text("냉동", color = Color(0xFF1565C0))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

    }
}

