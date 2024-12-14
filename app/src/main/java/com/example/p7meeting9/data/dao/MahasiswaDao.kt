package com.example.p7meeting9.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.p7meeting9.data.entity.Mahasiswa
import kotlinx.coroutines.flow.Flow

@Dao
interface MahasiswaDao {
    @Insert
    suspend fun insertMahasiswa(
        mahasiswa: Mahasiswa
    )

    @Query("SELECT * FROM mahasiswa ORDER BY nama ASC")
    fun getALLMahasiswa(): Flow<List<Mahasiswa>>


}
