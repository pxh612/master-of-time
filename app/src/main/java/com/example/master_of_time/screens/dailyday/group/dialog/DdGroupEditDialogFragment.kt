package com.example.master_of_time.screens.dailyday.group.dialog

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.drawerlayout.widget.DrawerLayout.LayoutParams
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.master_of_time.R
import com.example.master_of_time.database.AppDatabase
import com.example.master_of_time.database.table.DdGroup
import com.example.master_of_time.databinding.DdGroupEditDialogFragmentBinding
import com.example.master_of_time.screens.dailyday.group.viewmodel.DdGroupViewModel
import com.example.master_of_time.toEditable
import timber.log.Timber


class DdGroupEditDialogFragment : DialogFragment(), View.OnClickListener {


    private lateinit var binding: DdGroupEditDialogFragmentBinding
    private val viewModel: DdGroupViewModel by lazy {
        val dataSource = AppDatabase.getInstance(requireContext()).dailyDayDao()

        ViewModelProvider(
            requireActivity(), DdGroupViewModel.Factory(dataSource)
        )[DdGroupViewModel::class.java]
    }

    /** Data */
    var originalName: String = ""
    lateinit var name: String
    var isAdd: Boolean = true
    var groupId: Long? = null
    var newGroupId: Long = -1


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DdGroupEditDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disableSubmitButton()
        binding.run{
            bindUI = this@DdGroupEditDialogFragment

            name.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(editable: Editable?) {
                    val text  = editable.toString().trim()
                    when{
                        text.isNullOrBlank() -> disableSubmitButton()
                        text.startsWith(" ") -> disableSubmitButton()
                        (!isAdd) && (text == originalName) -> disableSubmitButton()
                        isDuplicateName(text) -> disableSubmitButton()
                        (text == getString(R.string.ddGroupPicker_ddEventEditFragment)) -> disableSubmitButton()
                        else -> enableSubmitButton()
                    }
                }
            })
        }

        retrieveParentData()
        retrieveChildData()

        showKeyboard()
    }

    private fun isDuplicateName(name: String): Boolean {
        // TODO
        return false
    }

    private fun enableSubmitButton() {
        binding.run{
            submitEnabled.visibility = View.VISIBLE
            submitDisabled.visibility = View.GONE
        }
    }

    private fun disableSubmitButton() {
        binding.run{
            submitEnabled.visibility = View.GONE
            submitDisabled.visibility = View.VISIBLE
        }

    }


    override fun onClick(view: View) {
        when(view.id) {
            R.id.cancel -> {
                findNavController().popBackStack()
            }
            R.id.submitEnabled -> {

                name = binding.name.text.toString().trim()
                if(name.isEmpty()) findNavController()
                else{
                    when(isAdd){
                        true -> DdGroup(name = name)
                            .let { viewModel.insertGroup(it) }

                        false -> groupId
                            ?.let { DdGroup(id = it, name = name) }
                            ?.let { viewModel.updateGroup(it) }
                    }
                    findNavController().popBackStack()
                }

            }
        }
    }

    private fun showKeyboard() {
        binding.name.requestFocus()
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.name, 0)
    }

    private fun retrieveParentData() {

        val navigationArgs: DdGroupEditDialogFragmentArgs by navArgs()
        isAdd = navigationArgs.isAdd
        lateinit var submitText: String
        when(isAdd){
            false -> {
                groupId = navigationArgs.groupId

                viewModel.getDdGroupName(groupId!!).observe(viewLifecycleOwner) {
                    originalName = it
                    binding.name.text = it.toEditable()
                }
                binding.dialogTitle.text = "Edit group"
                submitText = "OK"
            }
            true -> {
                groupId = null
                submitText = "ADD"
            }
        }
        binding.run{
            submitEnabled.text = submitText
            submitDisabled.text = submitText
        }
    }
    private fun retrieveChildData() {
        viewModel.newGroupId_LiveData.observe(viewLifecycleOwner){
            newGroupId = it
        }
    }

    private fun notifyOnDestroy(){
        setFragmentResult("DdGroupEditDialogFragment", bundleOf(
            "onDestroy" to true,
            "newGroupId" to newGroupId
        ))
    }

    override fun onDestroy() {
        super.onDestroy()
        notifyOnDestroy()
    }

}