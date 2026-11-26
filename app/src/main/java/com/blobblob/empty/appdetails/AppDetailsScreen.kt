package com.blobblob.empty.appdetails

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.blobblob.empty.AppItem
import com.blobblob.empty.R

@Composable
fun AppDetailsScreen(
    appId: String,
    onBackClick: () -> Unit,
    onShowcaseAppClick: (AppItem) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AppDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val underDevelopmentText = stringResource(R.string.under_developement)

    LaunchedEffect(appId) {
        viewModel.loadApp(appId)
    }

    when (val currentState = state) {
        is AppDetailsState.Loading -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is AppDetailsState.Error -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.under_developement),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        is AppDetailsState.Content -> {
            val appDetails = currentState.appDetails
            val showcaseApps = currentState.showcaseApps

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .navigationBarsPadding()
            ) {
                Toolbar(
                    onBackClick = onBackClick,
                    onShareClick = {
                        Toast.makeText(context, underDevelopmentText, Toast.LENGTH_SHORT).show()
                    },
                    isInWishlist = currentState.isInWishlist,
                    onWishlistClick = {
                        viewModel.toggleWishlist()
                    }
                )
                Spacer(Modifier.height(8.dp))
                AppDetailsHeader(
                    appDetails = appDetails,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
                Spacer(Modifier.height(16.dp))
                InstallButton(
                    installState = currentState.installState,
                    onClick = {
                        viewModel.onInstallClick()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(12.dp))
                ScreenshotsList(
                    screenshotUrlList = appDetails.screenshotUrlList,
                    contentPadding = PaddingValues(horizontal = 16.dp),
                )
                Spacer(Modifier.height(12.dp))
                AppDescription(
                    description = appDetails.description,
                    collapsed = currentState.descriptionCollapsed,
                    onReadMoreClick = {
                        viewModel.collapseDescription()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                )
                Spacer(Modifier.height(12.dp))
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.outlineVariant,
                )
                Spacer(Modifier.height(12.dp))
                Developer(
                    name = appDetails.developer,
                    onClick = {
                        Toast.makeText(context, underDevelopmentText, Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                )
                Spacer(Modifier.height(16.dp))
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.outlineVariant,
                )
                Showcase(
                    apps = showcaseApps,
                    onAppClick = onShowcaseAppClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
