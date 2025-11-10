package com.app.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.app.ui.R
import com.app.ui.models.UserUiModel
import com.app.ui.common.UserTopbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(detail: UserUiModel?, onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            UserTopbar(
                title = stringResource(R.string.user_details_screen_title),
                showBackButton = true,
                onBackClick = onBackClick
            )
        },
        content = { padding ->
            detail?.let {
                DetailView(
                    modifier = Modifier.padding(padding),
                    detail = it
                )
            }
        }
    )
}

@Composable
fun DetailView(modifier: Modifier = Modifier, detail: UserUiModel) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = detail.photo,
            contentDescription = detail.name,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
        )
        Text(
            text = detail.name,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        InfoText(text = detail.email)
        Spacer(modifier = Modifier.height(8.dp))
        InfoText(text = detail.username)
        Spacer(modifier = Modifier.height(8.dp))
        InfoText(text = detail.company)
        Spacer(modifier = Modifier.height(12.dp))
        InfoText(text = detail.country)
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun InfoText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = 16.sp,
        style = MaterialTheme.typography.bodySmall,
        color = Color.Gray,
        overflow = TextOverflow.Ellipsis
    )
}