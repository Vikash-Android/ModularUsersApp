package com.app.user.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.ui.UserDetailScreen
import com.app.ui.UserScreen
import com.app.ui.models.UserUiModel
import com.app.user.ui.theme.UserTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UserTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    AppNavGraph(rememberNavController())
                }

            }
        }
    }

    object Routes {
        const val USER_LIST = "user_list"
        const val USER_DETAIL = "user_detail"
    }

    @Composable
    fun AppNavGraph(navController: NavHostController) {
        NavHost(navController = navController, startDestination = Routes.USER_LIST) {
            composable(Routes.USER_LIST) {
                UserScreen(goToDetailScreen = { userDetail ->
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        "detail",
                        userDetail
                    )
                    navController.navigate(Routes.USER_DETAIL)
                })
            }
            composable(Routes.USER_DETAIL) { backstackEntry ->
                val detailFromNav =
                    navController.previousBackStackEntry?.savedStateHandle?.get<UserUiModel>("detail")
                val userDetail = rememberSaveable { mutableStateOf(detailFromNav) }
                UserDetailScreen(userDetail.value, { navController.popBackStack() })
            }

        }
    }
}