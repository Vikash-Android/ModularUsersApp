package com.app.user.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.ui.models.UserUiModel
import com.app.ui.screen.UserDetailScreen
import com.app.ui.screen.UserScreen

object Routes {
    const val USER_LIST = "user_list"
    const val USER_DETAIL = "user_detail"
}

private const val KEY_DETAIL = "detail"

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.USER_LIST) {
        composable(Routes.USER_LIST) {
            UserScreen(onNavigateToDetails = { userDetail ->
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    KEY_DETAIL,
                    userDetail
                )
                navController.navigate(Routes.USER_DETAIL)
            })
        }
        composable(Routes.USER_DETAIL) { backstackEntry ->
            val detailFromNav =
                navController.previousBackStackEntry?.savedStateHandle?.get<UserUiModel>(KEY_DETAIL)
            val userDetail = rememberSaveable { mutableStateOf(detailFromNav) }

            UserDetailScreen(userDetail.value, { navController.popBackStack() })
        }

    }
}