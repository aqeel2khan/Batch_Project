package com.dev.batchfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.dev.batchfinal.app_common.AppBaseActivity
import com.dev.batchfinal.app_common.BaseActivity
import com.dev.batchfinal.app_modules.account.view.ProfileActivity
import com.dev.batchfinal.app_session.UserSessionManager
import com.dev.batchfinal.app_utils.makeGone
import com.dev.batchfinal.app_utils.makeVisible
import com.dev.batchfinal.databinding.ActivityLoginBinding
import com.dev.batchfinal.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppBaseActivity<ActivityMainBinding>() {
  //  private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val DOUBLE_CLICK_TIME_DELTA: Long = 2000
    private var lastClickTime: Long = 0
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)
    override fun initUI() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
        navController = navHostFragment.navController
        binding.ivHome.setImageResource(R.drawable.sel_home)
        bottomNavigationClicks()
        bottonClicks()
    }

    /*    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
            navController = navHostFragment.navController


            binding.ivHome.setImageResource(R.drawable.sel_home)

            bottomNavigationClicks()
            bottonClicks()
        }*/

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

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val navHostFragment=  supportFragmentManager.findFragmentById(R.id.nav_host_container)
        val currentFragment = navHostFragment!!.childFragmentManager.primaryNavigationFragment
        if (currentFragment != null) {
            val fragmentClassName = currentFragment.javaClass.name
            when {
                fragmentClassName.equals("com.dev.batchfinal.app_modules.workout_motivator.view_frag.TrainingFragment") -> {
                    println("Current fragment class name: $fragmentClassName")
                    binding.tvHeading.text = resources.getString(R.string.batchboard)
                    binding.ivHome.setImageResource(R.drawable.sel_home)
                    binding.ivWorkout.setImageResource(R.drawable.iv_workout)
                    binding.ivMeal.setImageResource(R.drawable.iv_meal)
                    binding.ivShop.setImageResource(R.drawable.iv_shoping)
                    binding.ivScan.setImageResource(R.drawable.iv_scaning)
                    navigateToRootNode(R.id.home_navigation)
                }
                fragmentClassName.equals("com.dev.batchfinal.app_modules.meals.meal_unpurchase.view.fragment.MealFragment") -> {
                    binding.tvHeading.text = resources.getString(R.string.workout_batchs)
                    binding.ivHome.setImageResource(R.drawable.iv_home)
                    binding.ivWorkout.setImageResource(R.drawable.sel_training)
                    binding.ivMeal.setImageResource(R.drawable.iv_meal)
                    binding.ivShop.setImageResource(R.drawable.iv_shoping)
                    binding.ivScan.setImageResource(R.drawable.iv_scaning)
                    navigateToRootNode(R.id.trainer_navigation)
                }
                fragmentClassName.equals("com.dev.batchfinal.app_modules.shopping.view_frag.ShoppingFragment") -> {
                    binding.tvHeading.text = resources.getString(R.string.txt_meal_title)
                    binding.ivHome.setImageResource(R.drawable.iv_home)
                    binding.ivWorkout.setImageResource(R.drawable.iv_workout)
                    binding.ivMeal.setImageResource(R.drawable.sel_meal)
                    binding.ivShop.setImageResource(R.drawable.iv_shoping)
                    binding.ivScan.setImageResource(R.drawable.iv_scaning)
                    navigateToRootNode(R.id.meal_navigation)
                }
                fragmentClassName.equals("com.dev.batchfinal.app_modules.scanning.view_frag.ScanningFragment") -> {
                    binding.tvHeading.text = resources.getString(R.string.shop_title)
                    binding.ivHome.setImageResource(R.drawable.iv_home)
                    binding.ivWorkout.setImageResource(R.drawable.iv_workout)
                    binding.ivMeal.setImageResource(R.drawable.iv_meal)
                    binding.ivShop.setImageResource(R.drawable.sel_shopping)
                    binding.ivScan.setImageResource(R.drawable.iv_scaning)
                    navigateToRootNode(R.id.shop_navigation)
                }
                else -> {
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                        super.onBackPressed() // Or finish() if this is your main activity
                    } else {
                        lastClickTime = currentTime
                        //Toast.makeText(this, "Click on bottom icon to change tab", Toast.LENGTH_SHORT).show()
                        Toast.makeText(this, "Double click to exit.", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }else
        {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                super.onBackPressed() // Or finish() if this is your main activity
            } else {
                lastClickTime = currentTime
                //Toast.makeText(this, "Click on bottom icon to change tab", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "Double click to exit.", Toast.LENGTH_SHORT).show()

            }
        }


    }

}







