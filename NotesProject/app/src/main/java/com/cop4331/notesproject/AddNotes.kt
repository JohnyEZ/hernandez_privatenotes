package com.cop4331.notesproject

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.cop4331.notesproject.database.DatabaseHelper
import kotlinx.android.synthetic.main.activity_notes_add.*
import kotlinx.android.synthetic.main.notecard.*
import java.text.SimpleDateFormat
import java.util.*

class AddNotes : AppCompatActivity() {

    var edit = false
    var oldName = ""
    var oldDesc = ""
    var oldTag = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_add)

        var bundle = intent.extras
        val sdf = SimpleDateFormat("EEEE dd MMMM hh:mm:ss")
        val currentDate = sdf.format(Date())

        textViewDate.text = currentDate

        var suggestions = arrayOf("Grocery", "Important Information", "Account Passwords", "Todo List")
        var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, suggestions)
        noteTag.threshold = 0
        noteTag.setAdapter(adapter)
        noteTag.setOnFocusChangeListener { view, b -> if (b) noteTag.showDropDown() }

        edit = bundle!!.getBoolean("edit")
        if(edit) {
            noteName.setText(bundle.getString("Name").toString())
            noteDesc.setText(bundle.getString("Description").toString())
            noteTag.setText(bundle.getString("Label").toString())
            oldName = noteName.text.toString()
            oldDesc = noteDesc.text.toString()
            oldTag = noteTag.text.toString()
        }
    }

    override fun onResume() {
        super.onResume()
        var bundle = intent.extras

        edit = bundle!!.getBoolean("edit")
        if(edit) {
            noteName.setText(bundle.getString("Name").toString())
            noteDesc.setText(bundle.getString("Description").toString())
            noteTag.setText(bundle.getString("Label").toString())
            oldName = noteName.text.toString()
            oldDesc = noteDesc.text.toString()
            oldTag = noteTag.text.toString()
        }
    }

    fun endActivity(view: View){

        if(noteName.text.toString() != "" && noteDesc.text.toString() != "" && noteTag.text.toString() != "") {
            var dbManager = DatabaseHelper(this)
            var values = ContentValues()
            values.put("Name", noteName.text.toString())
            values.put("Description", noteDesc.text.toString())
            values.put("Label", noteTag.text.toString())

            if (edit) {
                var id = dbManager.getID(oldName, oldDesc, oldTag)
                dbManager.update(id, noteName.text.toString(), noteDesc.text.toString(), noteTag.text.toString())
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val ID = dbManager.insert(noteName.text.toString(), noteDesc.text.toString(), noteTag.text.toString())
                if (ID > 0) {
                    Toast.makeText(this, "Note created successfully", Toast.LENGTH_LONG).show()
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Failed to save note", Toast.LENGTH_LONG).show()
                }
            }
        }
        else{
            Toast.makeText(this, "Title and details must be filled", Toast.LENGTH_LONG).show()
        }
    }
}
