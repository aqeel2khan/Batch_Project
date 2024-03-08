package com.dev.batchfinal.app_modules.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.batchfinal.R
import com.dev.batchfinal.databinding.FragmentEducationBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import java.util.Arrays
@AndroidEntryPoint
class MotivatorFragment : Fragment() {
   private lateinit var binding: FragmentEducationBinding
    var techerName = ArrayList(
        Arrays.asList(
            "Bessie Cooper", "Leslie Alexander", "Jenny Wilson", "Bessie Cooper", "Leslie Alexander", "Jenny Wilson" )
    )
    var profesion = ArrayList(
        Arrays.asList(
            "Yoga, pilates", "Cardio stretching", "Mobility", "Yoga, pilates", "Cardio stretching", "Mobility" )
    )
    var courseImg = ArrayList(Arrays.asList(
        R.drawable.avtar, R.drawable.normal_boy,
        R.drawable.profile_image, R.drawable.avtar, R.drawable.normal_boy,
        R.drawable.profile_image)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEducationBinding.inflate(layoutInflater)
        setMotivatorListAdapter(courseImg, techerName, profesion)
        return binding.root
    }

    private fun setMotivatorListAdapter(courseImg: java.util.ArrayList<Int>, techerName: java.util.ArrayList<String>, profesion: java.util.ArrayList<String>) {
        binding.recyclerMotivator.layoutManager = GridLayoutManager(requireActivity(), 2)
//        binding.recyclerMotivator.adapter = MotivatorListAdapter(requireContext(), courseImg,techerName, profesion,
//            object : PositionItemClickListener<Int> {
//                override fun onPositionItemSelected(item: String, postions: Int) {
//                    activity!!.startActivity(Intent(context, MotivatorDetailActivity::class.java))
//                }
//            })
    }

//    private fun setUpBasicDetail() {
//        binding.educationType.layoutManager = GridLayoutManager(requireActivity(), 2)
//        binding.educationType.adapter = MotivatorListAdapter(requireActivity())
//    }
}