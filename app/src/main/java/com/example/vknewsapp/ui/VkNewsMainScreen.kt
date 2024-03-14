package com.example.vknewsapp.ui

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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vknewsapp.MainViewModel

@Composable
fun MainScreen(mainViewModel: MainViewModel) {

    val selectedNavItem by mainViewModel.selectedNavItem.observeAsState(NavigationItem.Home)

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .height(58.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {

                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favourite,
                    NavigationItem.Profile
                )

                items.forEach { navItem ->
                    NavigationBarItem(
                        selected = selectedNavItem == navItem,
                        onClick = { mainViewModel.selectNavItem(navItem) },
                        icon = { Icon(navItem.icon, contentDescription = null) },
                        label = { Text(text = stringResource(id = navItem.titleResId)) },
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

        when (selectedNavItem) {
            NavigationItem.Home -> {
                HomeScreen(mainViewModel = mainViewModel, paddingValues = paddingValues)
            }
            NavigationItem.Favourite -> {
                Text(text = "Favourite", color = MaterialTheme.colorScheme.onPrimary)
            }
            NavigationItem.Profile -> {
                Text(text = "Profile", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }

}