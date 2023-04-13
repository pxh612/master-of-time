package com.example.master_of_time.screens.dailyday.group.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.master_of_time.R
import com.example.master_of_time.database.AppDatabase
import com.example.master_of_time.database.table.DdGroup
import com.example.master_of_time.databinding.DdGroupFragmentBinding
import com.example.master_of_time.screens.dailyday.group.DdGroupTouchHelperCallback
import com.example.master_of_time.screens.dailyday.group.viewmodel.DdGroupViewModel
import com.example.master_of_time.screens.dailyday.group.adapter.DdGroupAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber


class DdGroupFragment : Fragment(), View.OnClickListener, DdGroupAdapter.Listener {

    private lateinit var binding: DdGroupFragmentBinding
    private lateinit var viewModel: DdGroupViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.dd_group_fragment, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.v("> doing stuff with fragment")

        val dataSource = AppDatabase.getInstance(requireContext()).dailyDayDao()
        viewModel = ViewModelProvider(
            requireActivity(),
            DdGroupViewModel.Factory(dataSource)
        )[DdGroupViewModel::class.java]



        val ddGroupAdapter = DdGroupAdapter(viewModel,this)
        lifecycle.coroutineScope.launch {
            viewModel.getAllDdGroup().collect {
                Timber.v("> collect FlowList for adapter: size = ${it.size}")
                ddGroupAdapter.submitList(it)
            }
        }

        val callback = DdGroupTouchHelperCallback(ddGroupAdapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.groupRecyclerView)


        binding.run {
            bindUI = this@DdGroupFragment
            bindVM = viewModel

            toolbar.run{
                setNavigationOnClickListener { findNavController().popBackStack() }
            }

            groupRecyclerView.run{
                adapter = ddGroupAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
        retrieveDatabase()
    }

    private fun retrieveDatabase() {
        viewModel.getDdEventTotalCount().observe(viewLifecycleOwner) { totalCount ->
            binding.countTotal.text = totalCount.toString()
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.add -> navigateAddGroup()
        }
    }
    override fun onTitleClick(item: DdGroup) {
        Timber.i("item = $item")
        navigateEditGroup(item.id)
    }

    override fun onDelete(item: DdGroup) {
        viewModel.getDdEventCount_byGroupId(item.id).observe(viewLifecycleOwner){ groupCount ->
            when (groupCount) {
                0 -> deleteGroup(item)
                else -> {
                    navigateDeleteConfirmation(item, groupCount)
                    setFragmentResultListener("DdGroupDeleteConfirmation") { _, bundle ->
                        val isConfirmed = bundle.getBoolean("isConfirmed")
                        if(isConfirmed) {
                            deleteGroup(item)
                            viewModel.selectedGroupId = -1L
                        }
                    }
                }
            }
        }
    }

    private fun deleteGroup(item: DdGroup) {
        lifecycle.coroutineScope.launch {
            viewModel.getDdEventListByGroupId(item.id).collect(){
                for(ddEvent in it){
                    Timber.d("delete group ${item.name}: $ddEvent")
                    viewModel.updateDdEvent(ddEvent.copy(groupId = null))
                }
            }
        }
        viewModel.getDdEventCount_byGroupId(item.id).observe(viewLifecycleOwner) { groupCount ->
            if (groupCount == 0) viewModel.deleteGroup(item)
        }
    }

    private fun navigateEditGroup(groupId: Long) {
        val action = DdGroupFragmentDirections.actionDdGroupFragmentToDdGroupEditDialogFragment(isAdd = false, groupId = groupId)
        requireView().findNavController().navigate(action)
    }
    private fun navigateAddGroup() {
        val action = DdGroupFragmentDirections.actionDdGroupFragmentToDdGroupEditDialogFragment(isAdd = true)
        requireView().findNavController().navigate(action)
    }

    private fun navigateDeleteConfirmation(item: DdGroup, groupCount: Int) {
        val action = DdGroupFragmentDirections.actionDdGroupFragmentToDdGroupDeleteConfirmation(groupCount = groupCount, groupTitle = item.name)
        requireView().findNavController().navigate(action)
    }

}
