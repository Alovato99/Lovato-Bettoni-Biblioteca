package com.example.bibliotecabl.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliotecabl.Book
import com.example.bibliotecabl.R
import com.squareup.picasso.Picasso

class HorizontalItemAdapter(val items: ArrayList<Book>) : RecyclerView.Adapter<HorizontalItemAdapter.ViewHolder> (){



    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: HorizontalItemAdapter.ViewHolder, position: Int) {
        holder.horizontal_book_title.text = items[position].title
        val url = items.get(position).bookImageUrl
        Picasso.get().load(url).into(holder.horizontal_book_cover)
        //holder.horizontal_book_author.text = items[position].author

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalItemAdapter.ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.row_item_horizontal, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val horizontal_book_title : TextView = itemView.findViewById(R.id.horizontal_book_title)
        val horizontal_book_cover : ImageView = itemView.findViewById(R.id.horizontal_book_cover)
        //val horizontal_book_author : TextView = itemView.findViewById(R.id.horizontal_book_author)


    }
}

