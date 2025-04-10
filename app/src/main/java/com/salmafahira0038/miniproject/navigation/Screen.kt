package com.salmafahira0038.miniproject.navigation

sealed class Screen (val route: String){
    data object Home: Screen("mainScreen")
    data object About: Screen("aboutScreen")
}