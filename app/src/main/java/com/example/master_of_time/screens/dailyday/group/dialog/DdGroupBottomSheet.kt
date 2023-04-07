package com.example.master_of_time.screens.dailyday.group.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.master_of_time.R
import com.example.master_of_time.database.AppDatabase
import com.example.master_of_time.database.table.DdGroup
import com.example.master_of_time.databinding.DdGroupBottomSheetBinding
import com.example.master_of_time.screens.dailyday.group.*
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
            DdGroupViewModelFactory(dataSource)
        )[DdGroupViewModel::class.java]


        val adapter = PickDdGroupAdapter(this)
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
            addGroup.setOnClickListener(this@DdGroupBottomSheet)
        }
    }
    override fun onClick(view: View) {
        when(view.id) {
            R.id.addGroup -> {
                Timber.d("click: addGroup")

                findNavController().popBackStack()
                setFragmentResult("DdGroupBottomSheet", bundleOf(
                    "action_ddGroupBottomSheet_to_ddGroupEditDialogFragment" to true
                ))
           }
        }
    }

    override fun onItemClick(item: DdGroup) {
        Timber.i("> picked $item")
        findNavController().previousBackStackEntry?.savedStateHandle?.set("groupId", item.id)
        findNavController().previousBackStackEntry?.savedStateHandle?.set("groupName", item.name)
    }

}