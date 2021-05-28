package com.example.bibliotecabl.ui.addbooks

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.icu.text.CaseMap
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
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_add_books.*
import java.text.SimpleDateFormat
import java.util.*


class AddBooksFragment: Fragment() {

    //private lateinit var addBooksViewModel: AddBooksViewModel
    //private val imageView: ImageView? = null


    private var selectedPhotoUri: Uri? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        /*addBooksViewModel =
                ViewModelProvider(this).get(AddBooksViewModel::class.java)*/
        val root = inflater.inflate(R.layout.fragment_add_books, container, false)
        val addCover : TextView=root.findViewById(R.id.select_photo_button)
        val addBook : Button = root.findViewById(R.id.addBookButton)
        /*val textView: TextView = root.findViewById(R.id.text_add_books)
        addBooksViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        //setContentView(R.layout.activity_test)


        val database = Firebase.database.reference

        addCover.setOnClickListener()
        {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        addBook.setOnClickListener()
        {
            /*val bookDBReference = FirebaseDatabase.getInstance().getReference("Books")
            bookDBReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    bookDBReference.child(email.replace(".", ",")).setValue(uid).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("emailToUid", "Added reference email to uid")
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })*/
            if (selectedPhotoUri != null) {
                val filename = UUID.randomUUID().toString()
                val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

                ref.putFile(selectedPhotoUri!!)
                        .addOnSuccessListener {
                            Log.d(TAG, "Successfully uploaded image: ${it.metadata?.path}")

                            ref.downloadUrl.addOnSuccessListener {
                                Log.d(TAG, "File Location: $it")

                                saveBookToFirebaseDatabase(it.toString())
                            }
                        }
                        .addOnFailureListener {
                            Log.d(TAG, "Failed to upload image to storage: ${it.message}")
                        }
            }
        }
        return root
    }

    private fun saveBookToFirebaseDatabase(bookImageUrl: String) {
        val bookDBReference = FirebaseDatabase.getInstance().getReference("Books")
        val bookUID = bookTitleEditText.text.toString().replace(" ", "-")+"-"+bookAuthorEditText.text.toString().replace(" ", "-") //EXAMPLE: "Rose-Viola-Nicola-Pascoli"
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        val book = Book(bookTitleEditText.text.toString(), bookAuthorEditText.text.toString(),bookDescEditText.text.toString(), bookImageUrl, currentDate)


        bookDBReference.child(bookUID).setValue(book)
                .addOnSuccessListener {
                    Log.d(TAG, "Finally we saved the user to Firebase Database")
                }
                .addOnFailureListener {
                    Log.d(TAG, "Failed to set value to database: ${it.message}")
                }
    }

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

class Book(val title: String, val author: String, val desc: String, val bookImageUrl: String, val currentDate: String)