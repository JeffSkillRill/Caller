package com.jeff_skillrill.caller.Adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jeff_skillrill.caller.R

class ContactAdapter(var list:MutableList<Contact>, var contInterface: ContactInterface, var context: Context, var activity: Activity) : RecyclerView.Adapter<ContactAdapter.ContactHolder>() {

    var onItemClick : ((Contact) -> Unit)? = null
    fun FilteredList(list: MutableList<Contact>){
        this.list = list
        notifyDataSetChanged()
    }
    class ContactHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var name: TextView = itemView.findViewById(R.id.name)
        var phone: TextView = itemView.findViewById(R.id.phone)
        var contactlayout: ConstraintLayout = itemView.findViewById(R.id.cont_lay)
        var call: ImageView = itemView.findViewById(R.id.call)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        var holder = ContactHolder(LayoutInflater.from(parent.context).inflate(R.layout.contac_layout,parent,false))
        return holder
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        var item = list[position]
        holder.name.text = item.name
        holder.phone.text = item.phone
        holder.contactlayout.setOnClickListener {
            contInterface.onClick(item)
        }
        holder.call.setOnClickListener {
            openCall(item.phone)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface ContactInterface{
        fun onClick(contact: Contact){

        }
    }

    fun openCall(number: String) {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CALL_PHONE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity, arrayOf(android.Manifest.permission.CALL_PHONE),
                1
            )
        } else {
            if (number.isNotEmpty()) {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:$number")
                activity.startActivity(callIntent)
            }
        }
    }
}