package com.conecttobluetooth.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "ListSignal")
data class SignalDataPersistensySignal (
    @PrimaryKey(autoGenerate = true)
    var idKey:Int?,
    var name: String,
    var signal: Int
        )