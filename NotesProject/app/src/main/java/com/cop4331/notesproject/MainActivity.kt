package com.cop4331.notesproject

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cop4331.notesproject.database.DatabaseHelper
import com.cop4331.notesproject.model.note
import com.cop4331.notesproject.pinHelper.Pin
import com.cop4331.notesproject.pinHelper.PinReset
import com.cop4331.notesproject.sharedPref.SharedPrefHandler
import com.cop4331.notesproject.adapter.NoteAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_notes_add.*
import android.widget.ArrayAdapter

class MainActivity : AppCompatActivity() {

    var listNotes = ArrayList<note>()
    val context = this
    var adapter = NoteAdapter(listNotes,context)
    var defaultLayout = 0
    var mSharedPref:SharedPreferences?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var sharedPref = SharedPrefHandler(context)

        //sorting sharedPref
        this.mSharedPref = this.getSharedPreferences("My_Data", android.content.Context.MODE_PRIVATE)
        //load sorting technique
        val mSorting = mSharedPref!!.getString("Sort", "newest")
        when(mSorting) {
            "newest" -> loadQueryNewest("%")
            "oldest" -> loadQueryOldest("%")
            "ascending" -> loadQueryAscending("%")
            "descending" -> loadQueryDescending("%")
            "category" -> loadQueryCategory("%")
        }

        var DatabaseHelper = DatabaseHelper(this)
        val cursor = DatabaseHelper.getNotes()

        if(sharedPref.getLayout() == "Linear"){
            println("layout is linear")
            recycler_view.layoutManager = LinearLayoutManager(this)
        }
        else{
            println("layout is grid")
            recycler_view.layoutManager = GridLayoutManager(this, 2)
        }


        LockBtnSetting.setOnClickListener{

            var intent = Intent(this, AddNotes::class.java)
            intent.putExtra("edit", false)
            intent.putExtra("Name", "")
            intent.putExtra("Description", "")
            intent.putExtra("Label", "")
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        var DatabaseHelper = DatabaseHelper(this)
        val cursor = DatabaseHelper.getNotes()
        var sharedPref = SharedPrefHandler(context)

        listNotes.clear()

        //load sorting technique
        val mSorting = mSharedPref!!.getString("Sort", "newest")
        when(mSorting) {
            "newest" -> loadQueryNewest("%")
            "oldest" -> loadQueryOldest("%")
            "ascending" -> loadQueryAscending("%")
            "descending" -> loadQueryDescending("%")
            "category" -> loadQueryCategory("%")
        }

        if(sharedPref.getLayout() == "Linear"){
            println("On resume layout is linear")
            recycler_view.layoutManager = LinearLayoutManager(this)
        }
        else{
            println("On resume layout is grid")
            recycler_view.layoutManager = GridLayoutManager(this, 2)
        }
        recycler_view.setHasFixedSize(true)
    }

    private fun loadQueryAscending(title: String) {
        var databaseHelper = DatabaseHelper(this)
        val projections = arrayOf("ID", "Name", "Description", "Label")
        val selectionArgs = arrayOf(title)
        //sort by ID
        val cursor = databaseHelper.query(projections, "ID like ?", selectionArgs, "Name")
        listNotes.clear()
        //Descending
        if(cursor.moveToFirst()) {
            do {
                println(cursor.getString(0))
                println(cursor.getString(1))
                println(cursor.getString(2))
                println(cursor.getString(3))
                listNotes.add(note(cursor.getString(1), cursor.getString(2), cursor.getString(3)))
            } while (cursor.moveToNext())
            recycler_view.adapter = adapter
        }
    }

    private fun loadQueryDescending(title: String) {
        var databaseHelper = DatabaseHelper(this)
        val projections = arrayOf("ID", "Name", "Description", "Label")
        val selectionArgs = arrayOf(title)
        //sort by ID
        val cursor = databaseHelper.query(projections, "ID like ?", selectionArgs, "Name")
        listNotes.clear()
        //Descending
        if(cursor.moveToLast()) {
            do {
                println(cursor.getString(0))
                println(cursor.getString(1))
                println(cursor.getString(2))
                println(cursor.getString(3))
                listNotes.add(note(cursor.getString(1), cursor.getString(2), cursor.getString(3)))
            } while (cursor.moveToPrevious())
            recycler_view.adapter = adapter
        }
    }


    private fun loadQueryNewest(title: String) {
        var databaseHelper = DatabaseHelper(this)
        val projections = arrayOf("ID", "Name", "Description", "Label")
        val selectionArgs = arrayOf(title)
        //sort by ID
        val cursor = databaseHelper.query(projections, "ID like ?", selectionArgs, "ID")
        listNotes.clear()
        //Descending
        if(cursor.moveToLast()) {
            do {
                println(cursor.getString(0))
                println(cursor.getString(1))
                println(cursor.getString(2))
                println(cursor.getString(3))
                listNotes.add(note(cursor.getString(1), cursor.getString(2), cursor.getString(3)))
            } while (cursor.moveToPrevious())
            recycler_view.adapter = adapter
        }
    }

    private fun loadQueryOldest(title: String) {
        var databaseHelper = DatabaseHelper(this)
        val projections = arrayOf("ID", "Name", "Description", "Label")
        val selectionArgs = arrayOf(title)
        //sort by ID
        val cursor = databaseHelper.query(projections, "ID like ?", selectionArgs, "ID")
        listNotes.clear()
        //Descending
        if(cursor.moveToFirst()) {
            do {
                println(cursor.getString(0))
                println(cursor.getString(1))
                println(cursor.getString(2))
                println(cursor.getString(3))
                listNotes.add(note(cursor.getString(1), cursor.getString(2), cursor.getString(3)))
            } while (cursor.moveToNext())
            recycler_view.adapter = adapter
        }
    }

    private fun loadQueryCategory(title: String) {
        var databaseHelper = DatabaseHelper(this)
        val projections = arrayOf("ID", "Name", "Description", "Label")
        val selectionArgs = arrayOf(title)
        //sort by ID
        val cursor = databaseHelper.query(projections, "ID like ?", selectionArgs, "Label")
        listNotes.clear()
        //Descending
        if(cursor.moveToFirst()) {
            do {
                println(cursor.getString(0))
                println(cursor.getString(1))
                println(cursor.getString(2))
                println(cursor.getString(3))
                listNotes.add(note(cursor.getString(1), cursor.getString(2), cursor.getString(3)))
            } while (cursor.moveToNext())
            recycler_view.adapter = adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //gets called automatically when activity fires up
        var sharedPref = SharedPrefHandler(context)
        menuInflater.inflate(R.menu.main_menu, menu)

        if (menu != null && sharedPref.getAccess()) {
            menu.getItem(0).setIcon(R.drawable.pin_enable)
        }
        else if(menu != null && !sharedPref.getAccess()){
            menu.getItem(0).setIcon(R.drawable.pin_disable)
        }

        if(menu != null && sharedPref.getLayout() == "Linear"){
            menu.getItem(1).setTitle("Grid Layout")
        }
        else if(menu != null && sharedPref.getLayout() == "Grid"){
            menu.getItem(1).setTitle("Linear Layout")
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val sharedPref = SharedPrefHandler(context)
        when (item.itemId) {

            R.id.layout -> {
                println("layout item clicked")
                println(sharedPref.getLayout())
                if(sharedPref.getLayout() == "Linear"){
                    recycler_view.layoutManager = GridLayoutManager(this,2)
                    item.setTitle("Linear Layout")
                    sharedPref.setLayout("Grid")
                    println(".....")
                    println(sharedPref.getLayout())
                    defaultLayout = 1
                }
                else{
                    recycler_view.layoutManager = LinearLayoutManager(this)
                    item.setTitle("Grid Layout")
                    sharedPref.setLayout("Linear")
                    defaultLayout = 0
                }
            }
            R.id.pinSetting -> {

                if(sharedPref.getPin() != 0 && sharedPref.getPin() != -1){
                    //pin is there
                    var intent = Intent(this, PinReset::class.java)
                    println("Called PIN RESET - Negative btn")
                    intent.putExtra("reset","true")
                    startActivity(intent)
                    finish()
                }
                else{
                    var intent = Intent(this, Pin::class.java)
                    println("Called PIN SETUP - Positive btn")
                    startActivity(intent)
                    finish()
                }
            }
            R.id.pinOnOff -> {
                if(sharedPref.getPin() != 0 && sharedPref.getPin() != -1 && !sharedPref.getAccess()){
                    //pin is there
                    //Switch Icon here and show toast saying pin is turned on
                    sharedPref.setAccess(true)
                    item.setIcon(R.drawable.pin_enable)
                    Toast.makeText(this, "Pin Enabled", Toast.LENGTH_LONG).show()

                }
                else if(sharedPref.getPin() != 0 && sharedPref.getPin() != -1 && sharedPref.getAccess()){
                    sharedPref.setAccess(false)
                    item.setIcon(R.drawable.pin_disable)
                    Toast.makeText(this, "Pin Disabled", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this, "Setup a Pin from settings menu in top right icon", Toast.LENGTH_LONG).show()
                }
            }
            R.id.sort->{
                showSortDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSortDialog() {
        println("showSortDialog()")
        //list of sorting options
        val sortOptions = arrayOf("Newest", "Oldest", "Title(Ascending)", "Title(Descending)", "Category")
        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle("Sort by")
        mBuilder.setIcon(R.drawable.ic_sort)
        mBuilder.setSingleChoiceItems(sortOptions, -1){
                dialogInterface, i ->
            println("i =" +i)
            if (i == 0){
                //newest first
                Toast.makeText(this, "Newest", Toast.LENGTH_SHORT).show()
                val editor = mSharedPref!!.edit()
                editor.putString("Sort", "newest")
                editor.apply()
                loadQueryNewest("%")
            }
            if (i == 1){
                //oldest first
                Toast.makeText(this, "Oldest", Toast.LENGTH_SHORT).show()
                val editor = mSharedPref!!.edit()
                editor.putString("Sort", "oldest")
                editor.apply()
                loadQueryOldest("%")
            }
            if (i == 2){
                //title ascending
                Toast.makeText(this, "Title(Ascending)", Toast.LENGTH_SHORT).show()
                val editor = mSharedPref!!.edit()
                editor.putString("Sort", "ascending")
                editor.apply()
                loadQueryAscending("%")
            }
            if (i == 3){
                //title descending
                Toast.makeText(this, "Title(Descending)", Toast.LENGTH_SHORT).show()
                val editor = mSharedPref!!.edit()
                editor.putString("Sort", "descending")
                editor.apply()
                loadQueryDescending("%")
            }
            if (i == 4){
                //categories
                Toast.makeText(this, "Category", Toast.LENGTH_SHORT).show()
                val editor = mSharedPref!!.edit()
                editor.putString("Sort", "category")
                editor.apply()
                loadQueryDescending("%")
            }
            dialogInterface.dismiss()
        }

        val mDialog = mBuilder.create()
        mDialog.show()
    }

}
