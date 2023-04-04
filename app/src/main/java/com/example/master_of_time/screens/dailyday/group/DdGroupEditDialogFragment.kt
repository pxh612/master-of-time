package com.example.master_of_time.screens.dailyday.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.master_of_time.R
import com.example.master_of_time.database.AppDatabase
import com.example.master_of_time.databinding.DdGroupEditDialogFragmentBinding

class DdGroupEditDialogFragment : DialogFragment(), View.OnClickListener {

    private lateinit var binding: DdGroupEditDialogFragmentBinding
    private lateinit var viewModel: DdGroupViewModel

    /** Data */
    lateinit var name: String



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DdGroupEditDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val dataSource = AppDatabase.getInstance(requireContext()).ddGroupDao()


        viewModel = ViewModelProvider(
            requireActivity(),
            DdGroupViewModelFactory(dataSource)
        )[DdGroupViewModel::class.java]


        binding.run{
            cancel.setOnClickListener(this@DdGroupEditDialogFragment)
            submit.setOnClickListener(this@DdGroupEditDialogFragment)

        }

    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.cancel -> findNavController().popBackStack()
            R.id.submit -> {

                fetchInput()
                if(name.isEmpty()) findNavController()
                else{
                    viewModel.insertGroup(name)
                    findNavController().popBackStack()
                }

            }
        }

    }

    private fun fetchInput() {
        name = binding.name.text.toString()
    }

}