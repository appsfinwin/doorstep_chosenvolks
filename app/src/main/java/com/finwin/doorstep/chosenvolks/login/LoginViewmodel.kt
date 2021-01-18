package com.finwin.doorstep.chosenvolks.login

import android.content.Context
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.chosenvolks.login.action.LoginAction
import com.finwin.doorstep.chosenvolks.retrofit.ApiInterface
import com.finwin.doorstep.chosenvolks.retrofit.RetrofitClient
import com.finwin.doorstep.chosenvolks.utils.Services
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.*


public  class LoginViewmodel() : ViewModel() {

    var of_username = ObservableField("")
    var of_password = ObservableField("")
    lateinit var apiInterface: ApiInterface
    var repository: LoginRepository
    var compositeDisposable:CompositeDisposable
    var mAction: MutableLiveData<LoginAction>
    init {
        compositeDisposable= CompositeDisposable()
        mAction=MutableLiveData()
        repository= LoginRepository(mAction)
    }

    var loading: SweetAlertDialog? = null

    fun initLoading(context: Context?) {
        loading = Services.showProgressDialog(context)
    }

    fun cancelLoading() {
        if (loading != null) {
            loading!!.cancel()
            loading = null
        }
    }


    fun clickLogin(view: View) {
        when {
            of_username.get() == "" -> {
                Services.showSnakbar("Username cannot be ampty", view)
            }
            of_password.get() == "" -> {
                Services.showSnakbar("Password cannot be ampty", view)
            }
            else -> {
                initLoading(view.context)
                loginApi()
            }
        }
    }

    private fun loginApi() {

        val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams["username"] = of_username.get()
        jsonParams["password"] = of_password.get()


        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        apiInterface = RetrofitClient().RetrofitClient()?.create(ApiInterface::class.java)!!
        repository.login(apiInterface, body)
    }

    fun getMasters() {

        apiInterface = RetrofitClient().RetrofitClient()?.create(ApiInterface::class.java)!!
        repository.getMasters(apiInterface)
    }


    override fun onCleared() {
        super.onCleared()
        mAction.value= LoginAction(LoginAction.DEFAULT)
    }

}