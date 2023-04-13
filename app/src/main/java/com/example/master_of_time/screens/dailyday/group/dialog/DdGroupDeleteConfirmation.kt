package com.example.master_of_time.screens.dailyday.group.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.master_of_time.R
import com.example.master_of_time.databinding.DdDroupDeletionConfirmationBinding
import com.example.master_of_time.plural
import com.example.master_of_time.screens.dailyday.group.viewmodel.DdGroupViewModel
import com.example.master_of_time.toEditable
import timber.log.Timber

class DdGroupDeleteConfirmation: DialogFragment(), View.OnClickListener {
    private lateinit var binding: DdDroupDeletionConfirmationBinding
    private lateinit var viewModel: DdGroupViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DdDroupDeletionConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retrieveParentData()

        binding.run{
            bindUI = this@DdGroupDeleteConfirmation

        }
    }

    private fun retrieveParentData() {
        val navigationArgs: DdGroupDeleteConfirmationArgs by navArgs()
        val groupCount = navigationArgs.groupCount
        val groupTitle = navigationArgs.groupTitle

        binding.run{
            dialogTitle.text = "Delete \"$groupTitle\""
            message.text = "This group contain $groupCount event${plural(groupCount)} in it. Are you sure you want to delete it?"
        }
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.cancel -> findNavController().popBackStack()
            R.id.submit -> sendConfirmationData()
        }
    }

    private fun sendConfirmationData() {
        findNavController().popBackStack()
        setFragmentResult("DdGroupDeleteConfirmation", bundleOf(
            "isConfirmed" to true
        ))
    }


}