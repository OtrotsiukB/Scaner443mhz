package com.conecttobluetooth

import com.conecttobluetooth.DBRoom.DatabaseR
import com.conecttobluetooth.data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class PresenterFirstFragment {

   private var listSignalData: MutableList<SignalData>? = mutableListOf<SignalData>()

    var interfaceSendListInRv:iSendListInRv?=null
    private var locationsDb: DatabaseR? = null

    fun attachDB() {
        locationsDb =
            interfaceSendListInRv?.giveContext()?.let { DatabaseR.create(it) }//база данных
    }

    fun presentLoad(){
        CoroutineScope(Dispatchers.IO).launch {
            var listSignalDataFromDB = getListSignalFromLocalDB()
            if (listSignalDataFromDB.size>1){
                listSignalData=listSignalDataFromDB
                //обновить список на фрагменте
                interfaceSendListInRv?.sendListinRv()
            }

        }
    }
    suspend fun getListSignalFromLocalDB():MutableList<SignalData>{
        var tempSignalData:MutableList<SignalData> = mutableListOf<SignalData>()
        var signalFromDb: List<SignalDataPersistensyName>? = locationsDb?.DaoInDB()?.getAllSignalDataName()
        if (signalFromDb != null)
        {
            for (signalName in signalFromDb){
                var templistSignals: List<SignalDataPersistensySignal>? =locationsDb?.DaoInDB()?.getSignalName(signalName.name)
                if (templistSignals!=null){
                   var temp = ConverterToSIgnalData.convertPersistensyToSIgnalData(signalName,templistSignals)
                   tempSignalData.add(temp)
               }
            }
        }else{
            return mutableListOf<SignalData>()
        }
        return tempSignalData
    }
    fun insertInDbSignalData(signalData: SignalData){
        CoroutineScope(Dispatchers.IO).launch {
            val signalDataPersistensyName = ConvertSignalDataToPersistensy.convertSignalDataToPersistensyName(signalData)
            locationsDb?.DaoInDB()?.insertSignalDataName(signalDataPersistensyName)
            val signalDataPersistensySignal = ConvertSignalDataToPersistensy.convertSignalDataToPersistensySignalList(signalData)
            for (s in signalDataPersistensySignal){
                locationsDb?.DaoInDB()?.insertSignalPersistensy(s)
            }
        }
    }

   fun getListSignalData():MutableList<SignalData>?{
       if (listSignalData==null){
           return mutableListOf<SignalData>()
       }
       else
       {
           return listSignalData
       }
   }
   fun getListSignalData(count:Int): SignalData {
       if (listSignalData!=null && listSignalData!!.count() >= count){
          return listSignalData?.get(count) ?: SignalData("NONE", mutableListOf())
       }else{
           return SignalData("NONE", mutableListOf())
       }
    }
   fun addListSignalData( signal: SignalData){
        listSignalData?.add(signal)
    }


    var tempSignal: MutableList<Int>? = mutableListOf()
    var switchWriteSignal = false
    fun setText(text: String) {
        val tempString = text.substring(0,text.indexOf("\n"))
        //val text2 = text.replace("\n".toRegex(), "")
        var temp: Int=0
        temp=tempString.toInt()


        if (temp==0){
            if (switchWriteSignal==true){
                switchWriteSignal=false
               // val tempSignalData = SignalData(LocalDateTime.now().toString(),tempSignal!!)
                try {
                   // val tempSignalData = SignalData("test", tempSignal!!)
                    val tempSignalData = SignalData(LocalDateTime.now().toString(), tempSignal!!)
                    addListSignalData(tempSignalData)
                    insertInDbSignalData(tempSignalData)
                    interfaceSendListInRv?.sendListinRv()
                }catch (e:Exception){

                }

                tempSignal=mutableListOf()
            }
        }
        else{
            switchWriteSignal=true
            tempSignal?.add(temp)


        }
    }


}