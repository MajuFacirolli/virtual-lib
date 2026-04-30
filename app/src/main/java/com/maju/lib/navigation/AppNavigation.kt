package com.maju.lib.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.maju.lib.ui.BookSearchScreen
import com.maju.lib.ui.SignInScreen
import com.maju.lib.ui.SignUpScreen
import com.maju.lib.viewmodel.AuthViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

    val startDestination = if (authViewModel.isUserAuthenticated()) "books" else "signin"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("signup") {
            SignUpScreen(
                authViewModel = authViewModel,
                onNavigateToSignIn = {
                    navController.navigate("signin") {
                        popUpTo("signup") { inclusive = true }
                    }
                },
                onSignUpSuccess = {
                    navController.navigate("books") {
                        popUpTo("signup") { inclusive = true }
                    }
                }
            )
        }
        composable("signin") {
            SignInScreen(
                authViewModel = authViewModel,
                onNavigateToSignUp = {
                    navController.navigate("signup") {
                        popUpTo("signin") { inclusive = true }
                    }
                },
                onSignInSuccess = {
                    navController.navigate("books") {
                        popUpTo("signin") { inclusive = true }
                    }
                }
            )
        }
        composable("books") {
            BookSearchScreen(
                onLogout = {
                    authViewModel.signOut()
                    navController.navigate("signin") {
                        popUpTo("books") { inclusive = true }
                    }
                }
            )
        }
    }
}
