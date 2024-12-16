package com.example.p7meeting9.ui.view.mahasiswa

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.p7meeting9.data.entity.Mahasiswa
import com.example.p7meeting9.ui.costumWidget.TopAppBar
import com.example.p7meeting9.ui.viewmodel.HomeMhsViewModel
import com.example.p7meeting9.ui.viewmodel.HomeUiState
import com.example.p7meeting9.ui.viewmodel.PenyediaViewModel

@Composable
fun HomeMhsView(
    modifier: Modifier = Modifier,
    viewModel: HomeMhsViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddMhs: () -> Unit,
    onBack: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onDetailClick: (String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                judul = "Daftar Mahasiswa",
                showBackButton = true,
                onBack = onBack,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Mahasiswa"
                )
            }
        }
    ) { innerPadding ->
        val homeUiState by viewModel.homeUiState.collectAsState()
        BodyHomeMhs(
            modifier = Modifier.padding(innerPadding),
            homeUiState = homeUiState,
            onDetailClick = onDetailClick
        )
    }
}


@Composable
fun BodyHomeMhs(
    modifier: Modifier = Modifier,
    homeUiState: HomeUiState = HomeUiState(),
    onDetailClick: (String) -> Unit = {}
) {
    when {
        homeUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        homeUiState.listMhs.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada data mahasiswa.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        else -> {
            ListMahasiswa(
                modifier = modifier,
                listMhs = homeUiState.listMhs,
                onClick = onDetailClick
            )
        }
    }
}

@Composable
fun ListMahasiswa(
    modifier: Modifier = Modifier,
    listMhs: List<Mahasiswa>,
    onClick: (String) -> Unit = {}
) {
    LazyColumn(modifier = modifier) {
        items(listMhs) { mhs ->
            CardMhs(
                mhs = mhs,
                onClick = { onClick(mhs.nim) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardMhs(
    mhs: Mahasiswa,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Person, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = mhs.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = mhs.nim,
                    fontSize = 14.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Home, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = mhs.kelas,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }
        }
    }
}
