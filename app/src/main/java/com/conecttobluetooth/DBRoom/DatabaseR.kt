package com.conecttobluetooth.DBRoom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.conecttobluetooth.data.SignalData
import com.conecttobluetooth.data.SignalDataPersistensyName
import com.conecttobluetooth.data.SignalDataPersistensySignal

@Database(entities = [SignalDataPersistensyName::class,SignalDataPersistensySignal::class], version = 1)
abstract class DatabaseR : RoomDatabase(){



    abstract fun DaoInDB():DAO


    companion object {
        private const val DATABASE_NAME = "SignalDB.db"

        fun create(applicationContext: Context): DatabaseR = Room.databaseBuilder(
            applicationContext,
            DatabaseR::class.java,
            DATABASE_NAME
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    }




}