package com.example.p7meeting9.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p7meeting9.data.entity.Mahasiswa
import com.example.p7meeting9.repository.RepositoryMhs
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMhsViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMhs: RepositoryMhs
) : ViewModel() {

    var updateUIState by mutableStateOf(MhsUIState())
        private set

    private val _nim: String = checkNotNull(savedStateHandle[DestinasiEdit.NIM])

    init {
        viewModelScope.launch {
            updateUIState = repositoryMhs.getMhs(_nim)
                .filterNotNull()
                .first()
                .toUiStateMhs()
        }
    }

    fun updateState(mahasiswaEvent: MahasiswaEvent) {
        updateUIState = updateUIState.copy(
            mahasiswaEvent = mahasiswaEvent
        )
    }

    fun validateFields(): Boolean {
        val currentEvent = updateUIState.mahasiswaEvent
        val errorState = FormErrorState()

        val nim = if (currentEvent.nim.isNotEmpty()) null else "NIM tidak boleh kosong"
        val nama = if (currentEvent.nama.isNotEmpty()) null else "Nama tidak boleh kosong"
        val jenisKelamin = if (currentEvent.jenisKelamin.isNotEmpty()) null else "Jenis kelamin tidak boleh kosong"
        val alamat = if (currentEvent.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong"
        val kelas = if (currentEvent.kelas.isNotEmpty()) null else "Kelas tidak boleh kosong"
        val angkatan = if (currentEvent.angkatan.isNotEmpty()) null else "Angkatan tidak boleh kosong"

        updateUIState = updateUIState.copy(
            isEntryValid = errorState
        )

        return errorState.isValid()
    }

    fun updateData() {
        val currentEvent = updateUIState.mahasiswaEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMhs.updateMhs(currentEvent.toMahasiswaEntity())
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data berhasil diupdate",
                        isEntryValid = FormErrorState()
                    )
                    println("SnackBarMessage diatur: ${updateUIState.snackBarMessage}")
                } catch (e: Exception) {
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data gagal diupdate"
                    )
                }
            }
        } else {
            updateUIState = updateUIState.copy(
                snackBarMessage = "Data gagal diupdate"
            )
        }
    }

    fun resetSnackBarMessage(){
        updateUIState = updateUIState.copy(snackBarMessage = null)
    }
}
fun Mahasiswa.toUiStateMhs(): MhsUIState = MhsUIState(
    mahasiswaEvent = this.toDetailUiEvent(),
)
