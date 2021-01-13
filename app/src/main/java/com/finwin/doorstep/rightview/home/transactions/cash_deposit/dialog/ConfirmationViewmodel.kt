package com.finwin.doorstep.rightview.home.transactions.cash_deposit.dialog

import androidx.databinding.BaseObservable
import com.finwin.doorstep.rightview.home.transactions.cash_deposit.pojo.Account
import com.finwin.doorstep.rightview.databinding.LayoutCollectionConfirmationBinding

class ConfirmationViewmodel: BaseObservable() {

    fun setConfirmData(binding: LayoutCollectionConfirmationBinding, account: Account, amount: String)
    {
        binding.tvAccountNumber.text=account.data.ACNO
        binding.tvMobile.text=account.data.MOBILE
        binding.tvName.text=account.data.NAME
        binding.tvDepositAmount.text=amount

    }
}