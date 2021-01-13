package com.finwin.doorstep.rightview.home.home_fragment

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finwin.doorstep.rightview.home.home_activity.HomeAction

class HomeFragmentViewmodel : ViewModel() {

    var mAction: MutableLiveData<HomeAction>

    init {
        mAction= MutableLiveData()
    }
    fun clickTransaction(view : View)
    {
        mAction.value= HomeAction(
            HomeAction.CLICK_TRANSACTION
        )
    }

    fun clickEnquiry(view : View)
    {
        mAction.value= HomeAction(
            HomeAction.CLICK_ENQUIRY
        )
    }

    fun clickBcReport(view : View)
    {
        mAction.value= HomeAction(
            HomeAction.CLICK_BC_REPORT
        )
    }

    fun clickAgentManegement(view : View)
    {
        mAction.value= HomeAction(
            HomeAction.CLICK_AGENT
        )
    }
}