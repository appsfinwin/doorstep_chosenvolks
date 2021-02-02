package com.finwin.doorstep.chosenvolks.home.customer_creation

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finwin.doorstep.chosenvolks.home.customer_creation.action.CustomerCretaionAction

class CustomerCreationViewModel : ViewModel() {

    var mAction: MutableLiveData<CustomerCretaionAction> = MutableLiveData()

    var idProofSideOne64 = ObservableField("")
    var idProofSideTwo64 = ObservableField("")
    var profikePic64 = ObservableField("")
    var signature64 = ObservableField("")

    public  fun clickProfilePhoto(view: View)
    {
        mAction.value= CustomerCretaionAction(CustomerCretaionAction.CLICK_PROFILE_IMAGE)
    }

      public  fun clickSignature(view: View)
    {
        mAction.value=CustomerCretaionAction(CustomerCretaionAction.CLICK_SIGNATURE)
    }

    public  fun clickIdproofSideOne(view: View)
    {
        mAction.value=CustomerCretaionAction(CustomerCretaionAction.CLICK_ID_PROOF_ONE)
    }


    public  fun clickIdproofSideTwo(view: View)
    {
        mAction.value=CustomerCretaionAction(CustomerCretaionAction.CLICK_ID_PROOF_TWO)
    }



}