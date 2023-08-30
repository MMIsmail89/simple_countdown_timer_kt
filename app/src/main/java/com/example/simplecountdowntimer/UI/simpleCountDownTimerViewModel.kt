package com.example.simplecountdowntimer.UI

import android.app.Application
import android.os.CountDownTimer
import android.os.SystemClock
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.simplecountdowntimer.R
import kotlinx.coroutines.launch

class simpleCountDownTimerViewModel(private val app: Application) : AndroidViewModel(app)  {
    /*
    Absolutely, you can use the AlarmManager class instead of the Timer class,
    for scheduling tasks like the countdown timer.
    The AlarmManager is better suited for such tasks,
     because it can wake up the device if it's in a low-power state,
      and it's designed to handle scheduling operations,
      even when your app is not actively running.
     */


     val second: Long = 1_000L
    //



    private lateinit var timer_CDT: CountDownTimer
    //

    private var _alarmOn = MutableLiveData<Boolean>()

    //

    private val _elapsedTime = MutableLiveData<Long>()
    val elapsedTime: LiveData<Long>
        get() = _elapsedTime
    //


    private var _remainingTime = MutableLiveData<Long>()
    val remainingTime: LiveData<Long>
        get() = _remainingTime



    init {
        _remainingTime.value = 0L
        _elapsedTime.value = 0L
        _alarmOn.value = true
    }

    fun updateRemainingTime(newTime: Long) {
        _remainingTime.postValue(newTime)
    }

    fun stopTimer() {
        if(timer_CDT != null) {
            resetTimer()
            _elapsedTime.value = -1
        }
    }

    fun startTimer(selectedInterval: Long) {

        val triggerTime = SystemClock.elapsedRealtime() + selectedInterval * second

        viewModelScope.launch {
            // val triggerTime = loadTime()
            timer_CDT = object : CountDownTimer(triggerTime, second) {
                override fun onTick(millisUntilFinished: Long) {
                    _elapsedTime.value = triggerTime - SystemClock.elapsedRealtime()
                    if (_elapsedTime.value!! <= 0) {
                        resetTimer()
                    }
                }

                override fun onFinish() {
                    resetTimer()
                }
            }
            timer_CDT.start()
        }


    }

    fun getAppName(): String {
        return app.getString(R.string.app_name)
    }

    override fun onCleared() {
        super.onCleared()
        // stopTimer()

    }


    private fun resetTimer() {
        timer_CDT.cancel()
        _elapsedTime.value = 0
        _alarmOn.value = false
    }

    //





}

