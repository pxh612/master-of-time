package com.example.master_of_time.screens.dailyday.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import com.example.master_of_time.database.AppDatabase
import com.example.master_of_time.databinding.DdGroupBottomSheetBinding
import com.example.master_of_time.databinding.DdGroupEditDialogFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import timber.log.Timber

class DdGroupBottomSheet(
    private val listener: DdGroupItemClickListener
): BottomSheetDialogFragment() {

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
        val ddGroupAdapter = DdGroupPickerAdapter(listener)
        lifecycle.coroutineScope.launch {
            viewModel.getAllDdGroup()!!.collect() {
                Timber.v("> collect FlowList for adapter: size = ${it.size}")
                ddGroupAdapter.submitList(it)
            }
        }

        binding.run{
            recyclerView.run{
                adapter = ddGroupAdapter
            }
        }

    }

}