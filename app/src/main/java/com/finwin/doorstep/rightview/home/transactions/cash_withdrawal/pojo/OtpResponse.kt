package com.finwin.doorstep.rightview.home.transactions.cash_withdrawal.pojo

 data class OtpResponse(val otp: Otp)


data class Otp(
    val error: String,
    val `data`: String,
    val otp_id: String
)