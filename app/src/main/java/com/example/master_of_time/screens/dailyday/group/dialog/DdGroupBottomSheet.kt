package com.example.master_of_time.screens.dailyday.group.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.master_of_time.R
import com.example.master_of_time.database.AppDatabase
import com.example.master_of_time.database.table.DdGroup
import com.example.master_of_time.databinding.DdGroupBottomSheetBinding
import com.example.master_of_time.screens.dailyday.group.*
import com.example.master_of_time.screens.dailyday.group.viewmodel.DdGroupViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import timber.log.Timber

class DdGroupBottomSheet: BottomSheetDialogFragment(), View.OnClickListener,
    PickDdGroupAdapter.Listener {

    private lateinit var binding: DdGroupBottomSheetBinding
    private lateinit var viewModel: DdGroupViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DdGroupBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val dataSource = AppDatabase.getInstance(requireContext()).dailyDayDao()

        viewModel = ViewModelProvider(
            requireActivity(),
            DdGroupViewModel.Factory(dataSource)
        )[DdGroupViewModel::class.java]


        val adapter = PickDdGroupAdapter(viewLifecycleOwner, viewModel, this)
        lifecycle.coroutineScope.launch {
            viewModel.getAllDdGroup().collect() {
                adapter.submitList(it)
            }
        }

        binding.run{
            bindUI = this@DdGroupBottomSheet

            recyclerView.run{
                this.adapter = adapter
            }
        }

        retrieveParentData()
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.addGroup -> {
                findNavController().popBackStack()
                setFragmentResult("DdGroupBottomSheet", bundleOf(
                    "action_ddGroupBottomSheet_to_ddGroupEditDialogFragment" to true
                ))
            }
        }
    }

    private fun retrieveParentData() {
        val navigationArgs: DdGroupBottomSheetArgs by navArgs()
        viewModel.selectedGroupId = navigationArgs.groupId
    }


    override fun onDdGroupPicked(isPicked: Boolean, item: DdGroup) {
        if(isPicked) findNavController().previousBackStackEntry?.savedStateHandle?.set("groupId", item.id)
        else findNavController().previousBackStackEntry?.savedStateHandle?.set("groupId", -1L)
    }
}