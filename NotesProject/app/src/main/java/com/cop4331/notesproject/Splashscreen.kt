package com.cop4331.notesproject

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cop4331.notesproject.pinHelper.Pin
import com.cop4331.notesproject.sharedPref.SharedPrefHandler
import kotlinx.android.synthetic.main.activity_pin.toolbar

class Splashscreen : AppCompatActivity() {

    val context = this
    lateinit var dialog: AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        setSupportActionBar(toolbar)

        val sharedPref = SharedPrefHandler(this)
        var pin:Int = sharedPref.getPin()
        var access = sharedPref.getAccess()

        if(pin.equals(0) && !access){ //default is 0
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Setup a pin?")
            val dialogClickListener = DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        var intent = Intent(this, Pin::class.java)
                        startActivity(intent)
                        finish()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        sharedPref.setPin(-1)
                        var intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
            builder.setPositiveButton("Now", dialogClickListener)
            builder.setNegativeButton("Later", dialogClickListener)
            dialog = builder.create()
            dialog.show()
            dialog.dismiss()

        }
        else if(pin.equals(-1) && !access){ // Chose not to set the pin
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else if(access){ // Pin got setup
            var intent = Intent(this, Pin::class.java)
            startActivity(intent)
            finish()
        }
        else{ // Pin
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

//    override fun onDestroy() {
//        super.onDestroy()
//        if(dialog != null) {
//            dialog.hide()
//        }
//    }

    override fun onPause() {
        super.onPause()
        if(dialog != null) {
            dialog.hide()
        }
    }

    override fun onResume() {
        super.onResume()

        val sharedPref = SharedPrefHandler(this)
        var pin:Int = sharedPref.getPin()

        if(pin.equals(0)){
            lateinit var dialog: AlertDialog
            // Initialize a new instance of alert dialog builder object
            val builder = AlertDialog.Builder(context)
            // Set a title for alert dialog
            builder.setTitle("Setup a pin?")
            // Set a message for alert dialog
            // On click listener for dialog buttons
            val dialogClickListener = DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        var intent = Intent(this, Pin::class.java)
                        startActivity(intent)
                        finish()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        sharedPref.setPin(-1)
                        var intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
            builder.setPositiveButton("Now", dialogClickListener)
            // Set the alert dialog negative/no button
            builder.setNegativeButton("Later", dialogClickListener)
            // Initialize the AlertDialog using builder object
            dialog = builder.create()
            // Finally, display the alert dialog
            dialog.show()

        }
        else if(pin.equals(-1)){
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{
            var intent = Intent(this, Pin::class.java)
            startActivity(intent)
            finish()
        }
    }

}
