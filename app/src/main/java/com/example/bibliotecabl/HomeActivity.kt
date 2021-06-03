package com.example.bibliotecabl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LiveData
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.nav_header_main.*


class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var completeName: String =""
    private val database = Firebase.database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = Firebase.auth

        val currentUser = auth.currentUser
        val uid = currentUser!!.uid


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)


        val navView: NavigationView = findViewById(R.id.nav_view_admin)
        val navController = findNavController(R.id.nav_host_fragment)


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        appBarConfiguration = AppBarConfiguration(setOf())


        database.child("Users").child(uid).child("admin").get().addOnSuccessListener {
            if(it.getValue().toString().toBoolean())
            {
                //Creo menu utente admin
                appBarConfiguration = AppBarConfiguration(setOf(
                        R.id.nav_home, R.id.nav_rentals, R.id.nav_settings, R.id.nav_browse_books,R.id.nav_add_books,R.id.nav_remove_books,R.id.nav_manage_admins), drawerLayout)

                nav_view_admin.inflateMenu(R.menu.activity_main_drawer_admin)

            }
            else
            {
                //Creo menu utente normale
                appBarConfiguration = AppBarConfiguration(setOf(
                        R.id.nav_home, R.id.nav_rentals, R.id.nav_settings, R.id.nav_browse_books), drawerLayout)

            }


    }.addOnFailureListener{
            appBarConfiguration = AppBarConfiguration(setOf(
                    R.id.nav_home, R.id.nav_rentals, R.id.nav_settings, R.id.nav_browse_books), drawerLayout)
    }

        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_rentals, R.id.nav_settings, R.id.nav_browse_books,R.id.nav_add_books,R.id.nav_remove_books,R.id.nav_manage_admins), drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



        database.child("Users").child(uid).child("name").get().addOnSuccessListener {
            completeName = it.getValue().toString()
            Log.e("firebase", "Getting data ${it.getValue()}")

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }


        database.child("Users").child(uid).child("surname").get().addOnSuccessListener {
            completeName += " " + it.value.toString()


        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }



    }
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null)
        val currentUser = auth.currentUser
        if(currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)

            startActivity(intent)
            finish()
        }
    }





    override fun onSupportNavigateUp(): Boolean {
          val navController = findNavController(R.id.nav_host_fragment)

//        val currentUser = auth.currentUser
//        val uid = currentUser!!.uid
//        var name : String = ""
//        //val database = FirebaseDatabase.getInstance().getReference()
//        val database = Firebase.database.reference
//        database.child("Users").child(uid).child("name").get().addOnSuccessListener {
//            name = it.getValue().toString()
//            Log.e("firebase", "Getting data ${it.getValue()}")
//
//        }.addOnFailureListener{
//            Log.e("firebase", "Error getting data", it)
//        }
//
//
//        database.child("Users").child(uid).child("surname").get().addOnSuccessListener {
//            //name += " " + it.value.toString()
//            homeName.setText(name + " " + it.getValue().toString())
//
//        }.addOnFailureListener{
//            Log.e("firebase", "Error getting data", it)
//        }

        homeName.setText(completeName)
        homeEmail.setText(auth.currentUser?.email.toString())


        val ref=FirebaseDatabase.getInstance().reference.child("Users")
        val childEventListener = object:ChildEventListener
        {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

                completeName=snapshot.child("name").value.toString()+" "+snapshot.child("surname").value.toString()
                homeName.setText(completeName)
                homeEmail.setText(auth.currentUser?.email.toString())
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }

        }
        ref.addChildEventListener(childEventListener)




        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    }
    /*fun updateInformation(fullname:String,email:String)
    {
        Toast.makeText(
                baseContext, R.string.accountAlreadyExists,
                Toast.LENGTH_SHORT
        ).show()
        homeName.setText(fullname)
        homeEmail.setText(email)
    }*/
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.activity_main_logout, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_logout) {
            auth.signOut()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}