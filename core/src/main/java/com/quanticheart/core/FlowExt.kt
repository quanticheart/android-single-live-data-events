package com.quanticheart.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

//
// Created by Jonn Alves on 22/08/22.
//
interface SingleEventEmitter<T> {
    suspend fun setValue(value: T)
}

interface SingleEvent<T> {
    fun value(): Flow<T>
}

class MutableSingleEvent<T> : SingleEventEmitter<T>, SingleEvent<T> {
    private val channel = Channel<T>(Channel.UNLIMITED)

    override suspend fun setValue(value: T) {
        channel.send(value)
    }

    override fun value(): Flow<T> = channel.receiveAsFlow()
}

fun <T> SingleEvent<T>.observeWithLifeCycle(
    lifeCycle: Lifecycle,
    lifecycleState: Lifecycle.State = Lifecycle.State.CREATED
) = value().flowWithLifecycle(lifeCycle, lifecycleState)

fun <T> SingleEvent<T>.observe(
    lifeCycle: Fragment,
    block: (T) -> Unit
) = lifeCycle.lifecycleScope {
    value().flowWithLifecycle(lifeCycle.lifecycle, Lifecycle.State.CREATED).collect {
        block(it)
    }
}

/**
 * Screen event emitter
 */
interface ScreenEventEmitter {
    suspend fun callScreen()
}

interface ScreenEvent {
    fun value(): Flow<Boolean>
}

class MutableScreenEvent : ScreenEventEmitter, ScreenEvent {
    private val channel = Channel<Boolean>(Channel.UNLIMITED)

    override suspend fun callScreen() {
        channel.send(true)
    }

    override fun value(): Flow<Boolean> = channel.receiveAsFlow()
}

fun ScreenEvent.observe(
    lifeCycle: Fragment,
    block: () -> Unit
) = lifeCycle.lifecycleScope {
    value().flowWithLifecycle(lifeCycle.lifecycle, Lifecycle.State.CREATED).collect {
        block()
    }
}
