package com.jeff_skillrill.caller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeff_skillrill.caller.database.Contact
import com.jeff_skillrill.caller.adapter.ContactAdapter
import com.jeff_skillrill.caller.database.AppDatabase
import com.jeff_skillrill.caller.databinding.FragmentContactsBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ContactsFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    val appDatabase: AppDatabase by lazy {
        AppDatabase.getInstanse(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentContactsBinding.inflate(inflater, container, false)
        val contacts = appDatabase.getContactDao().getAllUsers()

        if (contacts.isNotEmpty()) {
            binding.nullImg.visibility = View.INVISIBLE
            binding.recyclerView.visibility = View.VISIBLE
            val adapter = ContactAdapter(contacts, object : ContactAdapter.myContact {
                override fun onClick(contact: Contact) {
                    val bundle = Bundle()
                    bundle.putInt("id", contact.id)
                    findNavController().navigate(
                        R.id.action_contactsFragment_to_contact_InfoFragment,
                        bundle
                    )
                }
            }, requireContext(), requireActivity())
            val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = manager
        }


        binding.addContact.setOnClickListener {
            findNavController().navigate(R.id.action_contactsFragment_to_addContactFragment)

        }
        binding.toolbar.menu

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun majorityElement(nums: IntArray): Int {
        var res = 0
        var ok = nums.size / 2
        var count = 0
        nums.sort()
        for (i in 0..nums.size-2) {
            if (nums[i] == nums[i + 1])
                count++
            else if (count > ok) {
                res = nums[i]
                count = 0
            }
        }
        return res
    }
}