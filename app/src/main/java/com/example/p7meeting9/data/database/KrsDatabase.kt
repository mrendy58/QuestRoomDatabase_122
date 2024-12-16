package com.example.p7meeting9.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.p7meeting9.data.dao.MahasiswaDao
import com.example.p7meeting9.data.entity.Mahasiswa


//mendefinisikan database dengan table Mahasiswa
@Database(entities = [Mahasiswa::class], version = 1, exportSchema = false)
 abstract class KrsDatabase : RoomDatabase() {

     //mendefinisikan fungsi untuk mengakses data mahasiswa
     abstract  fun mahasiswaDao(): MahasiswaDao

     companion object{
         @Volatile// mendefinisikan bahwa nilai variable instance selalu sama di semua thread
         private var Instance: KrsDatabase? = null

         fun getDatabase(context:Context): KrsDatabase {
             return (Instance?: synchronized(this){
                 Room.databaseBuilder(
                     context,
                     KrsDatabase::class.java, // class database
                     "KrsDatabase"
                 )
                     .build().also { Instance= it }
             })
         }
     }
}