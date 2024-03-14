package com.example.vknewsapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vknewsapp.MainViewModel
import com.example.vknewsapp.navigation.AppNavGraph
import com.example.vknewsapp.navigation.Screen

@Composable
fun MainScreen(mainViewModel: MainViewModel) {

    val navHostController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .height(58.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {

                val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val items = listOf(
                    BottomNavigationBarItem.Home,
                    BottomNavigationBarItem.Favourite,
                    BottomNavigationBarItem.Profile
                )

                items.forEach { navItem ->
                    NavigationBarItem(
                        selected = currentRoute == navItem.screen.route,
                        onClick = {
                            navHostController.navigate(navItem.screen.route) {
                                popUpTo(Screen.Home.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(navItem.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(id = navItem.titleResId))
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSecondary,
                            indicatorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        },
    ) { paddingValues ->

        AppNavGraph(
            navHostController = navHostController,
            homeScreenContent = {
                HomeScreen(mainViewModel = mainViewModel, paddingValues = paddingValues)
            },
            favouriteScreenContent = {
                TextCounter(name = "Favourite")
            },
            profileScreenContent = {
                TextCounter(name = "Profile")
            }
        )
    }

}

@Composable
private fun TextCounter(name: String) {
    var count by rememberSaveable {
        mutableStateOf(0)
    }

    Text(
        modifier = Modifier
            .clickable { count++ },
        text = "$name Count: $count",
        color = MaterialTheme.colorScheme.onPrimary
    )
}