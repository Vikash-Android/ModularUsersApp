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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.app.domain.models.Details
import com.app.ui.models.UserUiModel
import com.vikash.common_ui.ErrorView
import com.vikash.common_ui.LoadingView
import com.vikash.common_ui.UserTopbar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    viewModel: UserViewModel = hiltViewModel(),
    goToDetailScreen: (userDetail: UserUiModel) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            UserTopbar("Users")
        },
        content = { padding ->
            when (uiState) {
                is UserUiState.Loading -> LoadingView()
                is UserUiState.Error -> ErrorView((uiState as UserUiState.Error).message)
                is UserUiState.Success -> UserList(
                    padding,
                    (uiState as UserUiState.Success).users,
                    goToDetailScreen
                )
            }
        }
    )
}

@Composable
fun UserList(
    padding: PaddingValues,
    users: List<UserUiModel>,
    goToDetailScreen: (userDetail: UserUiModel) -> Unit
) {
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
            count = users.size,
            key = { index -> users[index].id },
            contentType = { index -> Details::class.java },
            itemContent = { index ->
                val detail = users[index]
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(top = 20.dp),
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
                            goToDetailScreen(detail)
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 80.dp, bottom = 16.dp, start = 8.dp, end = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = detail.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Text(
                                text = detail.username,
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
                        model = detail.photo,
                        contentDescription = detail.name,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .offset(y = (-20).dp)
                            .size(80.dp)
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