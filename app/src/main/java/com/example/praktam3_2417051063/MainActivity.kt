package com.example.praktam3_2417051063

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import model.SosialSource
import model.Sosial
import com.example.praktam3_2417051063.ui.theme.PRAKTAM3_2417051063Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PRAKTAM3_2417051063Theme {
                SosialScreen()
            }
        }
    }
}

@Composable
fun SosialScreen() {
    androidx.compose.material3.Surface(
        color = androidx.compose.ui.graphics.Color(0xFFFFF5E1)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp, start = 20.dp, end = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Trouver des amis",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(10.dp))

            SosialSource.dummySosial.forEach { sosial ->
                DetailScreen(sosial = sosial)
                Spacer(modifier = Modifier.height(8.dp))
            }

        }
    }
}

@Composable
fun DetailScreen(sosial: Sosial) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {

        androidx.compose.material3.Card(
            shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Image(
                painter = painterResource(id = sosial.imageRes),
                contentDescription = sosial.nama,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = sosial.nama,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = sosial.deskripsi,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Teman: ${sosial.teman}",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = androidx.compose.ui.graphics.Color(0xFF355E3B)
            )
        ) {
            Text(
                text = "Cari Teman Kelompok",
                fontWeight = FontWeight.Bold
            )
        }
    }
}

