package com.finwin.doorstep.chosenvolks.home.agent_management

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AgentManagementViewModel : ViewModel() {

     var mAction: MutableLiveData<Int> = MutableLiveData()

    public fun clickChangePassword(view: View)
    {
        mAction.value=AgentManagementFragment.CLICK_CHANGE_PASSWORD
    }

    override fun onCleared() {
        super.onCleared()
        mAction.value=
           AgentManagementFragment.DEFAULT
    }
}