package com.jeff_skillrill.caller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.jeff_skillrill.caller.Adapters.Contact
import com.jeff_skillrill.caller.Adapters.ContactAdapter
import com.jeff_skillrill.caller.OtherStuff.DBHelper
import com.jeff_skillrill.caller.databinding.FragmentContactsBinding
import java.util.*
import kotlin.collections.ArrayList


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class FragmentContacts : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    var contacts = mutableListOf<Contact>()
    lateinit var adapter: ContactAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentContactsBinding.inflate(inflater, container, false)
        val db = DBHelper(requireContext())

        contacts = db.getContacts()

        if (contacts.isEmpty()){
            binding.box.visibility = View.VISIBLE
        }
        else{
          adapter = ContactAdapter(contacts, object : ContactAdapter.ContactInterface{
                override fun onClick(contact: Contact) {
                    val bundle = bundleOf()
                    bundle.putSerializable("contact",contact)
                    findNavController().navigate(R.id.action_fragmentContacts_to_viewFragment,bundle)
                }

            })

            binding.contactRv.adapter = adapter
        }

        binding.add.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentContacts_to_addContactFragment)
        }

        binding.searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText, adapter)
                return true
            }

        })


        return binding.root
    }
    private fun filterList(query: String?, adapter: ContactAdapter){
        if(query != null){
            val filteredList = ArrayList<Contact>()
            for(i in contacts){
                if(i.name.toLowerCase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
            }
            if(filteredList.isEmpty()){

            }else{
                adapter.FilteredList(filteredList)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentContacts().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}