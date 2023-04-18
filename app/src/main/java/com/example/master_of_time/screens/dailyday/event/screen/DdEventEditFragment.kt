package com.example.master_of_time.screens.dailyday.event.screen

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.master_of_time.*
import com.example.master_of_time.database.AppDatabase
import com.example.master_of_time.database.table.DdEvent
import com.example.master_of_time.databinding.DdEventEditFragmentBinding
import com.example.master_of_time.module.dailyday.DdEventCalculation
import com.example.master_of_time.screens.dailyday.event.DdEventEditViewModel
import com.example.master_of_time.screens.dailyday.event.DdEventEditViewModelFactory
import timber.log.Timber


class DdEventEditFragment : Fragment(), View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private val viewModel: DdEventEditViewModel by lazy {
        val dailyDayDao = AppDatabase.getInstance(requireContext()).dailyDayDao()
        ViewModelProvider(
            requireActivity(),
            DdEventEditViewModelFactory(dailyDayDao)
        )[DdEventEditViewModel::class.java]
    }

    private lateinit var binding: DdEventEditFragmentBinding

    /** Data */
    private lateinit var originalDdEvent: DdEvent

    var ddEvent: DdEvent = DdEvent()
        set(value){
            field = value
            bind()
        }

    val datePickerDialog: DatePickerDialog by lazy {
        DatePickerDialog(requireContext()).also {
            it.setOnDateSetListener(this)
        }
    }

    var isAdd: Boolean = false
    var isWaitingBottomSheet = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DdEventEditFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            bindUI = this@DdEventEditFragment

            toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

            initCalculationTypePicker()
        }

        retrieveParentData()
        retrieveChildData()
    }

    private fun initCalculationTypePicker() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.ddevent_calculation_picker,
            android.R.layout.simple_spinner_dropdown_item
        ).also{ mAdapter ->

            mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            binding.calculateTypePicker.run{
                adapter = mAdapter
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                        when(val dateCalculationType = parent.getItemAtPosition(position)){
                            is String -> {
                                val name: String = dateCalculationType
                                ddEvent = ddEvent.copy(calculationTypeId = DdEventCalculation.findIdByName(name))
                            }
                            else -> throw Exception("Spinner's item is not string type")
                        }
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) { }

                }
            }
        }
    }

    override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        ddEvent = ddEvent.copy(date = datePickerDialog.datePicker.toEpochTimeSeconds())
    }

    private fun bind(){
        binding.apply {
            invalidateAll()

            /** Date */
            date.text = ddEvent.date.toDateFormat().toEditable()
            ddEvent.date.toZonedDateTime().run{
                datePickerDialog.updateDate(year, monthValue - 1, dayOfMonth)
            }

            /** Group owner*/
            ddEvent.groupId.let{
                when {
                    (it == null)  -> {
                        ddGroupPicker.text = getString(R.string.ddGroupPicker_ddEventEditFragment)
                        ddGroupPicker.setTextColor(ContextCompat.getColor(requireContext(), R.color.light_gray))
                    }
                    else -> {
                        viewModel.getGroupName_byGroupId(it).observe(viewLifecycleOwner) { groupName ->
                                ddGroupPicker.text = groupName
                        }
                        ddGroupPicker.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    }
                }
            }

            /** Calculation type */
            calculateTypePicker.setSelection(ddEvent.calculationTypeId)

        }
    }



    override fun onClick(view: View) {
        when(view.id){
            R.id.submitButton -> {
                val title = binding.title.text.toString().trim()

                when{
                    (title.isEmpty()) -> notifyEmptyInput()
                    else -> {
                        ddEvent = ddEvent.copy(title = title)
                        when(isAdd) {
                            true -> viewModel.insertItem(ddEvent)
                            false -> viewModel.updateItem(ddEvent)
                        }
                        findNavController().popBackStack()
                    }
                }

            }
            R.id.date -> datePickerDialog.show()
            R.id.delete -> {
                viewModel.deleteItem(ddEvent)
                findNavController().popBackStack()
            }
            R.id.ddGroupPicker -> navigateGroupPicker()
        }
    }



    private fun retrieveParentData() {
        val navigationArgs: DdEventEditFragmentArgs by navArgs()
        isAdd = navigationArgs.isAdd
        when(isAdd){
            true -> {
                val groupId = navigationArgs.groupId

                if(groupId >= 0) ddEvent = ddEvent.copy(groupId = groupId)
                else ddEvent = DdEvent()

                binding.run{
                    delete.visibility = View.GONE
                    binding.toolbar.title = "Add event"
                }
            }
            false -> {
                val id = navigationArgs.eventId

                binding.toolbar.title = "Edit event"
                binding.delete.visibility = View.VISIBLE
                viewModel.getDdEvent(id).observe(this.viewLifecycleOwner) {

                    ddEvent = it
                    originalDdEvent = ddEvent
                    binding.run{
                        toolbar.subtitle = it.title
                        title.text = it.title.toEditable()
                    }
                }

            }
        }
        originalDdEvent = ddEvent
    }


    private fun retrieveChildData() {

        findNavController().currentBackStackEntry?.savedStateHandle?.run {
            getLiveData<Long>("groupId").observe(viewLifecycleOwner) { groupId ->
                if(groupId >= 0 && groupId != null) ddEvent = ddEvent.copy(groupId = groupId)
                else ddEvent = ddEvent.copy(groupId = null)
            }
        }

        setFragmentResultListener("DdGroupBottomSheet") { _, bundle ->
            val isNavigateAddDdGroup =
                bundle.getBoolean("action_ddGroupBottomSheet_to_ddGroupEditDialogFragment")

            if (isNavigateAddDdGroup) {
                isWaitingBottomSheet = true
                navigateAddGroup()
            }
        }

        setFragmentResultListener("DdGroupEditDialogFragment") { _, bundle ->
            val onDestroy = bundle.getBoolean("onDestroy")
            val newGroupId = bundle.getLong("newGroupId")

            if(newGroupId >= 0) ddEvent = ddEvent.copy(groupId = newGroupId)
            else if(onDestroy && isWaitingBottomSheet) navigateGroupPicker()
        }
    }

    private fun notifyEmptyInput() {
        val message = "Empty title!"
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }



    private fun navigateGroupPicker() {
        val mGroupId = ddEvent.groupId.let{
            when {
                (it == null || it < 0) -> -1
                else -> it
            }
        }
        val action = DdEventEditFragmentDirections.actionDdEventEditFragmentToDdGroupBottomSheet(groupId = mGroupId)
        /** Bug: navigation crash when make mutiple navigation call quickly
         * Fix: Solution https://stackoverflow.com/a/59700496/13170425
         * Fix2: https://dev.to/sh3lan93/android-navigation-component-issue-ik3
         */
        view?.post {
            findNavController().navigate(action)
        }
    }

    private fun navigateAddGroup() {
        val action = DdEventEditFragmentDirections.actionDdEventEditFragmentToDdGroupEditDialogFragment(isAdd = true)
        requireView().findNavController().navigate(action)
    }


}




/*    private fun showKeyboard() {

        *//* Worked: but only for toggling
        val view = requireActivity().currentFocus
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
        *//*

        *//* Worked: SHOW_FORCED & manually get view, but only when editText is focused
                (https://techenum.com/show-or-hide-soft-keyboard-in-android-application-and-more/)
        var view = requireActivity().currentFocus
        if (view == null) view = View(activity)
        val mgr: InputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mgr.showSoftInput(view, InputMethodManager.SHOW_FORCED)*//*

        *//* Worked: but not applicable to DdGroupEditDialogFragment, had to call through a button
        binding.title.requestFocus()
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.title, 0) *//*

    }*/



