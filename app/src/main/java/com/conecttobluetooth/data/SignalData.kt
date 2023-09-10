package com.conecttobluetooth.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
//@Parcelize
//@Entity(tableName = "SignalDataDB")
class SignalData(
   // @PrimaryKey
    var name:String,

    var signal: MutableList<Int> = mutableListOf<Int>())//: Parcelable

