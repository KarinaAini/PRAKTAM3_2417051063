package com.example.praktam3_2417051063

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.praktam3_2417051063.network.RetrofitClient
import com.example.praktam3_2417051063.ui.theme.PRAKTAM3_2417051063Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.Sosial

val LocalNavController = compositionLocalOf<NavController> { error("No NavController") }
val LocalIsFullscreen = compositionLocalOf<Boolean> { false }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PRAKTAM3_2417051063Theme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var sosials by remember { mutableStateOf<List<Sosial>>(emptyList()) }

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                CompositionLocalProvider(LocalIsFullscreen provides false) {
                    SosialScreen(
                        sosialList = sosials,
                        onDataLoaded = { fetchedData ->
                            sosials = fetchedData
                        }
                    )
                }
            }

            composable("detail/{nama}") { backStackEntry ->
                val nama = backStackEntry.arguments?.getString("nama")
                val sosial = sosials.find { it.nama == nama }

                sosial?.let {
                    CompositionLocalProvider(LocalIsFullscreen provides true) {
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .statusBarsPadding()
                        ) {
                            DetailScreen(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SosialScreen(
    sosialList: List<Sosial>,
    onDataLoaded: (List<Sosial>) -> Unit
) {
    var isLoading by remember { mutableStateOf(sosialList.isEmpty()) }
    var isError by remember { mutableStateOf(false) }
    var errorDetail by remember { mutableStateOf("") }
    var retryTrigger by remember { mutableStateOf(0) }

    LaunchedEffect(retryTrigger) {
        if (sosialList.isEmpty() || retryTrigger > 0) {
            try {
                val data = RetrofitClient.instance.getSosial()
                Log.d("API_RESULT", "Data = $data")
                Log.d("API_SIZE", "Jumlah = ${data.size}")
                onDataLoaded(data)
                isLoading = false
                isError = false
            } catch (e: Exception) {
                isLoading = false
                isError = true
                errorDetail = e.localizedMessage ?: e.toString()
                Log.e("API_ERROR", "Detail Error", e)
            }
        }
    }

    Surface(color = MaterialTheme.colorScheme.background) {
        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            isError -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Gagal Memuat Data",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = errorDetail,
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                isLoading = true
                                isError = false
                                retryTrigger++
                            }
                        ) {
                            Text("Coba Lagi")
                        }
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding(),
                    contentPadding = PaddingValues(bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Column(
                            modifier = Modifier.padding(
                                horizontal = 20.dp,
                                vertical = 10.dp
                            )
                        ) {
                            Text(
                                "Rekomendasi Teman",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(sosialList) { sosial ->
                                    SosialRowItem(sosial)
                                }
                            }
                        }
                    }

                    item {
                        Text(
                            "Daftar Teman Lengkap",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(
                                horizontal = 20.dp,
                                vertical = 8.dp
                            )
                        )
                    }

                    items(sosialList) { sosial ->
                        Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                            DetailScreen(sosial)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SosialRowItem(sosial: Sosial) {
    val navController = LocalNavController.current

    Card(
        modifier = Modifier
            .width(160.dp)
            .clickable {
                navController.navigate("detail/${sosial.nama}")
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            AsyncImage(
                model = getDrawableRes(sosial.imageName),
                contentDescription = sosial.nama,
                placeholder = painterResource(id = R.drawable.belajar),
                error = painterResource(id = R.drawable.bareng),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = sosial.nama,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Aktif",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun DetailScreen(sosial: Sosial) {
    val navController = LocalNavController.current
    val isFullscreen = LocalIsFullscreen.current

    var isFavorite by remember { mutableStateOf(false) }
    var isAddingFriend by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Box(
        modifier = if (isFullscreen) Modifier.fillMaxSize()
        else Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = if (isFullscreen) {
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            } else {
                Modifier.fillMaxWidth()
            }
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column {
                    Box {
                        AsyncImage(
                            model = getDrawableRes(sosial.imageName),
                            contentDescription = sosial.nama,
                            placeholder = painterResource(id = R.drawable.belajar),
                            error = painterResource(id = R.drawable.bareng),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(if (isFullscreen) 300.dp else 200.dp),
                            contentScale = ContentScale.Crop
                        )

                        IconButton(
                            onClick = { isFavorite = !isFavorite },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                                .background(
                                    Color.Black.copy(alpha = 0.3f),
                                    CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = if (isFavorite)
                                    Icons.Filled.Favorite
                                else
                                    Icons.Outlined.FavoriteBorder,
                                contentDescription = null,
                                tint = if (isFavorite) Color.Red else Color.White
                            )
                        }

                        if (isFullscreen) {
                            IconButton(
                                onClick = { navController.popBackStack() },
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(8.dp)
                                    .background(
                                        Color.Black.copy(alpha = 0.3f),
                                        CircleShape
                                    )
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = Color.White
                                )
                            }
                        }
                    }

                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = sosial.nama,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = sosial.deskripsi,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )

                        Text(
                            text = "Teman: ${sosial.teman}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                if (isFullscreen) {
                                    coroutineScope.launch {
                                        isAddingFriend = true
                                        delay(2000)
                                        isAddingFriend = false
                                        snackbarHostState.showSnackbar(
                                            "Teman ${sosial.nama} berhasil ditambahkan"
                                        )
                                    }
                                } else {
                                    navController.navigate("detail/${sosial.nama}")
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !isAddingFriend
                        ) {
                            if (isAddingFriend) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text(
                                    if (isFullscreen)
                                        "Tambah Teman Sekarang"
                                    else
                                        "Cari Teman Kelompok"
                                )
                            }
                        }

                        if (isFullscreen) {
                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedButton(
                                onClick = { navController.popBackStack() },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Kembali")
                            }
                        }
                    }
                }
            }
        }

        if (isFullscreen) {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 140.dp)
            )
        }
    }
}

fun getDrawableRes(name: String): Int {
    return when (name) {
        "belajar" -> R.drawable.belajar
        "bareng" -> R.drawable.bareng
        "kompak" -> R.drawable.kompak
        "lokasi" -> R.drawable.lokasi
        else -> R.drawable.belajar
    }
}