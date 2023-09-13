package com.conecttobluetooth.data

object ConvertSignalDataToPersistensy {
    fun convertSignalDataToPersistensyName(signalData: SignalData):SignalDataPersistensyName{
        val temp : SignalDataPersistensyName = SignalDataPersistensyName(null,signalData.name)
        return temp
    }
    fun convertSignalDataToPersistensySignalList(signalData: SignalData):MutableList<SignalDataPersistensySignal>{
        val tempSignalDataPersistensy = mutableListOf<SignalDataPersistensySignal>()
        val name = signalData.name
        for (signal in signalData.signal){
            var temp = SignalDataPersistensySignal(null,name,signal)
            tempSignalDataPersistensy.add(temp)
        }
        return tempSignalDataPersistensy
    }
}