package com.finwin.doorstep.chosenvolks.home.enquiry.mini_statement

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.finwin.doorstep.chosenvolks.R
import com.finwin.doorstep.chosenvolks.databinding.FragmentMiniStatementBinding
import com.finwin.doorstep.chosenvolks.home.enquiry.mini_statement.action.MiniStatementAction
import com.finwin.doorstep.chosenvolks.home.enquiry.mini_statement.reciept.MiniStatementRecieptActivity
import com.finwin.doorstep.chosenvolks.home.home_activity.HomeActivity
import com.finwin.doorstep.chosenvolks.home.transactions.search_account.SearchActivity
import com.finwin.doorstep.chosenvolks.utils.Constants



class MiniStatementFragment : Fragment() {

    lateinit var binding: FragmentMiniStatementBinding
    lateinit var viewmodel: MiniStatementViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_mini_statement, container, false)
        viewmodel=ViewModelProvider(this).get(MiniStatementViewmodel::class.java)
        binding.viewmodel=viewmodel

        HomeActivity.activityHomeBinding.appBar.tvHeading.text="MINI STATEMENT"
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.mAction.observe(viewLifecycleOwner, Observer {
            when(it.action)
            {
                MiniStatementAction.CLICK_SEARCH->
                {
                    var intent: Intent = Intent(activity, SearchActivity::class.java)
                    startActivityForResult(intent, Constants.INTENT_SEARCH_ACCOUNT_FROM_MINI_STATEMENT)
                }

                MiniStatementAction.GET_ACCOUNT_HOLDER_SUCCESS->
                {
                    viewmodel.cancelLoading()
                    viewmodel.setAccountHolderData(it.getAccountHolderResponse.account)
                }

                MiniStatementAction.CLICK_MINI_STATEMENT->
                {
                    var intent: Intent = Intent(activity, MiniStatementRecieptActivity::class.java)
                    intent.putExtra(Constants.ACCOUNT_NUMBER,viewmodel.accountNumber.get())
                    startActivity(intent)
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MiniStatementFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== Constants.INTENT_SEARCH_ACCOUNT_FROM_MINI_STATEMENT)
        {
            if(data!=null) {
                var accountNumber: String? = data.getStringExtra("account_number")
                viewmodel.accountNumber.set(accountNumber)
            }else{
                viewmodel.accountNumber.set("")
            }
        }else if (requestCode== Constants.INTENT_RECIEPT_FROM_CASH_DEPOSIT)
        {
            viewmodel.reset()
        }
    }

    override fun onStop() {
        super.onStop()
        viewmodel.mAction.value= MiniStatementAction(MiniStatementAction.DEFAULT)
    }

}