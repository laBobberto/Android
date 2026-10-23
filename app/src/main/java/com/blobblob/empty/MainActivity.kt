package com.blobblob.empty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.blobblob.empty.appdetails.AppDetailsScreen
import com.blobblob.empty.ui.theme.EmptyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EmptyTheme {
                AppNavigation(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "appList",
        modifier = modifier
    ) {
        composable("appList") {
            AppListScreen(
                onAppClick = { appId ->
                    navController.navigate("appDetails/$appId")
                }
            )
        }
        composable(
            route = "appDetails/{appId}",
            arguments = listOf(
                navArgument("appId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val appId = backStackEntry.arguments?.getString("appId") ?: "1"
            AppDetailsScreen(
                appId = appId,
                onBackClick = {
                    navController.popBackStack()
                },
                onShowcaseAppClick = {
                    navController.navigate("appList") {
                        popUpTo("appList") {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
