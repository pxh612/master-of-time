package com.example.master_of_time.screens.dailyday.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.master_of_time.databinding.DdGroupEditDialogFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DdGroupBottomSheet: BottomSheetDialogFragment() {

    private lateinit var binding: DdGroupEditDialogFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DdGroupEditDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


}