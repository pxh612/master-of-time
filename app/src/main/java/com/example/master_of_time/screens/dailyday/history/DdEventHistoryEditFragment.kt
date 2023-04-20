package com.example.master_of_time.screens.dailyday.history

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.master_of_time.R
import com.example.master_of_time.databinding.DdEventHistoryEditFragmentBinding
import timber.log.Timber

class DdEventHistoryEditFragment: Fragment(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {
    private lateinit var binding: DdEventHistoryEditFragmentBinding

    var intent: Int = -1
    val ADD_INTENT = 0
    val EDIT_INTENT = 1

    private var eventHistoryId = -1L
        set(value){
            field = value

            intent = when(value){
                -1L -> ADD_INTENT
                else -> EDIT_INTENT
            }
        }
    private var eventId = -1L
        set(value){
            field = value
        }

    val datePickerDialog: DatePickerDialog by lazy {
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

    }

    private fun retrieveParentData() {
        val navigationArgs: DdEventHistoryEditFragmentArgs by navArgs()
        eventHistoryId = navigationArgs.eventHistoryId
        eventId = navigationArgs.eventId
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.submitButton -> {
                Timber.d("reponsive")
            }
            R.id.dateEdit -> {
                Timber.d("reponsive")
                datePickerDialog.show()
            }
        }
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {

    }
}