package com.finwin.doorstep.rightview.home.enquiry.enquuiry_fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.finwin.doorstep.rightview.R
import com.finwin.doorstep.rightview.databinding.FragmentEnquiryBinding
import com.finwin.doorstep.rightview.home.enquiry.balance_enquiry.BalanceEnquiryFragment
import com.finwin.doorstep.rightview.home.enquiry.mini_statement.MiniStatementFragment
import com.finwin.doorstep.rightview.home.home_activity.HomeActivity


class EnquiryFragment : Fragment() {

    lateinit var binding: FragmentEnquiryBinding
    lateinit var viewmodel: EnquiryViewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_enquiry, container, false)
        viewmodel=ViewModelProvider(this).get(EnquiryViewmodel::class.java)
        binding.viewmodel=viewmodel
        HomeActivity.activityHomeBinding.appBar.tvHeading.text = "ENQUIRY"
        return binding.root

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EnquiryFragment()
                .apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel.mAction.observe(viewLifecycleOwner, Observer {

            when(it.action)
            {
                EnquiryAction.CLICK_ACCOUNT_STATUS->{}
                EnquiryAction.CLICK_MINI_STATEMENT->{
                    val myFragment: Fragment = MiniStatementFragment()
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.add(R.id.frame_layout, myFragment)?.addToBackStack(null)?.commit()
                }
                EnquiryAction.CLICK_BALANCE_ENQUIRY->{
                    val myFragment: Fragment = BalanceEnquiryFragment()
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.add(R.id.frame_layout, myFragment)?.addToBackStack(null)?.commit()
                }
            }
        })
    }
}