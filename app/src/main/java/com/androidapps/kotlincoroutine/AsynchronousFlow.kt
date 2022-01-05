package com.androidapps.kotlincoroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        println("Receiving prime numbers")
        sendPrimes().collect {
            println("Received prime number $it")
        }
        println("Finished receiving numbers")
    }
}

/*i want to send prime numbers in  flow with some delay
* this flow starts when we collect it.*/
fun sendPrimes(): Flow<Int> = flow {
    val primesList = listOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29)
    primesList.forEach {
        delay(it * 100L)
        emit(it)
    }
}