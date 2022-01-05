package com.androidapps.kotlincoroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

fun main() {
    /*properties of flow :
    * flows are cold:flow will transmit value when we call collect()
    * flow can not be cancelled by itself.flow will be cancelled if encompassing coroutine is cancelled*/

    runBlocking {
        val numbersFlow = sendNewNumbers()

        /*since flows are cold,flow will transmit value when we call collect()*/
        println("Flow hasn't started yet")
        println("Starting flow now")
        withTimeoutOrNull(1000L) {
            numbersFlow.collect { println(it) }
        }
    }
}

/*this flow transmit value with 400 ms delay .
* To transmit whole list of value we need 1200ms
* but the coroutine will be cancelled in 1000 ms since  withTimeoutOrNull(1000L).
* so flow can emit only 1,2 in 1000ms*/
fun sendNewNumbers() = flow {
    listOf(1, 2, 3).forEach {
        delay(400L)
        emit(it)
    }

    // fun sendNewNumber()= listOf(1,2,3).asFlow()
}
