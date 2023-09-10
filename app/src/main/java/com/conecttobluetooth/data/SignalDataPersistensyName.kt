package com.conecttobluetooth.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ListName")
data class SignalDataPersistensyName(
    @PrimaryKey(autoGenerate = true)
    var idKey:Int?,
    var name: String
)
