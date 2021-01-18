package com.finwin.doorstep.chosenvolks.home.transactions.cash_deposit

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.chosenvolks.retrofit.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody

class CashDepositRepository() {
    lateinit var mAction: MutableLiveData<CashDepositAction>

    @SuppressLint("CheckResult")
    fun getAccountHolder(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.getAccountHolder(body)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.account != null) {
                        mAction.value = CashDepositAction(
                            CashDepositAction.GET_ACCOUNT_HOLDER_SUCCESS,
                            response
                        )
                    } else {

                        mAction.value = CashDepositAction(CashDepositAction.API_ERROR, response)
                    }
                }, { error ->
                    mAction.value =
                        CashDepositAction(CashDepositAction.API_ERROR, error.message.toString())
                }
            )

    }

    @SuppressLint("CheckResult")
    fun cashDeposit(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.cashDeposit(body)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.receipt != null) {
                        mAction.value = CashDepositAction(
                            CashDepositAction.CASH_DEPOSIT_SUCCESS, response
                        )
                    } else {

                        mAction.value = CashDepositAction(CashDepositAction.API_ERROR, response)
                    }
                }, { error ->
                    mAction.value =
                        CashDepositAction(CashDepositAction.API_ERROR, error.message.toString())
                }
            )

    }
}