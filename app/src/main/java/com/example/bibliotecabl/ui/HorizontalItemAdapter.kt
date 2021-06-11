package com.example.bibliotecabl.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliotecabl.R

class HorizontalItemAdapter(val items: ArrayList<String>) : RecyclerView.Adapter<HorizontalItemAdapter.ViewHolder> (){



    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: HorizontalItemAdapter.ViewHolder, position: Int) {
            holder.horizontal_item.text = items[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalItemAdapter.ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.row_item_horizontal, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val horizontal_item: TextView = itemView.findViewById(R.id.firstItem)


    }
}

