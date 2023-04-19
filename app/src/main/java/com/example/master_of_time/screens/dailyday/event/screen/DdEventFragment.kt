package com.example.master_of_time.screens.dailyday.event.screen

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.master_of_time.R
import com.example.master_of_time.database.table.DdEvent
import com.example.master_of_time.database.AppDatabase
import com.example.master_of_time.databinding.DdEventFragmentBinding
import com.example.master_of_time.module.MyAnimator
import com.example.master_of_time.module.dailyday.DdEventListSorter
import com.example.master_of_time.screens.dailyday.event.DdEventLayoutWrapper
import com.example.master_of_time.screens.dailyday.event.DdEventViewModel
import com.example.master_of_time.screens.dailyday.event.adapter.DdEventRecyclerViewAdapter
import com.example.master_of_time.screens.dailyday.event.adapter.DdEventRecyclerViewGridLayoutAdapter
import com.example.master_of_time.screens.dailyday.group.DisplayEventsDdGroupAdapter
import kotlinx.coroutines.launch
import timber.log.Timber

class DdEventFragment : Fragment(), View.OnClickListener, DisplayEventsDdGroupAdapter.Listener,
    PopupMenu.OnMenuItemClickListener, DdEventRecyclerViewAdapter.Listener {

    /** Architecture */
    private lateinit var binding : DdEventFragmentBinding
    private val viewModel: DdEventViewModel by lazy {
        val dailyDayDao = AppDatabase.getInstance(requireContext()).dailyDayDao()
        ViewModelProvider(
            requireActivity(),
            DdEventViewModel.Factory(dailyDayDao)
        )[DdEventViewModel::class.java]
    }

    /** View Classes */
    private val displayEventsDdGroupAdapter by lazy {  DisplayEventsDdGroupAdapter(this, viewModel) }
    var ddEventRecyclerViewAdapter = DdEventRecyclerViewAdapter(emptyList(), this)

    /** Custom Module */
    private val ddEventLayoutWrapper: DdEventLayoutWrapper by lazy { DdEventLayoutWrapper(requireContext(), this) }
    private val ddEventListSorter: DdEventListSorter by lazy {
        val sharedPreferences = requireActivity().getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val ddEventListSortMethod = sharedPreferences
            .getInt("ddEventListSortMethod", DdEventListSorter.DEFAULT_SORT_METHOD)

        DdEventListSorter().apply {
            sortMethod = ddEventListSortMethod
        }
    }
    private val addAnimator: MyAnimator
        get() = viewModel.addMyAnimator

    /** Variables */
    private var ddEventList: List<DdEvent> = emptyList()
        set(value){
            field = value
            displayEvents()
        }

    private var selectedGroupId
        get() = viewModel.selectedGroupId
        set(value) { viewModel.selectedGroupId = value }

    private val mLayoutManager
        get() = ddEventLayoutWrapper.layoutManager



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dd_event_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFetchForGroupAdapter()
        onGroupDisplayClick(groupId = selectedGroupId)

        binding.run {
            this.bindUI = this@DdEventFragment


            toolbar.setNavigationOnClickListener {
                Timber.d("reponsive click")
                displayDrawerLayout()
            }

            /*groupsNavigationView.setNavigationItemSelectedListener {
                binding.groupsDrawerLayout.openDrawer(GravityCompat.START)
                return@setNavigationItemSelectedListener true
            }*/

            groupRecyclerView.run{
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = displayEventsDdGroupAdapter
            }
            eventRecyclerView.run{
                layoutManager = mLayoutManager
                adapter = ddEventRecyclerViewAdapter
            }
        }

        initAnimationForAddButton()
    }

    private fun displayDrawerLayout() {
        Timber.d("Display drawer layout")
        binding.groupsDrawerLayout.openDrawer(GravityCompat.START)
    }

    private fun initFetchForGroupAdapter() {
        lifecycle.coroutineScope.launch {
            viewModel.getAllDdGroup().collect() {
                displayEventsDdGroupAdapter.submitList(it)
            }
        }
    }

    override fun onGroupDisplayClick(groupId: Long?) {
        selectedGroupId = when{
            (groupId == null || groupId < 0) -> -1L
            else -> groupId
        }

        lifecycle.coroutineScope.launch {
            when (selectedGroupId) {
                -1L -> viewModel.getAllDdEvent().collect() {
                    ddEventList = it
                }
                else -> viewModel.getDdEventListByGroupId(selectedGroupId).collect() {
                    ddEventList = it
                }
            }
        }
    }

    private fun initAnimationForAddButton() {
        addAnimator.view = binding.add

        val onScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val offset = recyclerView.computeVerticalScrollOffset()
                Timber.v("newState = $newState & vertical Offset = $offset")
                when{
                    (newState == 0 ) -> addAnimator.show()
                    (newState >= 1 && offset != 0) -> addAnimator.hide()
                }
            }
        }
        binding.eventRecyclerView.addOnScrollListener(onScrollListener)
    }

    private fun displayEvents(){
        /** Restore ScrollPosition using saveInstanceState */
        val layoutManagerState = mLayoutManager.onSaveInstanceState()
        ddEventLayoutWrapper.setList(ddEventListSorter.sort(ddEventList))
        mLayoutManager.onRestoreInstanceState(layoutManagerState)



        when(ddEventLayoutWrapper.mLayoutState) {
            DdEventLayoutWrapper.LAYOUT_LINEAR -> {
                binding.eventRecyclerView.adapter  = ddEventLayoutWrapper.adapterLinear
                binding.layoutButton.setImageResource(R.drawable.three_column)
            }
            DdEventLayoutWrapper.LAYOUT_GRID -> {
                binding.eventRecyclerView.adapter  = ddEventLayoutWrapper.adapterGrid
                binding.layoutButton.setImageResource(R.drawable.grid_layout)

            }
            else -> throw Exception("Illegal argument for layout")
        }

        binding.eventRecyclerView.run{
            layoutManager = mLayoutManager
        }

        when(ddEventList.size){
            0 -> binding.noEventLayout.visibility = View.VISIBLE
            else -> binding.noEventLayout.visibility = View.GONE
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.add -> navigateToAddDdEvent(selectedGroupId)
            R.id.sortOption -> {
                displaySortOption(v)
            }
            R.id.layoutButton -> {
                ddEventLayoutWrapper.circleLayout()
                displayEvents()
            }
            R.id.group -> navigateToGroupList()
            R.id.noEventLayout -> navigateToAddDdEvent(selectedGroupId)
            R.id.menuImageView -> {
//                displayDrawerLayout()
                displayMenuOptions(v)
            }
        }
    }
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when(item!!.itemId){
            R.id.groupSettingOption -> {
                navigateToGroupList()
                true
            }
            else -> false
        }
    }

    override fun onDdEventItemClicked(ddEvent: DdEvent) {
        navigateToEditScreen(ddEvent.id)
    }

    private fun displayMenuOptions(v: View) {
        val popup = PopupMenu(requireContext(), v)
        popup.setOnMenuItemClickListener(this@DdEventFragment)
        popup.menuInflater.inflate(R.menu.dd_event_fragment_actionbar_menu, popup.menu)
        popup.show()
    }

    private fun displaySortOption(v: View) {

        val popup = PopupMenu(requireContext(), v).apply {
            setOnMenuItemClickListener {

                ddEventListSorter.sortMethod = when (it.itemId) {
                    R.id.sortOptionAlphabet -> DdEventListSorter.SORT_BY_ALPHABET
                    R.id.sortOptionDateCreated -> DdEventListSorter.SORT_BY_DATE_CREATED
                    R.id.sortOptionTargetDate -> DdEventListSorter.SORT_BY_TARGET_DATE
                    R.id.sortOptionNearestEvent -> DdEventListSorter.SORT_BY_NEAREST_EVENT
                    else -> return@setOnMenuItemClickListener false
                }

                /** write data to SharePreferences */
                val sharedPreferences = requireActivity().getSharedPreferences("UserPreferences", MODE_PRIVATE)
                sharedPreferences.edit().putInt("ddEventListSortMethod", ddEventListSorter.sortMethod).apply()

                /** Update screen */
                displayEvents()
                it.isChecked = true

                /** Popup stay open */
                it.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW)
                it.actionView = View(context)
                it.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{
                    override fun onMenuItemActionExpand(p0: MenuItem): Boolean { return false }
                    override fun onMenuItemActionCollapse(p0: MenuItem): Boolean { return false }
                })

                false
            }
        }
        val sortOptionItemId = findIdBySortMethod(ddEventListSorter.sortMethod)

        popup.menuInflater.inflate(R.menu.dd_event_fragment_sort_option, popup.menu)
        popup.menu.findItem(sortOptionItemId)?.isChecked = true
        popup.show()
    }
    private fun findIdBySortMethod(sortMethod: Int): Int{
        return when(sortMethod){
            DdEventListSorter.SORT_BY_TARGET_DATE -> R.id.sortOptionTargetDate
            DdEventListSorter.SORT_BY_ALPHABET -> R.id.sortOptionAlphabet
            DdEventListSorter.SORT_BY_DATE_CREATED -> R.id.sortOptionDateCreated
            DdEventListSorter.SORT_BY_NEAREST_EVENT -> R.id.sortOptionNearestEvent
            else -> throw IllegalStateException()
        }
    }



    private fun navigateToGroupList() {
        val action = DdEventFragmentDirections.actionDdEventFragmentToDdGroupFragment()
        requireView().findNavController().navigate(action)
    }

    private fun navigateToAddDdEvent(groupId: Long = -1) {
        val action = DdEventFragmentDirections.actionDdEventFragmentToDdEventEditFragment(isAdd = true, groupId = groupId)
        requireView().findNavController().navigate(action)
    }

    private fun navigateToEditScreen(eventId: Long) {
        val action = DdEventFragmentDirections.actionDdEventFragmentToDdEventEditFragment(isAdd = false, eventId = eventId)
        requireView().findNavController().navigate(action)
    }


}