package com.conecttobluetooth

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
class SignalData(
    var name:String,
    var signal: MutableList<Int> = mutableListOf<Int>()): Parcelable

