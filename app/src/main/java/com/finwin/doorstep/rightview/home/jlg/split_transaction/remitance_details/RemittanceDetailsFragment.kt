package com.finwin.doorstep.rightview.home.jlg.split_transaction.remitance_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finwin.doorstep.rightview.home.jlg.JlgAction
import com.finwin.doorstep.rightview.home.jlg.split_transaction.SplitTransactionActivity

import com.finwin.doorstep.rightview.R
import com.finwin.doorstep.rightview.databinding.RemittanceDetailsFragmentBinding
import com.finwin.doorstep.rightview.home.jlg.split_transaction.remitance_details.adapter.RemittanceDetailsAdapter

class RemittanceDetailsFragment : Fragment() {


    companion object {
        fun newInstance() = RemittanceDetailsFragment()
    }

    private  val TAG = "RemittanceDetailsFragme"
    private lateinit var viewModel: RemittanceDetailsViewModel
    private lateinit var binding: RemittanceDetailsFragmentBinding
    private lateinit var adapter: RemittanceDetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(
            inflater,
            R.layout.remittance_details_fragment,
            container,
            false
        )
        viewModel = ViewModelProviders.of(this).get(RemittanceDetailsViewModel::class.java)
        binding.viewModel=viewModel

        setupRecyclerview(binding.rvRemittance)
        return binding.root
    }

    private fun setupRecyclerview(rvRemittance: RecyclerView) {

        rvRemittance.layoutManager=LinearLayoutManager(activity)
        adapter= RemittanceDetailsAdapter()
        rvRemittance.setHasFixedSize(true)
        rvRemittance.adapter=adapter
        observeAdapter(adapter)
    }

    private fun observeAdapter(adapter: RemittanceDetailsAdapter) {

        adapter.mAction.observe(viewLifecycleOwner, Observer {
            when (it.action) {
                JlgAction.SELECT_ACCOUNT -> {
                   // if(  (activity as SplitTransactionActivity).getSelectedAccountsLiveData().contains(it.dat))
                   // {
                        (activity as SplitTransactionActivity?)?.insertData(it.dat)
                   // }
//                    add(
//                        it.dat
//
//                    )
                    //Log.d(TAG, "observeAdapter: "+ (activity as SplitTransactionActivity?)?.getSelectedAccountsLiveData()?.size)
                }
                JlgAction.DE_SELECT_ACCOUNT -> {
//                    (activity as SplitTransactionActivity?)?.getSelectedAccountsLiveData()?.remove(
//                        it.dat
//                    )
                    (activity as SplitTransactionActivity?)?.removeData(it.dat)

                   // Log.d(TAG, "observeAdapter: "+ (activity as SplitTransactionActivity?)?.getSelectedAccountsLiveData()?.size)
                }
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as SplitTransactionActivity?)?.disableTab(0)

           viewModel.mAction.observe(viewLifecycleOwner, Observer {
               when (it.action) {
                   JlgAction.CLICK_NEXT_FROM_REMITTANCE_DETAILS -> {
                       (activity as SplitTransactionActivity?)?.gotoNext()
                       (activity as SplitTransactionActivity?)?.enableTab(1)
                   }
                   JlgAction.CLICK_PREVIOUS_FROM_REMITTANCE_DETAILS -> {
                       (activity as SplitTransactionActivity?)?.gotoPrevious()
                       (activity as SplitTransactionActivity?)?.disableTab(1)
                   }
               }
           })

        (activity as SplitTransactionActivity?)?.getAccountsLiveData()?.observe(viewLifecycleOwner, Observer {

            adapter.setData(it)
            adapter.notifyDataSetChanged()

        })



    }
}