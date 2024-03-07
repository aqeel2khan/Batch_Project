package com.dev.batchfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.dev.batchfinal.databinding.ActivityMainBinding

import com.dev.batchfinal.utils.makeGone
import com.dev.batchfinal.utils.makeVisible
import com.dev.batchfinal.view.account.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
        navController = navHostFragment.navController

        binding.ivHome.setImageResource(R.drawable.sel_home)

        bottomNavigationClicks()
        bottonClicks()
    }

    private fun bottonClicks() {
        binding.imProfile.setOnClickListener {
            startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
        }
    }

    private fun bottomNavigationClicks() {
        binding.ivHome.setOnClickListener {
            binding.tvHeading.text = resources.getString(R.string.batchboard)
            binding.ivHome.setImageResource(R.drawable.sel_home)
            binding.ivWorkout.setImageResource(R.drawable.iv_workout)
            binding.ivMeal.setImageResource(R.drawable.iv_meal)
            binding.ivShop.setImageResource(R.drawable.iv_shoping)
            binding.ivScan.setImageResource(R.drawable.iv_scaning)
            navigateToRootNode(R.id.home_navigation)
        }
        binding.ivWorkout.setOnClickListener {
            binding.tvHeading.text = resources.getString(R.string.workout_batchs)
            binding.ivHome.setImageResource(R.drawable.iv_home)
            binding.ivWorkout.setImageResource(R.drawable.sel_training)
            binding.ivMeal.setImageResource(R.drawable.iv_meal)
            binding.ivShop.setImageResource(R.drawable.iv_shoping)
            binding.ivScan.setImageResource(R.drawable.iv_scaning)
            navigateToRootNode(R.id.trainer_navigation)
        }
        binding.ivMeal.setOnClickListener {
            binding.tvHeading.text = resources.getString(R.string.txt_meal_title)
            binding.ivHome.setImageResource(R.drawable.iv_home)
            binding.ivWorkout.setImageResource(R.drawable.iv_workout)
            binding.ivMeal.setImageResource(R.drawable.sel_meal)
            binding.ivShop.setImageResource(R.drawable.iv_shoping)
            binding.ivScan.setImageResource(R.drawable.iv_scaning)
            navigateToRootNode(R.id.meal_navigation)
        }
        binding.ivShop.setOnClickListener {
            binding.tvHeading.text = resources.getString(R.string.shop_title)
            binding.ivHome.setImageResource(R.drawable.iv_home)
            binding.ivWorkout.setImageResource(R.drawable.iv_workout)
            binding.ivMeal.setImageResource(R.drawable.iv_meal)
            binding.ivShop.setImageResource(R.drawable.sel_shopping)
            binding.ivScan.setImageResource(R.drawable.iv_scaning)
            navigateToRootNode(R.id.shop_navigation)
        }
        binding.ivScan.setOnClickListener {
            binding.tvHeading.text = resources.getString(R.string.scan_title)
            binding.ivHome.setImageResource(R.drawable.iv_home)
            binding.ivWorkout.setImageResource(R.drawable.iv_workout)
            binding.ivMeal.setImageResource(R.drawable.iv_meal)
            binding.ivShop.setImageResource(R.drawable.iv_shoping)
            binding.ivScan.setImageResource(R.drawable.sel_scanning)
            navigateToRootNode(R.id.scan_navigation)
        }
    }

    fun handleHeader(isVisible: Boolean = true, perform: () -> Unit = {}) {
        if (isVisible) {
            binding.headerTitle.makeVisible()
        } else {
            binding.headerTitle.makeGone()
        }
    }

    fun handleBottomBar(isVisible: Boolean = true, perform: () -> Unit = {}) {
        if (isVisible) {
            binding.rlBottomLayout.makeVisible()
        } else {
            binding.rlBottomLayout.makeGone()
        }
    }

    fun handleTitle(headerTitle: String, perform: () -> Unit = {}){
        binding.tvHeading.text = headerTitle
        when (headerTitle) {
            resources.getString(R.string.workout_batchs) -> {
                binding.ivHome.setImageResource(R.drawable.iv_home)
                binding.ivWorkout.setImageResource(R.drawable.sel_training)
                binding.ivMeal.setImageResource(R.drawable.iv_meal)
                binding.ivShop.setImageResource(R.drawable.iv_shoping)
                binding.ivScan.setImageResource(R.drawable.iv_scaning)
            }
            resources.getString(R.string.txt_meal_title) -> {
                binding.ivHome.setImageResource(R.drawable.iv_home)
                binding.ivWorkout.setImageResource(R.drawable.iv_workout)
                binding.ivMeal.setImageResource(R.drawable.sel_meal)
                binding.ivShop.setImageResource(R.drawable.iv_shoping)
                binding.ivScan.setImageResource(R.drawable.iv_scaning)
            }
            resources.getString(R.string.shop_title) -> {
                binding.ivHome.setImageResource(R.drawable.iv_home)
                binding.ivWorkout.setImageResource(R.drawable.iv_workout)
                binding.ivMeal.setImageResource(R.drawable.iv_meal)
                binding.ivShop.setImageResource(R.drawable.sel_shopping)
                binding.ivScan.setImageResource(R.drawable.iv_scaning)
            }
        }
    }


    private fun navigateToRootNode(startDestinationId: Int, bundle: Bundle? = null) {
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.enter_from_bottom)
            .setExitAnim(R.anim.exit_to_top)
            .setPopEnterAnim(R.anim.exit_to_top)
            .setPopExitAnim(R.anim.exit_to_bottom)
            .setPopUpTo(startDestinationId, false)
            .build()

        navController.navigate(startDestinationId, bundle, navOptions)
    }
}