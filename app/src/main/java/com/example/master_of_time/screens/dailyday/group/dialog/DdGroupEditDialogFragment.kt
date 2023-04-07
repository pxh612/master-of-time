package com.example.master_of_time.screens.dailyday.group.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.master_of_time.R
import com.example.master_of_time.database.AppDatabase
import com.example.master_of_time.database.table.DdGroup
import com.example.master_of_time.databinding.DdGroupEditDialogFragmentBinding
import com.example.master_of_time.screens.dailyday.group.DdGroupViewModel
import com.example.master_of_time.toEditable

class DdGroupEditDialogFragment : DialogFragment(), View.OnClickListener {


    private lateinit var binding: DdGroupEditDialogFragmentBinding
    private lateinit var viewModel: DdGroupViewModel

    /** Data */
    lateinit var name: String
    var isAdd: Boolean = true
    var groupId: Int? = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DdGroupEditDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataSource = AppDatabase.getInstance(requireContext()).dailyDayDao()

        viewModel = ViewModelProvider(
            requireActivity(),
            DdGroupViewModel.Factory(dataSource)
        )[DdGroupViewModel::class.java]


        binding.run{
            bindUI = this@DdGroupEditDialogFragment
        }

        retrieveParentView()
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.cancel -> findNavController().popBackStack()
            R.id.submit -> {

                fetchInput()

                if(name.isEmpty()) findNavController()
                else{
                    when(isAdd){
                        true -> viewModel.insertGroup(name)
                        false -> groupId
                            ?.let { DdGroup(id = it, name = name) }
                            ?.let { viewModel.updateGroup(it) }
                    }
                    findNavController().popBackStack()
                }

            }
        }
    }

    private fun retrieveParentView() {

        val navigationArgs: DdGroupEditDialogFragmentArgs by navArgs()
        isAdd = navigationArgs.isAdd
        when(isAdd){
            false -> {
                groupId = navigationArgs.groupId

                viewModel.getDdGroupName(groupId!!).observe(viewLifecycleOwner) {
                    binding.name.text = it.toEditable()
                }
                binding.submit.text = "OK"
            }
            true -> {
                groupId = null
                binding.submit.text = "Add"
            }
        }
    }

    private fun fetchInput() {
        name = binding.name.text.toString()
    }

    private fun notifyOnDestroy(){
        setFragmentResult("DdGroupEditDialogFragment", bundleOf(
            "onDestroy" to true
        ))
    }

    override fun onDestroy() {
        super.onDestroy()
        notifyOnDestroy()
    }

}