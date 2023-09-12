package com.conecttobluetooth.data

object ConverterToSIgnalData {
    fun convertPersistensyToSIgnalData(signalName: SignalDataPersistensyName, signalLIst: List<SignalDataPersistensySignal>):SignalData{
        var nameTemp:String = ""
        var signal: MutableList<Int> = mutableListOf<Int>()
        nameTemp = signalName.name
        for( signalFor in signalLIst){
            signal.add(signalFor.signal)
        }
        val temp:SignalData = SignalData(nameTemp,signal)
        return temp
    }
}