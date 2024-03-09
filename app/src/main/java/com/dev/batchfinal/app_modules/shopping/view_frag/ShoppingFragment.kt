package com.dev.batchfinal.app_modules.shopping.view_frag

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.dev.batchfinal.R
import com.dev.batchfinal.databinding.FragmentShoppingBinding
import com.dev.batchfinal.app_common.BaseFragment
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingFragment : BaseFragment<FragmentShoppingBinding>() {
    private val viewModel: AllViewModel by viewModels()

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        buttonClicks()
    }

    private fun buttonClicks() {

    }

    override fun getViewBinding() = FragmentShoppingBinding.inflate(layoutInflater)

    override fun onResume() {
        handleTitle(resources.getString(R.string.shop_title))
        super.onResume()
    }

    companion object {
        private const val id = "id"
        fun getBundle(id: String): Bundle {
            val bundle = Bundle()
            bundle.putString(id, id)
            return bundle
        }
    }
}