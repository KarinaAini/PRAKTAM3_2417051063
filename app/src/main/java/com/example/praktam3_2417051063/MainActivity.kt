package com.example.praktam3_2417051063

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.Color
import com.example.praktam3_2417051063.model.SosialSource
import com.example.praktam3_2417051063.ui.theme.PRAKTAM3_2417051063Theme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PRAKTAM3_2417051063Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    val listSosial = SosialSource.dummySosial

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFFFF8E1)).padding(top = 40.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
    ) {

        listSosial.forEach { sosial ->

            Row(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {

                Image(
                    painter = painterResource(id = sosial.imageRes),
                    contentDescription = sosial.nama,
                    modifier = Modifier.size(90.dp).padding(end = 12.dp)
                )

                Column {
                    Text(text = sosial.nama)
                    Text(text = sosial.deskripsi)
                    Text(text = "Teman: ${sosial.teman}")

                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PRAKTAM3_2417051063Theme {
        Greeting()
    }
}