package com.conecttobluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.ParcelUuid
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


class BluetoorhConnection {

    var bluetooth = BluetoothAdapter.getDefaultAdapter()
    private lateinit var pairedDevices: Set<BluetoothDevice>
    private lateinit var targetDevice: BluetoothDevice

    private var mmSocket: BluetoothSocket? = null

    private var mmInStream: InputStream? = null
    private var mmOutStream: OutputStream? = null

    private var interfaceSendText:iSendText?=null
    fun setinterfaceSendText(iSendText: iSendText){
        interfaceSendText=iSendText
    }


    @SuppressLint("HardwareIds", "MissingPermission")
    fun startTest(): String {
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
                    val answer: String = status + "" + state
                    return answer
                    //Toast.makeText(activity, status, Toast.LENGTH_LONG).show()

                } else {
                    // Bluetooth выключен. Предложим пользователю включить его.
                    val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    //  activity?.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)


                }

            }
        } catch (e: IOException) {
        }
        return ""
    }

    @SuppressLint("MissingPermission")
    fun getBluetoothDeice(): Set<BluetoothDevice> {
        pairedDevices = bluetooth.getBondedDevices()
        return pairedDevices
    }

    fun setTargetDevice(device: BluetoothDevice) {
        targetDevice = device
    }

    fun getTargetDeice(): BluetoothDevice {
        return targetDevice
    }

    @SuppressLint("MissingPermission")
    fun clientConnect() {
        try {
            // MY_UUID это UUID, который используется и в сервере
            val uuids: Array<ParcelUuid> = targetDevice.getUuids()
            mmSocket = targetDevice.createRfcommSocketToServiceRecord(uuids[0].getUuid());
        } catch (e:IOException) {

        }
        try {
            bluetooth.cancelDiscovery();
        }catch (e:Exception){
            Log.i(null,"bluetooth.cancelDiscovery error")
        }
        CoroutineScope(Dispatchers.IO).launch {
            try {
              //  bluetooth.cancelDiscovery();
            }catch (e:Exception){
                Log.i(null,"bluetooth.cancelDiscovery error")
            }



            try {
                // Соединяемся с устройством через сокет.
                // Метод блокирует выполнение программы до
                // установки соединения или возникновения ошибки
                mmSocket!!.connect()
            } catch (connectException: IOException) {
                Log.i(null," mmSocket!!.connect()  error")
                // Невозможно соединиться. Закрываем сокет и выходим.
                try {
                    mmSocket!!.close()
                } catch (closeException: IOException) {
                }

            }

            // управлчем соединением (в отдельном потоке)
            manageConnectedSocket();
        }

    }
     fun manageConnectedSocket() {
        // Получить входящий и исходящий потоки данных
        try{
            mmInStream= mmSocket?.inputStream;
            mmOutStream= mmSocket?.outputStream;
            readBluetooth()

        } catch(e:IOException){
            Log.i(null," Получить входящий и исходящий потоки данных error")
        }


    }

     fun writeBluetooth(bytes: ByteArray?) {
         CoroutineScope(Dispatchers.IO).launch {
             try {
                 mmOutStream!!.write(bytes)
             } catch (e: IOException) {
                 Log.i(null," mmOutStream!!.write (bytes) error")
             }
         }
    }



    fun readBluetooth() {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.i(null,"  read(buffer)  start")
              //  val buffer = ByteArray(1024) // буферный массив

                var bytes: Int // bytes returned from read()


// Прослушиваем InputStream пока не произойдет исключение

// Прослушиваем InputStream пока не произойдет исключение
                while (true) {
                    try {
// читаем из InputStream
                        val buffer = ByteArray(1024)
                        bytes = mmInStream!!.read(buffer)
                        var str = String(buffer,Charsets.UTF_8)

                        // посылаем прочитанные байты главной деятельности
                        CoroutineScope(Dispatchers.Main).launch {
                         //   interfaceSendText?.setText(buffer.toString())
                            interfaceSendText?.setText(str)
                        }
                    } catch (e: IOException) {
                        Log.i(null,"  mmInStream!!.read(buffer)  error")

                        break
                    }
                }
            } catch (e: IOException) { }
        }
    }

    fun clientClose(){
        CoroutineScope(Dispatchers.IO).launch {
            mmSocket?.close();
        }
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