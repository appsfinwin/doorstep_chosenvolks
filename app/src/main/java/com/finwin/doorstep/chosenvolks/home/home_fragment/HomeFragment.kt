package com.finwin.doorstep.chosenvolks.home.home_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.finwin.doorstep.chosenvolks.home.home_activity.HomeAction
import com.finwin.doorstep.chosenvolks.home.home_activity.HomeActivity
import com.finwin.doorstep.chosenvolks.home.transactions.TransactionsFragment


import com.finwin.doorstep.chosenvolks.R
import com.finwin.doorstep.chosenvolks.databinding.FragmentHomeBinding
import com.finwin.doorstep.chosenvolks.home.agent_management.AgentManagementFragment
import com.finwin.doorstep.chosenvolks.home.bc_report.BcReportFragment
import com.finwin.doorstep.chosenvolks.home.enquiry.enquuiry_fragment.EnquiryFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {


    lateinit var viewmodel: HomeFragmentViewmodel
    lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewmodel = ViewModelProvider(this).get(HomeFragmentViewmodel::class.java)
        binding.viewmodel = viewmodel
        HomeActivity.activityHomeBinding.appBar.tvHeading.text = "Welcome"
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel.mAction.observe(viewLifecycleOwner, Observer {

            when (it.action) {
                HomeAction.CLICK_TRANSACTION -> {
                    val myFragment: Fragment = TransactionsFragment()
                    activity?.getSupportFragmentManager()?.beginTransaction()?.replace(
                        R.id.frame_layout,
                        myFragment
                    )?.addToBackStack(null)?.commit()
                }
                HomeAction.CLICK_ENQUIRY -> {
                    val myFragment: Fragment =
                        EnquiryFragment()
                    activity?.getSupportFragmentManager()?.beginTransaction()?.replace(
                        R.id.frame_layout,
                        myFragment
                    )?.addToBackStack(null)?.commit()
                }
                HomeAction.CLICK_BC_REPORT -> {
                    val myFragment: Fragment =
                        BcReportFragment()
                    activity?.getSupportFragmentManager()?.beginTransaction()?.replace(
                        R.id.frame_layout,
                        myFragment
                    )?.addToBackStack(null)?.commit()
                }
                HomeAction.CLICK_AGENT -> {
                    val myFragment: Fragment =
                        AgentManagementFragment()
                    activity?.getSupportFragmentManager()?.beginTransaction()?.replace(
                        R.id.frame_layout,
                        myFragment
                    )?.addToBackStack(null)?.commit()
                }
            }

        })
    }



    override fun onStop() {
        super.onStop()
        viewmodel.mAction.value = HomeAction(HomeAction.DEFAULT)
    }
}