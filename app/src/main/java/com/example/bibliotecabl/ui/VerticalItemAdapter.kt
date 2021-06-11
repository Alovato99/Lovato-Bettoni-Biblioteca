package com.example.bibliotecabl.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliotecabl.Book
import com.example.bibliotecabl.R

class VerticalItemAdapter(val items: ArrayList<Book>) : RecyclerView.Adapter<VerticalItemAdapter.ViewHolder> (){



    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VerticalItemAdapter.ViewHolder, position: Int) {
        holder.vertical_item.text = items.get(position).title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalItemAdapter.ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.row_item_vertical, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vertical_item: TextView = itemView.findViewById(R.id.bookName)

    }
}
