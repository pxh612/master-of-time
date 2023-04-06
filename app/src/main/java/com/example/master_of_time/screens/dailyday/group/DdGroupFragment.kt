package com.example.master_of_time.screens.dailyday.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.master_of_time.R
import com.example.master_of_time.database.AppDatabase
import com.example.master_of_time.database.table.DdGroup
import com.example.master_of_time.databinding.DdGroupFragmentBinding
import com.example.master_of_time.screens.dailyday.group.adapter.DdGroupAdapter
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
            DdGroupViewModelFactory(dataSource)
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
            ui = this@DdGroupFragment
            bindVM = this@DdGroupFragment.viewModel

            groupRecyclerView.run{
                adapter = ddGroupAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.back -> findNavController().popBackStack()
            R.id.add -> navigateAddGroup()
        }
    }
    override fun onTitleClick(item: DdGroup) {
        Timber.i("item = $item")
        navigateEditGroup(item.id)
    }

    private fun navigateEditGroup(groupId: Int) {
        val action = DdGroupFragmentDirections.actionDdGroupFragmentToDdGroupEditDialogFragment(isAdd = false, groupId = groupId)
        requireView().findNavController().navigate(action)
    }
    private fun navigateAddGroup() {
        val action = DdGroupFragmentDirections.actionDdGroupFragmentToDdGroupEditDialogFragment(isAdd = true)
        requireView().findNavController().navigate(action)
    }


}
