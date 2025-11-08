package com.app.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.app.ui.models.UserUiModel
import com.vikash.common_ui.UserTopbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(detail: UserUiModel?, onBackClick: () -> Unit) {
    Scaffold(
        topBar = { UserTopbar("Detail", true, onBackClick) },
        content = { padding ->
            detail?.let {  DetailView(padding, detail)}
        }
    )
}

@Composable
fun DetailView(padding: PaddingValues, detail: UserUiModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = padding.calculateTopPadding() + 16.dp,
                start = 8.dp,
                end = 8.dp
            )
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
            modifier = Modifier.padding(top = 16.dp).fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = detail.email,
            fontSize = 16.sp,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = detail.username,
            fontSize = 16.sp,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = detail.company,
            fontSize = 16.sp,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = detail.country,
            fontSize = 16.sp,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}