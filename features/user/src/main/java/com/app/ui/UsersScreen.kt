package com.app.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.app.domain.models.Details
import com.vikash.common_ui.UserTopbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen() {
    Scaffold(
        topBar = {
            UserTopbar("Users")
        },
        content = { padding ->
            UserList(padding)
        }
    )
}

@Composable
fun UserList(padding: PaddingValues) {
    LazyVerticalStaggeredGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = padding.calculateTopPadding() + 16.dp,
                bottom = padding.calculateBottomPadding(),
                start = 16.dp,
                end = 16.dp
            ),
        columns = StaggeredGridCells.Fixed(2)
    ) {
        items(
            count = 10,
            key = { index -> "id${index}" },
            contentType = { index -> Details::class.java },
            itemContent = { index ->
                Box(
                    modifier = Modifier.fillMaxWidth(0.5f).padding(top = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(contentColor = Color.White),
                        onClick = {

                        }
                    ) {
                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 80.dp, bottom = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "name",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color =  MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = "email",
                                    fontSize = 14.sp,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFF00796B)
                                )

                                Text(
                                    text = "company",
                                    fontSize = 12.sp,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray,
                                    minLines = 2,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    AsyncImage(
                        model = "https://json-server.dev/ai-profiles/94.png",
                        contentDescription = "User Image",
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .offset( y = (-20).dp)
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            )
                    )
                    }
            }
        )
    }
}