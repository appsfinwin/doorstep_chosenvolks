package com.finwin.doorstep.chosenvolks.home.jlg.search_account_group

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.chosenvolks.retrofit.ApiInterface
import com.finwin.doorstep.chosenvolks.home.jlg.JlgAction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody

class SearchGroupRepository {
    lateinit var INSTANCE: SearchGroupRepository

    lateinit var mAction: MutableLiveData<JlgAction>
    public fun getInstance(): SearchGroupRepository {

        if (!:: INSTANCE.isInitialized)
        {
            INSTANCE= SearchGroupRepository()
        }
        return INSTANCE
    }

    @SuppressLint("CheckResult")
    fun searchGroup(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.getGroupSearch(body)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.status.equals("1")) {
                        mAction.value = JlgAction(
                            JlgAction.JLG_GET_GROUP_ACCOUNT_SUCCESS,
                            response
                        )
                    } else {

                        mAction.value = JlgAction(JlgAction.API_ERROR,response.msg )
                    }
                }, { error ->
                    mAction.value =
                        JlgAction(JlgAction.API_ERROR, error.message.toString())
                }
            )

    }

}