package anz.project.userapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import anz.project.userapp.common.AppConstants
import anz.project.userapp.navigation.appNavGraph
import anz.project.userapp.ui.common.AppScaffold
import anz.project.userapp.ui.theme.AnzAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnzAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.primary
                ) {
                    App(AppConstants.Routes.USER_LIST)
                }
            }
        }
    }
}


@Composable
fun App(startDestination: String = AppConstants.Routes.USER_LIST) {
    //NavController
    val navController = rememberNavController()
    AppScaffold(startDestination, navController)
}