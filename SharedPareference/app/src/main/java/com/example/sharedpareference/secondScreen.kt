package com.example.sharedpareference

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class SecondScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecondScreenData(context = applicationContext)
        }
    }

    @Composable
    fun SecondScreenData(context: Context) {
        val sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val name = remember { sharedPreferences.getString("Name", "No Data in SharedPreferences") ?: "Null" }
        val fatherName =
            remember { sharedPreferences.getString("FatherName", "No Data in SharedPreferences") ?: "Null" }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Name: $name",
                modifier = Modifier
                    .padding(top = 200.dp)
            )
            Text(
                text = "Father Name: $fatherName",
                modifier = Modifier
                    .padding(top = 50.dp)
            )

        }
    }
}
