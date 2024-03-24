package com.dev.batchfinal.app_modules.account.minterface

import com.dev.batchfinal.app_modules.account.model.MotivatorFollowingData

interface FollowingCallback {

    fun onClickMotivator(position:Int, posData: MotivatorFollowingData)
}