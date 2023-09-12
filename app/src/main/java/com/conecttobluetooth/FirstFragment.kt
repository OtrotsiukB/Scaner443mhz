package com.conecttobluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.conecttobluetooth.data.SignalData
import com.conecttobluetooth.databinding.FragmentFirstBinding
import com.conecttobluetooth.rv.rvListSignal
import android.content.Context


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(),iSendText,rvListSignal.OnItemClickListener,iSendListInRv {

    override fun setText(text:String){

        try {
            binding.textviewFirst.text = text.toString()

        }catch (e:Exception){
            Log.i(null,"  binding.textviewFirst.text  error")
        }
        present.setText(text)
    }


    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var bluetooth = BluetoothAdapter.getDefaultAdapter()
    var mybluetooth:BluetoorhConnection= BluetoorhConnection()

    var pairedDevices: Set<BluetoothDevice>? = null
    private val signalAdapter: rvListSignal = rvListSignal(this)

    val present = PresenterFirstFragment()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mybluetooth.setinterfaceSendText(this)
        present.interfaceSendListInRv=this
        present.attachDB()
        present.presentLoad()
      /*  binding.rvMainCategory.layoutManager = mLayoutManager
        categorryAdapter.setData(dataCategoryMain.getCategory())
        binding.rvMainCategory.adapter=categorryAdapter*/
        val mLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,true)
        binding.rvListSignal.layoutManager = mLayoutManager

        binding.rvListSignal.adapter=signalAdapter

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        /*binding.buttonStart1.setOnClickListener{
           //startTest()
            binding.textviewFirst.text=mybluetooth.startTest()
        }*/
        binding.buttonStep2.setOnClickListener {
            //step2()
           pairedDevices = mybluetooth.getBluetoothDeice()
            if (pairedDevices!=null) {
                for (device in pairedDevices!!) {
                    if (device.name.toString()=="HC-06"){
                        mybluetooth.setTargetDevice(device)
                        binding.textviewFirst.text=device.name.toString()+" "+device.address

                    }
                }
            }
            mybluetooth.clientConnect()
            binding.buttonStep2.visibility= View.INVISIBLE
        }

        binding.buttonConnectClose.setOnClickListener {
            mybluetooth.clientClose()
            binding.buttonStep2.visibility= View.VISIBLE
        }
        binding.buttonSend0.setOnClickListener {
           // mybluetooth.writeBluetooth(0)
            val byteArray = byteArrayOf(0.toByte())
         //   val byteArray = byteArrayOf(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
            mybluetooth.writeBluetooth(byteArray)

            present.getListSignalData()?.let { it1 -> signalAdapter.setData(it1) }

        }
        binding.buttonSend1.setOnClickListener {
           // mybluetooth.writeBluetooth(1)
            val byteArray = byteArrayOf(1.toByte())
           // val byteArray = byteArrayOf(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1)
            mybluetooth.writeBluetooth(byteArray)
        }
    }
    @SuppressLint("MissingPermission")
    fun step2(){
        val pairedDevices: Set<BluetoothDevice> = bluetooth.getBondedDevices()
// Если список спаренных устройств не пуст
// Если список спаренных устройств не пуст
        if (pairedDevices.size > 0) {
// проходимся в цикле по этому списку
            var listOfDeiceBonded:String=""
            for (device in pairedDevices) {
// Добавляем имена и адреса в mArrayAdapter, чтобы показать
// через ListView
                listOfDeiceBonded=listOfDeiceBonded+" "+device.name.toString()+" "+device.address+"\n"
              /*  mArrayAdapter.add(
                    """
            ${device.name}
            ${device.address}
            """.trimIndent()
                )*/
            }
            binding.textviewFirst.text=listOfDeiceBonded
        }
    }
     @SuppressLint("MissingPermission")
     fun startTest(){
        if(bluetooth!=null)
        {
        // С Bluetooth все в порядке.
            var status: String =""
            if (bluetooth.isEnabled) {
                // Bluetooth включен. Работаем.

                 if (bluetooth.isEnabled) {
                    val mydeviceaddress = bluetooth.address.toString()
                    val mydevicename = bluetooth.name.toString()
                     status =  mydevicename +" "+mydeviceaddress
                } else {
                    "Bluetooth выключен"
                }
                val state: String = bluetooth.state.toString()
                binding.textviewFirst.text=status +""+state
                //Toast.makeText(activity, status, Toast.LENGTH_LONG).show()

            } else {
                // Bluetooth выключен. Предложим пользователю включить его.
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
              //  activity?.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)


            }

        }
    }

    override fun giveContext():Context{
        val con = requireContext().applicationContext
        return con
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(data: SignalData) {
        var bundle = bundleOf("Signal" to data)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment,bundle)
    }

    override fun sendListinRv() {
        val temp = present.getListSignalData()
        if (temp != null) {
            signalAdapter.setData(temp)
            binding.rvListSignal.adapter=signalAdapter
        }
       // present.getListSignalData()?.let { signalAdapter.setData(it) }
    }
}