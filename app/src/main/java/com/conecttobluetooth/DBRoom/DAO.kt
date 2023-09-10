package com.conecttobluetooth.DBRoom

import androidx.room.*
import com.conecttobluetooth.data.SignalDataPersistensySignal
import com.conecttobluetooth.data.SignalDataPersistensyName


@Dao
interface DAO {


    @Query("SELECT * FROM ListName")
    suspend fun getAllSignalDataName(): List<SignalDataPersistensyName>
    @Insert
    fun insertSignalDataName(signalDataPersistensyName: SignalDataPersistensyName)
    @Query("DELETE FROM ListName")
    suspend fun deleteAllSignalDataName()
    @Delete
    suspend fun deleteSignalDataName(signalDataPersistensyName: SignalDataPersistensyName)
    @Update
    fun update(signalDataPersistensyName: SignalDataPersistensyName)

    @Query("SELECT * FROM ListSignal WHERE name == :name")
    suspend fun getSignalName(name: String):List<SignalDataPersistensySignal>

    @Insert
    fun insertSignalPersistensy(signal:SignalDataPersistensySignal)
    @Query("DELETE FROM ListSignal")
    suspend fun deleteAllSignalDataListSignal()
    @Delete
    suspend fun deleteSignalDataPersistensySignal(signalDataPersistensySignal: SignalDataPersistensySignal)
    @Update
    fun updateSignal(signalDataPersistensySignal: SignalDataPersistensySignal)




}