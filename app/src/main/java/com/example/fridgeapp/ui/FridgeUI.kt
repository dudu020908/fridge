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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.fridgeapp.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fridgeapp.viewmodel.FridgeViewModel
import com.example.fridgeapp.model.Ingredient

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

//  2.내부 냉장고 화면
@Composable
fun FridgeInsideScreen(
    navController: NavController?,
    viewModel: FridgeViewModel = viewModel()
) {
    var name by rememberSaveable { mutableStateOf("") }
    var quantity by rememberSaveable { mutableStateOf("") }
    var expiry by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("신선") }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        // ✅ 재료 입력
        Text("재료 등록", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("재료 이름") })
        OutlinedTextField(value = quantity, onValueChange = { quantity = it }, label = { Text("수량") })
        OutlinedTextField(value = expiry, onValueChange = { expiry = it }, label = { Text("유통기한") })

        Spacer(Modifier.height(8.dp))
        Button(onClick = {
            if (name.isNotBlank() && quantity.isNotBlank()) {
                viewModel.addIngredient(
                    Ingredient(
                        name = name,
                        quantity = quantity.toInt(),
                        expiryDate = expiry,
                        category = category
                    )
                )
                name = ""; quantity = ""; expiry = ""; category = "신선"
            }
        }) {
            Text("등록")
        }

        Spacer(Modifier.height(16.dp))

        // ✅ 냉장고 이미지 + 버튼 겹치기
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.fridgeinside),
                contentDescription = "냉장고 내부",
                modifier = Modifier.fillMaxSize()
            )

            // 예시: 신선칸 버튼
            Button(
                onClick = { viewModel.selectedCategory = "신선" },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 24.dp)
            ) { Text("신선칸") }

            // 예시: 냉동칸 버튼
            Button(
                onClick = { viewModel.selectedCategory = "냉동" },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
            ) { Text("냉동칸") }
        }

        Spacer(Modifier.height(16.dp))


        viewModel.selectedCategory?.let { selected ->
            Text("$selected 보관 재료", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(viewModel.getByCategory(selected)) { ingredient ->
                    Card(Modifier.fillMaxWidth(), elevation = 2.dp) {
                        Row(
                            Modifier.padding(12.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(ingredient.name, fontWeight = FontWeight.SemiBold)
                                Text("수량: ${ingredient.quantity}")
                                Text("유통기한: ${ingredient.expiryDate}")
                            }
                            TextButton(onClick = { /* TODO 상세 */ }) {
                                Text("상세", color = Color.Blue)
                            }
                        }
                    }
                }
            }
        } ?: Text("냉장고 칸을 선택해주세요")
    }
}


