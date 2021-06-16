package com.example.bibliotecabl.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliotecabl.Book
import com.example.bibliotecabl.LoginActivity
import com.example.bibliotecabl.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_item_vertical.*


class VerticalItemAdapter(val items: MutableList<Book>) : RecyclerView.Adapter<VerticalItemAdapter.ViewHolder> (){


    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.vertical_item_title.text = items.get(position).title
        holder.vertical_item_author.text = items.get(position).author
        holder.vertical_item_copies.text = "Copie: "+ items.get(position).copies
        val bookID = items.get(position).title.replace(" ", "-") + "-" + items.get(position).author.replace(" ", "-")
        val url = items.get(position).bookImageUrl
        Picasso.get().load(url).into(holder.vertical_item_cover)
        //val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        //holder.vertical_item_cover.setImageBitmap(bmp)


        holder.vertical_layout.setOnClickListener(){
            val intent = Intent(it.context, BookActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, bookID)
            }
            startActivity(it.context, intent, null)
        }


    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalItemAdapter.ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.row_item_vertical, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vertical_item_title: TextView = itemView.findViewById(R.id.bookName)
        val vertical_item_author: TextView = itemView.findViewById(R.id.bookAuthor)
        val vertical_item_cover : ImageView = itemView.findViewById(R.id.bookCover)
        val vertical_item_copies : TextView = itemView.findViewById(R.id.bookCopies)
        val vertical_layout : LinearLayout = itemView.findViewById(R.id.verticalBooks)

    }
}
