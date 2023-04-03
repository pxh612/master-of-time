package com.example.master_of_time.screens.dailyday.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.master_of_time.R

class DailyDayGroupEditDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.dialog_fragment_event_group_edit, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog!!.setTitle("Add Group")
    }
//    override fun onViewCreated(view: View?, @Nullable savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // set DialogFragment title
//        dialog!!.setTitle("Dialog #$mNum")
//    }

}