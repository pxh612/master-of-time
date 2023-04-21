package com.example.master_of_time.screens.dailyday.history

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.master_of_time.*
import com.example.master_of_time.database.AppDatabase
import com.example.master_of_time.database.table.DdEvent
import com.example.master_of_time.database.table.DdEventHistory
import com.example.master_of_time.databinding.DdEventHistoryEditFragmentBinding
import com.example.master_of_time.screens.dailyday.event.DdEventViewModel
import timber.log.Timber

class DdEventHistoryEditFragment: Fragment(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {

    companion object{
        val ADD_INTENT = 0
        val EDIT_INTENT = 1
    }


    private lateinit var binding: DdEventHistoryEditFragmentBinding
    private val viewModel: DdEventHistoryEditViewModel by lazy {
        val dailyDayDao = AppDatabase.getInstance(requireContext()).dailyDayDao()
        ViewModelProvider(
            requireActivity(),
            DdEventHistoryEditViewModel.Factory(dailyDayDao)
        )[DdEventHistoryEditViewModel::class.java]
    }

    var intent: Int = -1
    private var eventHistoryId = -1L

    private var ddEventHistory: DdEventHistory = DdEventHistory()
        set(value){
            field = value

            /** Toolbar */
            binding.toolbar.subtitle = when(intent){
                ADD_INTENT -> "New note"
                EDIT_INTENT -> "Note of " + value.date.toDateFormat()
                else -> ""
            }


            /** Date */
            binding.dateEdit.text = value.date.toDateFormat().toEditable()
            value.date.toZonedDateTime().run{
                datePickerDialog.updateDate(year, monthValue - 1, dayOfMonth)
            }

            /** Text */
            binding.titleEditText.text = value.title.toEditable()
            binding.descriptionEditText.text = value.description.toEditable()
        }

    private var ddEvent: DdEvent? = null
        set(value){
            field = value!!

            /** Data */
            ddEventHistory = ddEventHistory.copy(date = value.date)

            /** View */
            binding.toolbar.title = value.title
        }

    private var eventId = -1L
        set(value){
            field = value


        }

    private val datePickerDialog: DatePickerDialog by lazy {
        DatePickerDialog(requireContext()).also {
            it.setOnDateSetListener(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dd_event_history_edit_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.run{
            bindUI = this@DdEventHistoryEditFragment

            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
        retrieveParentData()
    }

    private fun retrieveParentData() {
        val navigationArgs: DdEventHistoryEditFragmentArgs by navArgs()
        intent = navigationArgs.fragmentIntent
        eventHistoryId = navigationArgs.eventHistoryId
        eventId = navigationArgs.eventId


        when(intent){
            ADD_INTENT -> {
                ddEventHistory = ddEventHistory.copy(eventId = eventId)
                viewModel.getDdEvent(eventId).observe(viewLifecycleOwner){
                    ddEvent = it
                }
            }
            EDIT_INTENT -> {
                viewModel.getDdEventHistory(eventHistoryId).observe(viewLifecycleOwner){ observedDdEventHistory ->
                    ddEventHistory = observedDdEventHistory
                }
                viewModel.getDdEvent(eventId).observe(viewLifecycleOwner){
                    ddEvent = it
                }
            }
        }
    }

    override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        ddEventHistory = ddEventHistory.copy(date = datePickerDialog.datePicker.toEpochTimeSeconds())
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.submitButton -> {
                val title: String = binding.titleEditText.text.toString().trim()
                val description: String = binding.descriptionEditText.text.toString()
                Timber.d("title = $title & description = $description" )
                when{
                    (title.isBlank() && description.isBlank()) -> {
                         val message = "Empty content!"
                         Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        ddEventHistory = ddEventHistory.copy(title = title, description = description)

                        when(intent){
                            ADD_INTENT -> viewModel.insertEventHistory(ddEventHistory)
                            EDIT_INTENT -> viewModel.updateEventHistory(ddEventHistory)
                        }


                        findNavController().popBackStack()
                    }
                }
            }
            R.id.dateEdit -> {
                datePickerDialog.show()
            }
            R.id.deleteButton -> {
                viewModel.deleteDdEventHistory(ddEventHistory)
                findNavController().popBackStack()
            }
        }
    }
}