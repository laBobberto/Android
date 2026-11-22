package com.blobblob.empty.appdetails

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.blobblob.empty.R

@Composable
fun InstallButton(
    installState: InstallState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val text = when (installState) {
        is InstallState.NotInstalled -> stringResource(R.string.install)
        is InstallState.Installing -> stringResource(R.string.cancel_install_progress, installState.progress)
        is InstallState.Installed -> stringResource(R.string.installed)
    }
    val colors = if (installState is InstallState.Installing) {
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error,
            contentColor = MaterialTheme.colorScheme.onError
        )
    } else {
        ButtonDefaults.buttonColors()
    }
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(vertical = 12.dp),
        colors = colors,
        modifier = modifier,
    ) {
        Text(text = text)
    }
}
