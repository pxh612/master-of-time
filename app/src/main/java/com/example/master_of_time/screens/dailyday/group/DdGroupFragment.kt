package com.example.master_of_time.screens.dailyday.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.master_of_time.R
import com.example.master_of_time.databinding.DdGroupFragmentBinding
import timber.log.Timber

class DdGroupFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: DdGroupFragmentBinding
    private lateinit var dialogFragment: DdGroupEditDialogFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dd_group_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        /** init View */
        binding.run {



            add.setOnClickListener(this@DdGroupFragment)
            back.setOnClickListener(this@DdGroupFragment)

        }
    }

    override fun onClick(view: View) {
        Timber.v("> reponsive click")
        when (view.id) {
            R.id.back -> findNavController().popBackStack()
            R.id.add -> showDialog()
        }
    }

    private fun showDialog() {
        Timber.v("> clicked to show dialogFragment")
        val action = DdGroupFragmentDirections.actionDailyDayGroupFragmentToDailyDayGroupEditDialogFragment()
        requireView().findNavController().navigate(action)
    }

    fun testClick(view: View){
        Timber.d("> databinding onclick")
    }
    fun testClickNoArgument(){
        Timber.d("> databinding onclick testClickNoArgument")
    }

}
