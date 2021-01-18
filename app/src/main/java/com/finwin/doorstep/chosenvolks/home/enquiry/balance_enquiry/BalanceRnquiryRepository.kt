package com.finwin.doorstep.chosenvolks.home.enquiry.balance_enquiry

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.chosenvolks.home.enquiry.balance_enquiry.action.BalanceAction
import com.finwin.doorstep.chosenvolks.home.transactions.cash_deposit.CashDepositAction
import com.finwin.doorstep.chosenvolks.retrofit.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody

class BalanceRnquiryRepository {
    
    lateinit var mAction: MutableLiveData<BalanceAction>
    
    @SuppressLint("CheckResult")
    fun balanceEnquiry(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.balanceEnqury(body)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.balance != null) {
                        mAction.value = BalanceAction(
                            BalanceAction.BALANCE_ENQUIRY_SUCCESS,
                            response
                        )
                    } else {

                        mAction.value = BalanceAction(CashDepositAction.API_ERROR, response)
                    }
                }, { error ->
                    mAction.value =
                        BalanceAction(BalanceAction.API_ERROR, error.message.toString())
                }
            )

    }

    @SuppressLint("CheckResult")
    fun getAccountHolder(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.getAccountHolder(body)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.account != null) {
                        mAction.value = BalanceAction(
                            BalanceAction.GET_ACCOUNT_HOLDER_SUCCESS,
                            response
                        )
                    } else {

                        mAction.value = BalanceAction(BalanceAction.API_ERROR, "")
                    }
                }, { error ->
                    mAction.value =
                        BalanceAction(BalanceAction.API_ERROR, error.message.toString())
                }
            )

    }
}