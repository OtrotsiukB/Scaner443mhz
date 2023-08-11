package com.conecttobluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import java.io.IOException

class BluetoorhConnection {

    var bluetooth = BluetoothAdapter.getDefaultAdapter()
    private lateinit var pairedDevices: Set<BluetoothDevice>
    private lateinit var targetDevice:BluetoothDevice

    @SuppressLint("HardwareIds", "MissingPermission")
    fun startTest():String{
        try {


            if (bluetooth != null) {
                // С Bluetooth все в порядке.
                var status: String = ""
                if (bluetooth.isEnabled) {
                    // Bluetooth включен. Работаем.

                    if (bluetooth.isEnabled) {
                        val mydeviceaddress = bluetooth.address.toString()
                        val mydevicename = bluetooth.name.toString()
                        status = mydevicename + " " + mydeviceaddress
                    } else {
                        "Bluetooth выключен"
                    }
                    val state: String = bluetooth.state.toString()
                    val answer:String= status + "" + state
                    return answer
                    //Toast.makeText(activity, status, Toast.LENGTH_LONG).show()

                } else {
                    // Bluetooth выключен. Предложим пользователю включить его.
                    val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    //  activity?.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)


                }

            }
        }catch (e: IOException){}
        return ""
    }
    @SuppressLint("MissingPermission")
    fun getBluetoothDeice():Set<BluetoothDevice>{
         pairedDevices = bluetooth.getBondedDevices()
        return pairedDevices
    }
    fun setTargetDevice( device:BluetoothDevice){
        targetDevice = device
    }
    fun getTargetDeice():BluetoothDevice{
        return targetDevice
    }



}

/*
//check permision bluetooth in actiity
fun checkPermisionBluetooth(){
        if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            {
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.BLUETOOTH_CONNECT), 2)
                return
            }
        }
    }
 */