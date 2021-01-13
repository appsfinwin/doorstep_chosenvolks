package com.finwin.doorstep.rightview.home.bc_report.daily_report


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cn.pedant.SweetAlert.SweetAlertDialog

import com.finwin.doorstep.rightview.R
import com.finwin.doorstep.rightview.databinding.DailyReportFragmentBinding
import com.finwin.doorstep.rightview.home.bc_report.daily_report.action.DailyReportAction
import com.finwin.doorstep.rightview.home.home_activity.HomeActivity


class DailyReportFragment : Fragment() {

    companion object {
        fun newInstance() = DailyReportFragment()
    }

    private lateinit var viewModel: DailyReportViewModel
    private lateinit var binding : DailyReportFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        HomeActivity.activityHomeBinding.appBar.tvHeading.text = "daily collection report"
        binding=DataBindingUtil.inflate(inflater, R.layout.daily_report_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(DailyReportViewModel::class.java)
        binding.viewmodel=viewModel

        viewModel.initLoading(activity)
        viewModel.getDailyReport()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.mAction.observe(viewLifecycleOwner, Observer {
            when(it.action)
            {
                DailyReportAction.DAILY_REPORT_SUCCESS->
                {
                    viewModel.cancelLoading()
                    viewModel.setData(it.bcReportResponse.bc_report.data)
                }

                DailyReportAction.API_ERROR->{
                    viewModel.cancelLoading()
                    SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("ERROR!")
                        .setContentText(it.error)
                        .setConfirmClickListener {
                            getFragmentManager()?.popBackStack();
                            it.cancel()
                        }
                        .show()
                }
                
            }
        })
    }

    override fun onStop() {
        super.onStop()
        viewModel.mAction.value= DailyReportAction(DailyReportAction.DEFAULT)
    }

}