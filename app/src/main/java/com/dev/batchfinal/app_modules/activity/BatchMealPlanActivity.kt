package com.dev.batchfinal.app_modules.activity

//@AndroidEntryPoint
/*
class BatchMealPlanActivity : BaseActivity<ActivityBatchMealPlanBinding>() {
    private val viewModel: AllViewModel by viewModels()
    var str: String? = null
    var name = ArrayList(
        Arrays.asList("Breakfast", "Lunch & Dinner", "Snack", "Desserts" ))
    */
/*main Act*//*

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        buttonClicks()
        setupMealPlanList()
        setUpAllMealsAdapter()
    }

    private fun setupMealPlanList() {
        binding.recyclerMealPlan.apply {
            layoutManager = LinearLayoutManager(this@BatchMealPlanActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = MealPlanListAdapter(this@BatchMealPlanActivity, name, object : PositionItemClickListener<Int>{
                override fun onPositionItemSelected(item: String, postions: Int) {
                    setUpAllMealsAdapter()
                }
            })
        }
    }

    private fun setUpAllMealsAdapter() {
        binding.recyclerAllMeal.apply {
            layoutManager = GridLayoutManager(this@BatchMealPlanActivity, 2)
            adapter = AllTypeOfMealAdapter(this@BatchMealPlanActivity, name,
                object : PositionItemClickListener<Int> {
                    override fun onPositionItemSelected(item: String, postions: Int) {
//                    startActivity(Intent(this, MotivatorDetailActivity::class.java))
                    }
                })
        }
    }

    private fun buttonClicks() {
        binding.imgBack.setOnClickListener {
           onBackPressed()
        }
        binding.txtAddPromo.setOnClickListener {
            showBottomSheetDialog()
        }
        binding.btnSubscribePlan.setOnClickListener {
            startActivity(Intent(this@BatchMealPlanActivity, RegistrationActivity::class.java).putExtra("batchMeal", "BatchMeal"))
        }
    }

    private fun showBottomSheetDialog() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        val ll_bottom_change_course = dialog.findViewById<LinearLayout>(R.id.ll_bottom_change_course)
        val ll_add_promo = dialog.findViewById<LinearLayout>(R.id.ll_add_promo)
        val btn_add = dialog.findViewById<Button>(R.id.btn_add)
        val add_promo_code = dialog.findViewById<EditText>(R.id.add_promo_code)
        ll_bottom_change_course!!.visibility = View.GONE
        ll_add_promo!!.visibility = View.VISIBLE
        btn_add!!.setOnClickListener {
            //code for save week price
            showReviewSubmitDialog()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showReviewSubmitDialog() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        val ll_bottom_change_course = dialog.findViewById<LinearLayout>(R.id.ll_bottom_change_course)
        val ll_review_submit = dialog.findViewById<LinearLayout>(R.id.ll_review_submit)
        val btn_ok = dialog.findViewById<Button>(R.id.btn_ok)
        val success_msg = dialog.findViewById<TextView>(R.id.success_msg)
        val content = dialog.findViewById<TextView>(R.id.content)
        ll_bottom_change_course!!.visibility = View.GONE
        ll_review_submit!!.visibility = View.VISIBLE
        content!!.visibility = View.GONE
        success_msg!!.text = "Promo-code applied \nsuccessfully!"
        btn_ok!!.setOnClickListener {
            //code for save week price
            binding.txtAddPromo.text = resources.getString(R.string.txt_remove_pro)
            dialog.dismiss()
        }


        dialog.show()
    }

    override fun getViewBinding() = ActivityBatchMealPlanBinding.inflate(layoutInflater)

    companion object {
        fun getBundle(s: String): Bundle? {
            val bundle = Bundle()
            return bundle
        }
    }

    override fun onResume() {
//        handleHeader(false)
//        handleBottomBar(false)
        super.onResume()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}*/
