package com.jeff_skillrill.caller.OtherStuff

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class Dialog : DialogFragment() {
    @SuppressLint("ResourceType")
    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Delete contact")
                .setMessage("Are you sure you want to remove this contact from your contacts?")
                .setCancelable(true)
                .setPositiveButton("Yes") { _, _ ->

                }
                .setNegativeButton(
                    "No"
                ) { _, _ ->

                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}