package com.jeff_skillrill.caller

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController

import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jeff_skillrill.caller.Adapters.Contact
import com.jeff_skillrill.caller.OtherStuff.DBHelper
import com.jeff_skillrill.caller.databinding.FragmentViewBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ViewFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentViewBinding.inflate(inflater, container, false)
        val db = DBHelper(requireContext())
        val contact = arguments?.getSerializable("contact") as Contact

        binding.name.text = contact.name
        binding.phone.text = contact.phone

        binding.delete.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext()).setTitle("Dear User").setMessage("Do you want to delete contact?").setPositiveButton("Yes"
            ) { p0, p1 ->
                db.deleteContact(contact)
                findNavController().navigate(R.id.action_viewFragment_to_fragmentContacts)
            }
                .setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        Toast.makeText(requireContext(), "Cancel", Toast.LENGTH_SHORT).show()
                    }
                })
                .show()

        }

        binding.edit.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext()).setTitle("Dear User").setMessage("Do you want to edit contact?").setPositiveButton("Edit"
            ) { p0, p1 ->
                db.deleteContact(contact)
                findNavController().navigate(R.id.action_viewFragment_to_addContactFragment)
            }
                .setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        Toast.makeText(requireContext(), "Cancel", Toast.LENGTH_SHORT).show()
                    }
                })
                .show()
        }



        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}