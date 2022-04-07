package com.example.projectuas

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class itemList (
    var judul: String? = null,
    var tanggal: String? = null,
    var nominal: String? = null,
    var kategory: String? = "No Category"
)

class adapterHistory(private val listItem: ArrayList<itemList>) :
    RecyclerView.Adapter<adapterHistory.ListViewHolder>()
{
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val _judul = itemView.findViewById<TextView>(R.id.tvTitle)
        val _date = itemView.findViewById<TextView>(R.id.tvDate)
        val _nominal = itemView.findViewById<TextView>(R.id.tvValue)
        val _category = itemView.findViewById<TextView>(R.id.tvCategory)
        val _tvIn = itemView.findViewById<TextView>(R.id.tvIn)
        val _tvOut = itemView.findViewById<TextView>(R.id.tvOut)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.rvitem_layout, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val itemDetails = listItem[position]

        holder._tvIn.visibility = View.INVISIBLE
        holder._tvOut.visibility = View.INVISIBLE
        holder._judul.setText(itemDetails.judul)
        holder._date.setText(itemDetails.tanggal)
        if(itemDetails.kategory == "Pendapatan" ||
           itemDetails.kategory == "Pendapatan Sewa"||
           itemDetails.kategory == "Pendapatan Royalti" ||
           itemDetails.kategory == "Laba Penjualan") {
            holder._nominal.setTextColor(Color.parseColor("#FF99CC00"))
            holder._tvIn.visibility = View.VISIBLE
        }
        else if (itemDetails.kategory == "No Category"){

        }
        else{
            holder._nominal.setTextColor(Color.parseColor("#FFFF4444"))
            holder._tvOut.visibility = View.VISIBLE
        }
        holder._nominal.setText(itemDetails.nominal)
        holder._category.setText(itemDetails.kategory)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }
}