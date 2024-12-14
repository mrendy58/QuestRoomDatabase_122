package com.example.p7meeting9.repository

import com.example.p7meeting9.data.entity.Mahasiswa
import kotlinx.coroutines.flow.Flow

interface RepositoryMhs {
    suspend fun insertMhs(mahasiswa: Mahasiswa)

    //getAllMhs
    fun getAllMhs(): Flow<List<Mahasiswa>>

    //getMhs
    fun getMhs(nim: String): Flow<Mahasiswa>


}
