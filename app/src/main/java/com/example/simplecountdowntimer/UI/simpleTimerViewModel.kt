package com.example.simplecountdowntimer.UI

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.simplecountdowntimer.R
import java.util.*

class simpleTimerViewModel(private val app: Application) : AndroidViewModel(app) {

    /*
    However, if your ViewModel ever requires access to resources, system services,
    or application-level data, using the Application context passed to
    the ViewModel's constructor would be appropriate.
    If your specific use case doesn't require the Application context,
     you might not see its usage in the code snippet you provided.
     */
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
    fun getAppName(): String {
        return app.getString(R.string.app_name)
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}