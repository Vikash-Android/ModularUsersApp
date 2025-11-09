package com.app.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.app.ui.models.UserUiModel
import com.app.ui.presentation.UserViewModel
import com.app.ui.presentation.state.ScreenState
import com.app.ui.common.ErrorView
import com.app.ui.common.LoadingView
import com.app.ui.R
import com.app.ui.common.UserTopbar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    viewModel: UserViewModel = hiltViewModel(),
    onNavigateToDetails: (userDetail: UserUiModel) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchUser()
    }

    Scaffold(
        topBar = {
            UserTopbar(stringResource(R.string.user_screen_title))
        },
        content = { padding ->
            when (uiState.screenState) {
                ScreenState.LOADING -> LoadingView()
                ScreenState.GENRIC_ERROR -> ErrorView(
                    message = stringResource(R.string.user_generic_error_message),
                    onRetry = { viewModel.fetchUser() }
                )

                ScreenState.NETWORK_ERROR -> ErrorView(
                    message = stringResource(R.string.user_network_error_message),
                    onRetry = { viewModel.fetchUser() }
                )

                else -> UserList(
                    modifier = Modifier.padding(padding),
                    users = uiState.users,
                    onNavigateToDetails = onNavigateToDetails
                )
            }
        }
    )
}

@Composable
fun UserList(
    modifier: Modifier = Modifier,
    users: List<UserUiModel>,
    onNavigateToDetails: (userDetail: UserUiModel) -> Unit
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 16.dp
    ) {
        items(
            items = users,
            key = { user -> user.id },
            contentType = { UserUiModel::class.java }
        ) { detail ->
            UserListItem(
                detail = detail,
                onNavigateToDetails = onNavigateToDetails
            )
        }
    }
}

@Composable
fun UserListItem(
    detail: UserUiModel,
    onNavigateToDetails: (userDetail: UserUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(contentColor = Color.White),
            onClick = { onNavigateToDetails(detail) }
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