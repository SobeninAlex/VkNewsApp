package com.example.vknewsapp.utils.examples

import android.os.Handler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Если функция вносит какие-то изменения за пределами своего скоупа, то про такую функцию можно
 * сказать, что она имеет side-эффект. Помимо прямого своего назначения, она меняет состояние программы
 * за пределами скоупа своей ответственности.
 */
//var result = 0
//
//fun sum(a: Int, b: Int): Int {
//    result = a + b
//    return result
//}

@Composable
fun SideEffectTest(number: MyNumber) {
    
    Column {
        LazyColumn {
            repeat(5) {
                item {
                    Text(text = "Number: ${number.a}")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))

        Handler().postDelayed({
            number.a = 5
        }, 3000)

        LazyColumn {
            repeat(5) {
                item {
                    Text(text = "Number: ${number.a}")
                }
            }
        }
    }

}

data class MyNumber(var a : Int) {

}
