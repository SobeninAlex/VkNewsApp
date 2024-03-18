package com.example.vknewsapp.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vknewsapp.R
import com.example.vknewsapp.ui.theme.Black300
import com.example.vknewsapp.ui.theme.Black700

@Preview
@Composable
fun SplashScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Black700),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(200.dp),
                painter = painterResource(id = R.drawable.vk_outline),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Black300,
                text = stringResource(id = R.string.app_name)
            )
        }
    }
}