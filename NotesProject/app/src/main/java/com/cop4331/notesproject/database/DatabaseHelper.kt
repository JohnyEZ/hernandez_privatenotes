package com.cop4331.notesproject.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder

class DatabaseHelper(context: Context):
        SQLiteOpenHelper(context, dbName, null, 1) {


    val colID = "ID"
    val colName = "Name"
    val colDesc = "Description"
    val colTag = "Label"
    //val colDate = "Date"
    val dbVersion = 1
    val sqlCreateTable = "CREATE TABLE IF NOT EXISTS $dbTable ($colID INTEGER PRIMARY KEY," +
            "$colName TEXT, $colDesc TEXT, $colTag TEXT);"

    val context = context
    var sqlDB:SQLiteDatabase?=null

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(sqlCreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $dbName")
        onCreate(db)
    }

    fun insert(name: String, desc: String, label: String):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_1, name)
        contentValues.put(COL_2, desc)
        contentValues.put(COL_3, label)
        //contentValues.put(COL_3, date)

        return db.insert(dbTable, null, contentValues)
    }

    fun getNotes(): Cursor {
        val db = this.writableDatabase
        val res = db.rawQuery("SELECT * FROM $dbTable", null)
        return res
    }

    fun query(projection:Array<String>, selection:String, selectionArgs:Array<String>, sorOrder:String):Cursor {
        val qb = SQLiteQueryBuilder()
        qb.tables = dbTable
        return qb.query(this.writableDatabase, projection, selection, selectionArgs, null, null, sorOrder)
    }

    fun delete(name: String, desc: String, label: String){
        val db = this.writableDatabase
        val res = db.rawQuery("SELECT $COL_0 FROM $dbTable WHERE $COL_1 = '$name'", null)
        var id = 0
        while(res.moveToNext()){
//            Toast.makeText(context, "Found a row ${res.getString(0)}", Toast.LENGTH_LONG).show()
            id = res.getString(0).toInt()
        }
        db.delete(dbTable, "ID = $id", null)
        //return db.delete(dbTable, "ID = ?", arrayOf(id))
    }

    fun getID(name: String, desc: String, label: String): String{
        val db = this.writableDatabase
        val res = db.rawQuery("SELECT $COL_0 FROM $dbTable WHERE $COL_1 = '$name' AND $COL_2 = '$desc' AND $COL_3 = '$label'", null)
        var id = ""
        while(res.moveToNext()){
            id = res.getString(0)
        }
        return id
    }

    fun update(id: String, name: String, desc: String, label: String){
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_0, id)
        contentValues.put(COL_1, name)
        contentValues.put(COL_2, desc)
        contentValues.put(COL_3, label)
        //contentValues.put(COL_3, date)

        db.update(dbTable,contentValues, "ID = ?", arrayOf(id))

    }


    companion object {
        val dbName = "Notes"
        val dbTable = "NotesTable"
        val COL_0 = "ID"
        val COL_1 = "Name"
        val COL_2 = "Description"
        val COL_3 = "Label"
        //val COL_3 = "Date"
    }

}