package com.example.master_of_time.screens.dailyday.event.screen

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.master_of_time.*
import com.example.master_of_time.database.table.DdEvent
import com.example.master_of_time.database.AppDatabase
import com.example.master_of_time.databinding.DdEventEditFragmentBinding
import com.example.master_of_time.screens.dailyday.event.DdEventEditViewModel
import com.example.master_of_time.screens.dailyday.event.DdEventEditViewModelFactory
import timber.log.Timber


class DdEventEditFragment : Fragment(), View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private lateinit var viewModel: DdEventEditViewModel
    private lateinit var binding: DdEventEditFragmentBinding
    private val navigationArgs: DdEventEditFragmentArgs by navArgs()

    /** Data */
    lateinit var ddEvent: DdEvent
    lateinit var datePickerDialog: DatePickerDialog
    var isAdd: Boolean = false



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DdEventEditFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dailyDayDao = AppDatabase.getInstance(requireContext()).dailyDayDao()

        viewModel = ViewModelProvider(requireActivity(), DdEventEditViewModelFactory(dailyDayDao))[DdEventEditViewModel::class.java]


        /** init Views  */
        binding.run {
            submitButton.setOnClickListener(this@DdEventEditFragment)
            date.setOnClickListener(this@DdEventEditFragment)
            delete.setOnClickListener(this@DdEventEditFragment)
            ddGroupPicker.setOnClickListener(this@DdEventEditFragment)
        }

        /** init DatePickerDialog */
        datePickerDialog = DatePickerDialog(requireContext())
        datePickerDialog.setOnDateSetListener(this)

        retrieveNavigationArgs()
        observeNavigationArgs()
    }



    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        binding.date.text = view.toDateFormat().toEditable()
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.submitButton -> {
                when(fetchInput()) {
                    true ->{
                        when(isAdd) {
                            true -> viewModel.insertItem(ddEvent)
                            false -> viewModel.updateItem(ddEvent)
                        }
                        findNavController().popBackStack()
                    }
                    false -> notifyEmptyInput()
                }
            }
            R.id.date -> {
                datePickerDialog.show()
            }
            R.id.delete -> {
                viewModel.deleteItem(ddEvent)
                findNavController().popBackStack()
            }
            R.id.ddGroupPicker -> {
                showGroupPicker()
            }
        }
    }

    private fun retrieveNavigationArgs() {

        isAdd = navigationArgs.isAdd
        when(isAdd){
            true -> {
                ddEvent = DdEvent()
                bind(ddEvent, View.GONE)
            }
            false -> {
                val id = navigationArgs.eventId
                viewModel.getDdEvent(id).observe(this.viewLifecycleOwner) {
                    ddEvent = it
                    bind(it)
                }
            }
        }
    }

    private fun observeNavigationArgs() {
        findNavController().currentBackStackEntry?.savedStateHandle?.run {

            getLiveData<Int>("groupId").observe(viewLifecycleOwner) { groupId ->
                ddEvent.groupId = groupId
            }

            getLiveData<String>("groupName").observe(viewLifecycleOwner) { groupName ->
                binding.ddGroupPicker.text = groupName
            }
        }
    }



    private fun bind(ddEvent: DdEvent, deleteVisibility: Int = View.VISIBLE){
        binding.apply {
            title.text = ddEvent.title.toEditable()
            date.text = ddEvent.date.toDateFormat().toEditable()

            if(ddEvent.groupId != null) viewModel.getGroupName(ddEvent.groupId)?.observe(viewLifecycleOwner) {
                ddGroupPicker.text = it
            }
            else ddGroupPicker.text = getString(R.string.ddGroupPicker_ddEventEditFragment)


            delete.visibility = deleteVisibility
        }
    }



    private fun fetchInput(): Boolean {
        val title: String = binding.title.text.toString()
        val date:Long = datePickerDialog.datePicker.toEpochTimeSeconds()

        if(title.isEmpty()) return false
        else {
            ddEvent = ddEvent.copy(title = title, date = date)
            return true
        }
    }

    private fun showGroupPicker() {
        val action = DdEventEditFragmentDirections.actionDdEventEditFragmentToDdGroupBottomSheet()
        requireView().findNavController().navigate(action)
    }

    private fun notifyEmptyInput() {
        val message = "Empty title!"
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


}






