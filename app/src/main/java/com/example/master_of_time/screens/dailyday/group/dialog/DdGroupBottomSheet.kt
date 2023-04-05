package com.example.master_of_time.screens.dailyday.group.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.example.master_of_time.database.AppDatabase
import com.example.master_of_time.database.ddgroup.DdGroup
import com.example.master_of_time.databinding.DdGroupBottomSheetBinding
import com.example.master_of_time.screens.dailyday.group.PickerDdGroupAdapter
import com.example.master_of_time.screens.dailyday.group.DdGroupPickerListener
import com.example.master_of_time.screens.dailyday.group.DdGroupViewModel
import com.example.master_of_time.screens.dailyday.group.DdGroupViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import timber.log.Timber

class DdGroupBottomSheet: BottomSheetDialogFragment(), DdGroupPickerListener {

    private lateinit var binding: DdGroupBottomSheetBinding
    private lateinit var viewModel: DdGroupViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DdGroupBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val dataSource = AppDatabase.getInstance(requireContext()).ddGroupDao()

        viewModel = ViewModelProvider(
            requireActivity(),
            DdGroupViewModelFactory(dataSource)
        )[DdGroupViewModel::class.java]

        /** init Adapter for RecyclerView*/
        val adapter = PickerDdGroupAdapter(this)
        lifecycle.coroutineScope.launch {
            viewModel.getAllDdGroup().collect() {
                Timber.d("> collect FlowList for adapter: size = ${it.size}")
                adapter.submitList(it)
            }
        }

        binding.run{
            recyclerView.run{
                this.adapter = adapter
            }
        }

    }

    override fun onItemClick(item: DdGroup) {
        Timber.i("> picked $item")
        findNavController().previousBackStackEntry?.savedStateHandle?.set("groupId", item.id)
        findNavController().previousBackStackEntry?.savedStateHandle?.set("groupName", item.name)
    }

}