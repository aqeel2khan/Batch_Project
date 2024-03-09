package com.dev.batchfinal.app_modules.batchboard.view_frag

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.batchfinal.R
import com.dev.batchfinal.adapter.DemoAllBatchesAdapter
import com.dev.batchfinal.adapter.DemoMotivatorListAdapter
import com.dev.batchfinal.adapter.SliderAdapter
import com.dev.batchfinal.databinding.FragmentHomeBinding
import com.dev.batchfinal.`interface`.PositionItemClickListener
import com.dev.batchfinal.app_modules.activity.MotivatorDetailActivity
import com.dev.batchfinal.app_modules.activity.WeightLossActivity
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import java.util.Arrays


@AndroidEntryPoint
class HomeFragment : Fragment() {
   private lateinit var binding: FragmentHomeBinding

    private val images: IntArray = intArrayOf(
        R.drawable.exercise_image,
        R.drawable.food,
        R.drawable.meal_bg,
        R.drawable.exercise_image
    )
    var name = ArrayList(
        Arrays.asList(
            "Workout Batch", "Weight Loss", "Workout Batch" )
    )
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
    private var adapter: SliderAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        buttonClicks()
        adapter = SliderAdapter(images)

        binding.imageSlider.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR;
        binding.imageSlider.setSliderAdapter(adapter!!)
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.SLIDE)
        // on below line setting scroll time for slider view
        binding.imageSlider.scrollTimeInSec = 3
        // on below line setting auto cycle for slider view.
        binding.imageSlider.isAutoCycle = true
        binding.imageSlider.startAutoCycle()
        setUpBatchesAdapter()
        setMotivatorListAdapter(courseImg, techerName, profesion)
        return binding.root
//        return inflater.inflate(R.layout.fragment_home, container, false)


    }

    private fun setMotivatorListAdapter(courseImg: ArrayList<Int>, techerName: ArrayList<String>, profesion: ArrayList<String>) {
        binding.recyclerMotivators.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = DemoMotivatorListAdapter(requireContext(), courseImg,techerName, profesion,
                object : PositionItemClickListener<Int> {
                    override fun onPositionItemSelected(item: String, postions: Int) {
                        activity!!.startActivity(Intent(context, MotivatorDetailActivity::class.java))
                    }
                })
        }
    }

    private fun setUpBatchesAdapter() {
        binding.recyclerWorkbatches.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = DemoAllBatchesAdapter(requireContext(), name, object :
                PositionItemClickListener<Int> {
                override fun onPositionItemSelected(item: String, postions: Int) {
                   /* activity!!.startActivity(
                        Intent(
                            requireContext(),
                            CourseDetailActivity::class.java
                        )
                    )*/
                    requireContext().startActivity(Intent(requireContext(), WeightLossActivity::class.java))
                }
            })
        }
    }

    private fun buttonClicks() {
        /*binding.btnTraining.setOnClickListener {
            findNavController().navigate(
                R.id.action_batchboardFragment_to_trainingFragment,
                TrainingFragment.getBundle("")
            )
        }

        binding.btnMeal.setOnClickListener {
            findNavController().navigate(
                R.id.action_batchboardFragment_to_mealFragment,
                MealFragment.getBundle("")
            )
        }
        binding.btnShop.setOnClickListener {
            findNavController().navigate(
                R.id.action_batchboardFragment_to_shopFragment,
                ShoppingFragment.getBundle("")
            )
        }*/
    }
}