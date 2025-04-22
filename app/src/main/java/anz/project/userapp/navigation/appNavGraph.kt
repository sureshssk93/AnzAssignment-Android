package anz.project.userapp.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.navigation.navArgument
import anz.project.userapp.common.AppConstants
import anz.project.userapp.ui.detail.UserDetailScreen
import anz.project.userapp.ui.list.UserListScreen

@SuppressLint("DefaultLocale")
@Composable
fun appNavGraph(
    startDestination: String,
    navController: NavHostController = rememberNavController(),
    paddingValues: PaddingValues
): NavGraph {
    return navController.createGraph(startDestination) {
        composable(AppConstants.Routes.USER_LIST) {
            UserListScreen(
                onUserClick = { userId ->
                    navController.navigate(
                        String.format(
                            AppConstants.Routes.USER_DETAIL_WITH_ID,
                            userId
                        )
                    )
                },
                modifier = Modifier.padding(paddingValues)
            )
        }
        composable(
            route = AppConstants.Routes.USER_DETAIL,
            arguments = listOf(navArgument(AppConstants.USER_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt(AppConstants.USER_ID) ?: 0
            UserDetailScreen(userId = userId, modifier = Modifier.padding(paddingValues))
        }
    }
}