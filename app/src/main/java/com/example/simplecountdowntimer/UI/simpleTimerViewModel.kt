package com.example.simplecountdowntimer.UI

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*

class simpleTimerViewModel(private val app: Application) : AndroidViewModel(app) {
    private val _remainingTime = MutableLiveData<Long>()
    val remainingTime: LiveData<Long>
        get() = _remainingTime

    private var timer: Timer? = null

    init {
        _remainingTime.value = 0L
    }

    fun stopTimer() {
        if (timer!=null) {
            timer?.cancel()
            _remainingTime.postValue(-1L)
        }

    }

        fun startTimer(seconds: Long) {
            timer?.cancel()
            timer = Timer()

            _remainingTime.value = seconds


            timer?.scheduleAtFixedRate(object : TimerTask() {

            override fun run() {
                if (_remainingTime.value!! > 0) {
                    _remainingTime.postValue(_remainingTime.value!! - 1)
                } else {
                    timer?.cancel()
                }
            }
        }, 0, 1000)
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}