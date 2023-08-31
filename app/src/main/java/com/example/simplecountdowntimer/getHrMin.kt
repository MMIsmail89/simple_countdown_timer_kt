package com.example.simplecountdowntimer

data class getHrMin (
    var hour: Int,
    var minute: Int,
    var AM: Boolean
){

    fun setAM () {
        this.AM = true
    }

    fun reSetAM () {
        this.AM = false
    }



    fun setHr (hr:Int) {
        this.hour = hr
    }

    fun setMin (min:Int) {
        this.minute = min
    }

    fun getHr () : Int {
        return this.hour
    }

    fun getMin () : Int {
        return this.minute
    }

    fun isAM () : Boolean {
        return this.AM
    }
}