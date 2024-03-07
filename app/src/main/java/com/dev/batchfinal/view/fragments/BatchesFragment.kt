package com.dev.batchfinal.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.batchfinal.databinding.FragmentBasicDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import java.util.Arrays
@AndroidEntryPoint
class BatchesFragment : Fragment() {
    private lateinit var binding: FragmentBasicDetailBinding
    var name = ArrayList(
        Arrays.asList(
            "Workout Batch", "Weight Loss", "Workout Batch" )
    )
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentBasicDetailBinding.inflate(layoutInflater)
//        setAllBatchesAdapter()
        return binding.root
    }

 /*   private fun setAllBatchesAdapter() {
        binding.recyclerBatches.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerBatches.adapter = AllBatchesAdapter(context, name, object :
            PositionItemClickListener<Int> {
            override fun onPositionItemSelected(item: String, postions: Int) {
                activity!!.startActivity(Intent(requireContext(), WeightLossBatchActivity::class.java))
            }
        })
    }*/

}