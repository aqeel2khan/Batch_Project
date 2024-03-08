package com.dev.batchfinal.app_modules.activity

import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.batchfinal.adapter.TestRecyclerViewAdapter
import com.dev.batchfinal.databinding.ActivityRecyclerviewBinding
import com.dev.batchfinal.app_common.BaseActivity
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class RecyclerViewActivity : BaseActivity<ActivityRecyclerviewBinding>() {
    private val viewModel: AllViewModel by viewModels()

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        binding = ActivityRecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    override fun getViewBinding() = ActivityRecyclerviewBinding.inflate(layoutInflater)

    private fun setupView() {
        val ids: ArrayList<Int> = arrayListOf()
        ids.add(913065292)
        ids.add(911682135)
        ids.add(911607104)
        ids.add(318380844)

        /*ids.add(56282283)
        ids.add(318380844)
        ids.add(318173011)
        ids.add(318794329)
        ids.add(315632203)
        ids.add(19231868)*/

        val adapter = TestRecyclerViewAdapter(lifecycle, ids)
        binding.recyclerView.layoutManager = LinearLayoutManager(this@RecyclerViewActivity)
        binding.recyclerView.adapter = adapter
    }
}