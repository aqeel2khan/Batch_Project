package com.example.bottomanimationmydemo.view.activity

import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomanimationmydemo.adapter.TestRecyclerViewAdapter
import com.example.bottomanimationmydemo.databinding.ActivityRecyclerviewBinding
import com.example.bottomanimationmydemo.view.BaseActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
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