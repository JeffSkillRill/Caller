package com.jeff_skillrill.caller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jeff_skillrill.caller.database.Contact
import com.jeff_skillrill.caller.database.AppDatabase
import com.jeff_skillrill.caller.databinding.FragmentAddContactBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddContactFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddContactFragment : Fragment() {
    // TODO: Rename and change types of parameters
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
    ): View? {
        val binding = FragmentAddContactBinding.inflate(inflater, container, false)

        binding.done.setOnClickListener {
            if (binding.name.text!!.isNotEmpty() && binding.surname.text!!.isNotEmpty() && binding.phoneNumber.text!!.isNotEmpty()) {
                findNavController().navigate(R.id.action_addContactFragment_to_contactsFragment)
                appDatabase.getContactDao().addContact(
                    Contact(
                        name = binding.name.text!!.trim()
                            .toString() + " ${binding.surname.text!!.trim()}",
                        phone_number = binding.phoneNumber.text.toString()
                    )
                )
            }
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddContactFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddContactFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}