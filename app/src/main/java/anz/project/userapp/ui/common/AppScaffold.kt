package anz.project.userapp.ui.common

import android.util.Log
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import anz.project.userapp.common.AppConstants
import anz.project.userapp.navigation.appNavGraph
import anz.project.userapp.ui.theme.AnzBlueColor

@Composable
fun AppScaffold(
    startDestination: String,
    navController: NavHostController
) {
    val isBackEnabled = remember {
        mutableStateOf(false)
    }

    val currentTitle = remember {
        mutableStateOf(AppConstants.Titles.APP_TITLE)
    }

    navController.addOnDestinationChangedListener { _,
                                                    destination,
                                                    _ ->
        Log.i("NavController", "Destination: ${destination.route}")
        isBackEnabled.value = destination.route == AppConstants.Routes.USER_LIST
        
        // Update title based on current route
        currentTitle.value = when (destination.route) {
            AppConstants.Routes.USER_LIST -> AppConstants.Titles.APP_TITLE
            AppConstants.Routes.USER_DETAIL -> AppConstants.Titles.USER_DETAIL
            else -> AppConstants.Titles.APP_TITLE
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                contentColor = AnzBlueColor,
                title = {
                    Text(text = currentTitle.value, style = MaterialTheme.typography.titleMedium)
                },
                navigationIcon = {
                    var navIcon = if (isBackEnabled.value) {
                        Icons.Filled.Home
                    } else {
                        Icons.Filled.ArrowBack
                    }

                    IconButton(onClick = {
                        if (!isBackEnabled.value) {
                            navController.popBackStack()
                        }
                    }) {
                        Icon(imageVector = navIcon, contentDescription = "NavIcon")
                    }
                }
            )
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            graph = appNavGraph(startDestination, navController, paddingValues)
        )
    }
}