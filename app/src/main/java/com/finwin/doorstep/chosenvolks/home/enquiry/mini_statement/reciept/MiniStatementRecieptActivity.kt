package com.finwin.doorstep.chosenvolks.home.enquiry.mini_statement.reciept

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finwin.doorstep.chosenvolks.R
import com.finwin.doorstep.chosenvolks.databinding.ActivityMiniStatementRecieptBinding
import com.finwin.doorstep.chosenvolks.home.enquiry.mini_statement.action.MiniStatementAction
import com.finwin.doorstep.chosenvolks.home.enquiry.mini_statement.adapter.MiniStatementAdapter
import com.finwin.doorstep.chosenvolks.home.enquiry.mini_statement.pojo.MiniStatementProfile
import com.finwin.doorstep.chosenvolks.utils.Constants


class MiniStatementRecieptActivity : AppCompatActivity() {
    lateinit var viewmodel: MiniStatementReciepViewmodel
    lateinit var binding: ActivityMiniStatementRecieptBinding
    lateinit var adapter: MiniStatementAdapter
    //lateinit var profile: List<MiniStatementProfile>
    var profile: List<MiniStatementProfile> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mini_statement_reciept)
        viewmodel=ViewModelProvider(this).get(MiniStatementReciepViewmodel::class.java)
        binding.viewmodel=viewmodel

        viewmodel.accountNumber.set(intent.getStringExtra(Constants.ACCOUNT_NUMBER))

        setRecyclerview(binding.rvMiniStatement)
        viewmodel.initLoading(this)
        viewmodel.miniStatement()

        viewmodel.mAction.observe(this, Observer {
            when(it.action)
            {
                MiniStatementAction.GET_MINI_STATEMENT_SUCCESS->
                {
                    viewmodel.cancelLoading()

                    viewmodel.setData(it.miniStatementResponse.mini_statement.data)
                   var mini_s_profile: MiniStatementProfile = MiniStatementProfile(
                       it.miniStatementResponse.mini_statement.data.ACC_NO,
                       it.miniStatementResponse.mini_statement.data.CURRENT_BALANCE,
                       it.miniStatementResponse.mini_statement.data.MOBILE,
                       it.miniStatementResponse.mini_statement.data.NAME
                   )

                   profile= listOf(
                       MiniStatementProfile(
                       it.miniStatementResponse.mini_statement.data.ACC_NO,
                       it.miniStatementResponse.mini_statement.data.CURRENT_BALANCE,
                       it.miniStatementResponse.mini_statement.data.MOBILE,
                       it.miniStatementResponse.mini_statement.data.NAME
                   )
                   )
                    adapter.setProfile(profile)
                    adapter.setMiniStatement(it.miniStatementResponse.mini_statement.data.TRANSACTONS)
                    adapter.notifyDataSetChanged()
                }
            }
        })

    }

    private fun setRecyclerview(rvMiniStatement: RecyclerView) {
        adapter= MiniStatementAdapter()
        rvMiniStatement.layoutManager=LinearLayoutManager(this)
        rvMiniStatement.setHasFixedSize(true)
        rvMiniStatement.adapter=adapter

    }
}