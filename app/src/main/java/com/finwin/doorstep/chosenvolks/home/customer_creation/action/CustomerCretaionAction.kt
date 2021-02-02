package com.finwin.doorstep.chosenvolks.home.customer_creation.action

class CustomerCretaionAction {
    companion object {

        public const val DEFAULT: Int = -1
        public const val CLICK_PROFILE_IMAGE: Int = 1
        public const val CLICK_SIGNATURE: Int = 2
        public const val CLICK_ID_PROOF_ONE: Int = 3
        public const val CLICK_ID_PROOF_TWO: Int = 4
        public const val CLICK_LOGOUT: Int = 5
        public const val CLICK_JLG_LOAN: Int = 6
        public const val CLICK_CUSTOMER_CREATION: Int = 7

    }
    var action: Int = 0

    constructor(action: Int) {
        this.action = action
    }
}