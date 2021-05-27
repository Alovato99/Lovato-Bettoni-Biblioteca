package com.example.bibliotecabl.ui.addbooks

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bibliotecabl.R
import kotlinx.android.synthetic.main.fragment_add_books.*


class AddBooksFragment: Fragment() {

    //private lateinit var addBooksViewModel: AddBooksViewModel
    //private val imageView: ImageView? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        /*addBooksViewModel =
                ViewModelProvider(this).get(AddBooksViewModel::class.java)*/
        val root = inflater.inflate(R.layout.fragment_add_books, container, false)
        val addCover:TextView=root.findViewById(R.id.select_photo_button)
        /*val textView: TextView = root.findViewById(R.id.text_add_books)
        addBooksViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        //setContentView(R.layout.activity_test)
        addCover.setOnClickListener()
        {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
        return root
    }
    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            // proceed and check what the selected image was....
            Log.d(TAG, "Photo was selected")

            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, selectedPhotoUri)

            imageIcon.setImageBitmap(bitmap)

            select_photo_button.alpha = 0f
        }
    }


}