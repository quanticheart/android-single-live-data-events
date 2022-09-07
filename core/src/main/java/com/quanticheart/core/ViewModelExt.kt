package com.quanticheart.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//
// Created by Jonn Alves on 07/09/22.
//
fun ViewModel.dispatcher(block: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
        block()
    }
}

fun Fragment.lifecycleScope(block: suspend CoroutineScope.() -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
        block()
    }
}
