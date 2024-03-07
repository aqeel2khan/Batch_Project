package com.dev.batchfinal.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentManager
import com.dev.batchfinal.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NetworkConnectivityFragment : BottomSheetDialogFragment() {
    companion object {
        fun showLoader(fragmentManager: FragmentManager){
            try {
                NetworkConnectivityFragment().show(fragmentManager,"NetworkConnectivityFragment")
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
        fun dismissLoader(fragmentManager: FragmentManager){
            try {
                (fragmentManager.findFragmentByTag("NetworkConnectivityFragment") as BottomSheetDialogFragment).dismiss()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.network_connectivity_layout, container, false)

        val btn_got_it = view.findViewById<Button>(R.id.btn_got_it)
        btn_got_it.setOnClickListener {

        }
        return view
    }

}