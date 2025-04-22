package anz.project.userapp.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.UrlAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import anz.project.domain.model.User
import anz.project.userapp.common.AppConstants
import anz.project.userapp.ui.common.CoilImageFromUrl
import anz.project.userapp.ui.theme.AnzBlueColor
import anz.project.userapp.ui.theme.BlackColor
import anz.project.userapp.viewmodel.UserDetailViewModel
@Composable
fun UserDetailScreen(
    userId: Int,
    viewModel: UserDetailViewModel = hiltViewModel(),
    modifier: Modifier
) {
    val userState = viewModel.user.collectAsState()

    LaunchedEffect(userId) {
        viewModel.loadUser(userId)
    }

    when (val state = userState.value) {
        is UserDetailViewModel.UserState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
        is UserDetailViewModel.UserState.Success -> {
            UserDetail(user = state.user, modifier = modifier)
        }
        is UserDetailViewModel.UserState.Error -> {
            Text(
                text = state.message,
                modifier = modifier.fillMaxSize(),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun UserDetail(user: User, modifier: Modifier) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column( modifier = modifier.padding(16.dp).fillMaxSize()) {
            Spacer(modifier = Modifier.height(8.dp))
            CoilImageFromUrl(
                imageUrl = user.photo,
                contentDescription = "Photo of ${user.name}",
                isCircular = false,
                width = 192,
                height = 192
            )
            Text(
                text = user.name,
                style = TextStyle(
                    color = BlackColor,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontStyle = MaterialTheme.typography.titleLarge.fontStyle
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Email: ${user.email}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Phone: ${user.phone}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Company: ${user.company}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Address: ${user.address}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Country: ${user.country}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Zip: ${user.zip}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}