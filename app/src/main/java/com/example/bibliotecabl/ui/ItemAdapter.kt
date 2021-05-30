package com.example.bibliotecabl.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliotecabl.R

class ItemAdapter(val items: ArrayList<String>) : RecyclerView.Adapter<ItemAdapter.ViewHolder> (){



    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
        holder.firstItem.text = items[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstItem: TextView = itemView.findViewById(R.id.firstItem)

    }
}

