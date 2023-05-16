package com.jeff_skillrill.caller.database

import androidx.room.*
import com.jeff_skillrill.caller.database.Contact

@Dao
interface ContactDao {
    @Query("select * from contacts")
    fun getAllUsers(): MutableList<Contact>

    @Insert
    fun addContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

    @Update
    fun updateContact(contact: Contact)
}