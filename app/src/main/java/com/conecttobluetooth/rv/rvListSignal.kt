package com.conecttobluetooth.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.conecttobluetooth.R
import com.conecttobluetooth.data.SignalData

class rvListSignal(private val listener: OnItemClickListener): RecyclerView.Adapter<rvListSignal.EmptyViewHolder>() {


    private var data = mutableListOf<SignalData>()

    interface OnItemClickListener {
        fun onItemClick(data: SignalData)
    }
    fun setData(data: MutableList<SignalData>) {
        this.data = data
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmptyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.holder_list_signal,
            parent,
            false
        )
        return EmptyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: EmptyViewHolder, position: Int) {


        holder.onBind(data[position],listener)


    }

    override fun getItemCount(): Int {
        return data.count()
    }
    override fun getItemViewType(position: Int): Int {

        return 0
    }

    class EmptyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val textViewNumber = itemView.findViewById<TextView>(R.id.textViewNumber)
        val textViewSignal = itemView.findViewById<TextView>(R.id.textViewSignal)

        fun onBind(data: SignalData, listener: OnItemClickListener) {

            var textViewSignalString:String = ""
            for (info in data.signal){
                textViewSignalString= textViewSignalString+ info.toString()+","
            }
            textViewSignal.text=textViewSignalString
            textViewNumber.text=data.name

            itemView.setOnClickListener { listener.onItemClick(data) }

        }
    }
}