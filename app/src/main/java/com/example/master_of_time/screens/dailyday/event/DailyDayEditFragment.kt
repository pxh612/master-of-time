package com.example.master_of_time.screens.dailyday.event

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.master_of_time.*
import com.example.master_of_time.database.dailyday.DdEvent
import com.example.master_of_time.database.AppDatabase
import com.example.master_of_time.database.dailyday.OfflineDdEventRepository
import com.example.master_of_time.databinding.FragmentDailyDayEditBinding
import timber.log.Timber


class DailyDayEditFragment : Fragment(), View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private lateinit var viewModel: DailyDayViewModel
    private lateinit var binding: FragmentDailyDayEditBinding
    private val navigationArgs: DailyDayEditFragmentArgs by navArgs()

    /** Data */
    lateinit var selectedDdEvent: DdEvent
    lateinit var fetchedDdEvent: DdEvent
    lateinit var datePickerDialog: DatePickerDialog
    var isAdd: Boolean = false



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDailyDayEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** init Repository */
        val dailyDayRepository = OfflineDdEventRepository(AppDatabase.getInstance(requireContext()).dailyDayDao())

        /** init ViewModel */
        viewModel = ViewModelProvider(requireActivity(), DailyDayViewModelFactory(dailyDayRepository))[DailyDayViewModel::class.java]

        /** init Views  */
        binding.run {
            submitButton.setOnClickListener(this@DailyDayEditFragment)
            date.setOnClickListener(this@DailyDayEditFragment)
            delete.setOnClickListener(this@DailyDayEditFragment)

//            groupSpinner.adapter = ?
            // SAMPLE
//            groupSpinner.adapter = ArrayAdapter.createFromResource(
//                this,
//
//            )
        }

        /** init DatePickerDialog */
        datePickerDialog = DatePickerDialog(requireContext())
        datePickerDialog.setOnDateSetListener(this)

        /** retrieve Navigation */
        isAdd = navigationArgs.isAdd
        if(isAdd) {
            selectedDdEvent = DdEvent(title = "")
            binding.run{
                date.text = datePickerDialog.datePicker.toDateFormat().toEditable()
                delete.visibility = View.GONE
            }

        }
        else {
            val id = navigationArgs.dailyDayId
            viewModel.retrieveDailyDay(id).observe(this.viewLifecycleOwner){
                selectedDdEvent = it
                bind(selectedDdEvent)
            }
        }
    }



    private fun bind(ddEvent: DdEvent){
        binding.apply {
            title.text = ddEvent.title.toEditable()
            date.text = ddEvent.date.toOffsetDateTime().toDateFormat().toEditable()
        }
    }


    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        binding.date.text = view.toDateFormat().toEditable()
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.submitButton -> {
                Timber.v("> reponsive")

                if(fetchInput()) {
                    if (isAdd) addItem()
                    else updateItem()
                    findNavController().popBackStack()

                } else {
                    notifyEmptyInput()
                }
            }
            R.id.date -> {
                datePickerDialog.show()
            }
            R.id.delete -> {
                deleteItem()
                findNavController().popBackStack()
            }
            R.id.groupSpinner -> {
                Timber.d("> spinner reponsive")
            }
        }
    }


    private fun fetchInput(): Boolean {
        val title: String = binding.title.text.toString()
        val date:Long = datePickerDialog.datePicker.toEpochTimeSeconds()

        if(title.isEmpty()) return false
        else {
            fetchedDdEvent = selectedDdEvent.copy(title = title, date = date)
            return true
        }
    }


    private fun notifyEmptyInput() {
        val message = "Empty title!"
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun addItem() {
        viewModel.insertDailyDay(fetchedDdEvent)
    }
    private fun updateItem() {
        viewModel.updateDailyDay(fetchedDdEvent)
    }
    private fun deleteItem() {
        viewModel.deleteDailyDay(selectedDdEvent)
    }

}




// ====================================== IGNORE

// https://www.techyourchance.com/you-dont-need-android-viewmodel/
// If you create your custom Views, you can achieve the same behavior by implementing
// onSaveInstanceState and onRestoreInstanceState methods.
// In fact, you’ll probably want to rely on this mechanism even if you do use ViewModel
// (e.g. it would be extremely dirty to store input fields state in ViewModel).
// So, no benefit for ViewModel in the context of UI state.