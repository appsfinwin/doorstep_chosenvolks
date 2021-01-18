package com.finwin.doorstep.chosenvolks.home.home_activity

class HomeAction {

    companion object{

       public val DEFAULT:Int=-1
        public val CLICK_TRANSACTION:Int=1
        public val CLICK_ENQUIRY:Int=2
        public val CLICK_BC_REPORT:Int=3
        public val CLICK_AGENT:Int=4
        public val CLICK_LOGOUT:Int=5
        public val CLICK_JLG_LOAN:Int=6

    }

    var action: Int? = null

    constructor(action: Int) {
        this.action = action
    }
}