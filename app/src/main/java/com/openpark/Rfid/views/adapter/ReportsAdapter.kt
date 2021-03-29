package com.softray_solutions.newschoolproject.ui.activities.chart.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.openpark.Rfid.R
import com.openpark.Rfid.databinding.ItemReportsBinding
import com.openpark.Rfid.views.models.ModelReportsItem



class ReportsAdapter(private val context: Context, val list: List<ModelReportsItem>)
    : RecyclerView.Adapter<ReportsAdapter.MyViewHolder>() {



    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.binding.name.text=context.getString(R.string.name) +" "+ list[position].username
        holder.binding.phoneNumber.text=context.getString(R.string.phone) +" : "+ list[position].phone
        holder.binding.date.text=context.getString(R.string.date) +" : "+ list[position].trx_date
        holder.binding.balance.text=context.getString(R.string.balance) +" "+list[position].amount


    }



    fun addList(movielist: MutableList<ModelReportsItem>) {

       // this.chartList?.addAll(movielist)
        notifyDataSetChanged()
    }




    override fun getItemCount(): Int {

            return list?.size!!

    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_reports, parent, false)
        )
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemReportsBinding.bind(view)
    }
}