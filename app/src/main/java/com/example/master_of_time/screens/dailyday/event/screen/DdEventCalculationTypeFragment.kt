package com.example.master_of_time.screens.dailyday.event.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.master_of_time.databinding.DdEventCalculationTypeFragmentBinding
import com.example.master_of_time.module.dailyday.DdEventCalculation
import com.example.master_of_time.screens.dailyday.event.DdEventEditViewModel
import com.example.master_of_time.screens.dailyday.event.adapter.CalculationTypeAdapter
import com.example.master_of_time.module.dailyday.DdEventCalculationType
import timber.log.Timber

class DdEventCalculationTypeFragment: Fragment(), View.OnClickListener,
    CalculationTypeAdapter.Listener {

    private lateinit var viewModel: DdEventEditViewModel
    private lateinit var binding: DdEventCalculationTypeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DdEventCalculationTypeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.d("create view of DdEventCalculationTypeFragment: Fragment(), View.OnClickListener {\n")

        val adapterList: List<DdEventCalculationType> = DdEventCalculation.GivenList
        val calculationTypeAdapter = CalculationTypeAdapter(list = adapterList, this)

        binding.run {
            bindUI = this@DdEventCalculationTypeFragment

            recyclerView.run {
                layoutManager = LinearLayoutManager(context)
                adapter = calculationTypeAdapter
            }
        }
    }


    override fun onClick(view: View) {
        when(view.id){

        }
    }

    override fun onClickCalculationType(dateCalculationTypeIndex: Int) {

        findNavController().popBackStack()
        setFragmentResult("DdEventCalculationTypeFragment", bundleOf(
            "DdEventCalculationType" to dateCalculationTypeIndex
        ))

    }



}