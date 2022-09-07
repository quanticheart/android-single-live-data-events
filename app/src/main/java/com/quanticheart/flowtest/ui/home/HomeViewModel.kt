package com.quanticheart.flowtest.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quanticheart.core.*
import kotlinx.coroutines.flow.flow

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply { value = "This is home Fragment" }
    val text: LiveData<String> = _text

    private val _booleanEvent = MutableLiveData<Boolean>().apply { value = false }
    val booleanEvent: LiveData<Boolean> = _booleanEvent

    private val _simpleEvent = MutableSingleEvent<Boolean>()
    val simpleEvent: SingleEvent<Boolean> = _simpleEvent

    private val _screenBackEvent = MutableScreenEvent()
    val screenBackEvent: ScreenEvent = _screenBackEvent

    private val _screenNextEvent = MutableScreenEvent()
    val screenNextEvent: ScreenEvent = _screenNextEvent

    fun numbersFlow() = flow { // runs in Dispatchers.Default
        for (i in 1..50) {
            kotlinx.coroutines.delay(1000)
            emit(i)
        }
    }

    fun sendBooleanEvent() {
        _booleanEvent.value = true
    }

    fun sendSingleEvent() {
        dispatcher {
            _simpleEvent.setValue(true)
        }
    }

    fun sendScreenNextEvent() {
        dispatcher {
            _screenNextEvent.callScreen()
        }
    }

    fun sendScreenBackEvent() {
        dispatcher {
            _screenBackEvent.callScreen()
        }
    }
}