package com.dev.batchfinal.app_common

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.dev.batchfinal.R

class LoaderFragment : DialogFragment() {

    companion object {
        fun showLoader(fragmentManager: FragmentManager){
            try {
                LoaderFragment().show(fragmentManager,"LoaderFragment")
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
        fun dismissLoader(fragmentManager: FragmentManager)
        {
            try {
               // (fragmentManager.findFragmentByTag("LoaderFragment") as DialogFragment).dismiss()

                val loaderFragment = fragmentManager.findFragmentByTag("LoaderFragment") as DialogFragment?
                if (loaderFragment != null) {
                    loaderFragment.dismiss()
                } else {
                    // Handle the case where the fragment is not found
                    Log.e("FragmentNotFound", "LoaderFragment not found in FragmentManager");
                    if (LoaderFragment().isVisible)
                        LoaderFragment().dismiss()
                }
            }catch (_:Exception){ }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_loader_upgraded, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
       // progressBar.background = null
        progressBar.setBackgroundResource(R.drawable.transparent_bg)

    }

}