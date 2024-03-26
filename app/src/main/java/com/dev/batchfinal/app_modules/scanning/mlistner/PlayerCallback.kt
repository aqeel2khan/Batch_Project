package com.dev.batchfinal.app_modules.scanning.mlistner

import com.dev.batchfinal.app_modules.scanning.model.course_order_list.CourseDurationExercise

interface PlayerCallback {

    fun onClickVideoInformation(position:Int, posData: CourseDurationExercise)

    fun onClickClosePlayer(position:Int)


}