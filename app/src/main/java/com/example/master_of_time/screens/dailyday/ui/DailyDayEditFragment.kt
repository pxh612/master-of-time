package com.example.master_of_time.screens.dailyday.ui

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
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
import com.example.master_of_time.database.dailyday.DailyDay
import com.example.master_of_time.database.dailyday.DailyDayDatabase
import com.example.master_of_time.database.dailyday.OfflineDailyDayRepository
import com.example.master_of_time.databinding.FragmentDailyDayEditBinding
import com.example.master_of_time.screens.dailyday.DailyDayViewModel
import com.example.master_of_time.screens.dailyday.DailyDayViewModelFactory
import timber.log.Timber
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.*


class DailyDayEditFragment : Fragment(), View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private lateinit var viewModel: DailyDayViewModel
    private lateinit var binding: FragmentDailyDayEditBinding
    private val navigationArgs: DailyDayEditFragmentArgs by navArgs()

    /** Data */
    lateinit var selectedDailyDay: DailyDay
    lateinit var fetchedDailyDay: DailyDay
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
        val dailyDayRepository = OfflineDailyDayRepository(DailyDayDatabase.getInstance(requireContext()).dailyDayDao())

        /** init ViewModel */
        viewModel = ViewModelProvider(requireActivity(), DailyDayViewModelFactory(dailyDayRepository))[DailyDayViewModel::class.java]

        /** init Views & Buttons */
        binding.apply {
            submitButton.setOnClickListener(this@DailyDayEditFragment)
            date.setOnClickListener(this@DailyDayEditFragment)
            delete.setOnClickListener(this@DailyDayEditFragment)
        }

        /** init DatePickerDialog */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            datePickerDialog = DatePickerDialog(requireContext())

        } else throw Exception("API Level is lower than 24")
        datePickerDialog.setOnDateSetListener(this)


        /** retrieve Navigation */
        isAdd = navigationArgs.isAdd
        if(isAdd) {
            selectedDailyDay = DailyDay(title = "")

            binding.date.text = datePickerDialog.datePicker.toEpochTimeSeconds().toOffsetDateTime().toDateFormat().toEditable()
            binding.delete.visibility = View.GONE
        }
        else {
            val id = navigationArgs.dailyDayId
            viewModel.retrieveDailyDay(id).observe(this.viewLifecycleOwner){
                selectedDailyDay = it
                bind(selectedDailyDay)
            }
        }
    }



    private fun bind(dailyDay: DailyDay){
        binding.apply {
            title.text = dailyDay.title.toEditable()
            date.text = dailyDay.date.toOffsetDateTime().toDateFormat().toEditable()
        }
    }


    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        binding.date.text = view.toEpochTimeSeconds().toOffsetDateTime().toDateFormat().toEditable()
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
        }
    }


    private fun fetchInput(): Boolean {
        val title: String = binding.title.text.toString()
        val date:Long = datePickerDialog.datePicker.toEpochTimeSeconds()

        if(title.isEmpty()) return false
        else {
            fetchedDailyDay = selectedDailyDay.copy(title = title, date = date)
            return true
        }
    }


    private fun notifyEmptyInput() {
        val message = "Empty title!"
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun addItem() {
        viewModel.insertDailyDay(fetchedDailyDay)
    }
    private fun updateItem() {
        viewModel.updateDailyDay(fetchedDailyDay)
    }
    private fun deleteItem() {
        viewModel.deleteDailyDay(selectedDailyDay)
    }

}


