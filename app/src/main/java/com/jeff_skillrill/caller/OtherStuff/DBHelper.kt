package com.jeff_skillrill.caller.OtherStuff

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.jeff_skillrill.caller.Adapters.Contact

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
    companion object{
        const val DB_NAME = "contact"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        var query = "create table 'contact'('id' integer NOT NULL PRIMARY KEY AUTOINCREMENT, 'name' TEXT NOT NULL, 'phone_number' INTEGER NOT NULL)"
        p0?.execSQL(query)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun addContact(contact: Contact){
        val writabledb = this.writableDatabase
        var cv = ContentValues()
        cv.put("name", contact.name)
        cv.put("phone_number", contact.phone)

        writabledb.insert("contact",null,cv)

    }

    fun deleteContact(contact: Contact){
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("id",contact.id)

        db.delete("contact","id="+contact.id,null)
    }

    @SuppressLint("Range")
    fun getContacts():MutableList<Contact>{
        var readable = this.readableDatabase
        var list = mutableListOf<Contact>()
        var query = "SELECT * FROM contact"
        var cursor : Cursor? = null
        try{
            cursor = readable.rawQuery(query,null)
        }catch (e: SQLiteException) {
            readable.execSQL(query)
            return list
        }
        var contactId: Int
        var contactName: String
        var contactNumber: String
        if (cursor.moveToFirst()){
            do {
                contactId = cursor.getInt(cursor.getColumnIndex("id"))
                contactName = cursor.getString(cursor.getColumnIndex("name"))
                contactNumber = cursor.getString(cursor.getColumnIndex("phone_number"))
                val emp= Contact(id = contactId, name = contactName, phone = contactNumber)
                list.add(emp)
            }while (cursor.moveToNext())
        }
        return list
    }

}