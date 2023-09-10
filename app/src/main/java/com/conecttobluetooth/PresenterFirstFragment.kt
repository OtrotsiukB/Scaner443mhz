package com.conecttobluetooth

import com.conecttobluetooth.data.SignalData
import java.time.LocalDateTime

class PresenterFirstFragment {

   private var listSignalData: MutableList<SignalData>? = mutableListOf<SignalData>()

    var interfaceSendListInRv:iSendListInRv?=null

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