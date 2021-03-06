package com.finwin.doorstep.chosenvolks.home.bc_report.daily_report

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.chosenvolks.retrofit.ApiInterface
import com.finwin.doorstep.chosenvolks.home.bc_report.daily_report.action.DailyReportAction

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody

class DailyReportRepository {
    lateinit var mAction: MutableLiveData<DailyReportAction>


    @SuppressLint("CheckResult")
    fun getDailyReport(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.bcReport(body)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.bc_report.data != null) {
                        mAction.value = DailyReportAction(
                            DailyReportAction.DAILY_REPORT_SUCCESS,
                            response
                        )
                    } else {

                        mAction.value = DailyReportAction(DailyReportAction.API_ERROR, response.bc_report.error+" on "+response.bc_report.TXN_DATE)
                    }
                }, { error ->
                    mAction.value =
                        DailyReportAction(DailyReportAction.API_ERROR, error.message.toString())
                }
            )

    }
}