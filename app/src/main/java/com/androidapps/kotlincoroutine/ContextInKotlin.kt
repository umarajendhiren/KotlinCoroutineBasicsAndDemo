package com.androidapps.kotlincoroutine

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(){

    /*A Context is a set of Data that relates to the coroutine.
    * All Coroutine have an associated context
    * important element of a context
    * Dispatcher-which thread the coroutine is run on
    * job-handle on the coroutine lifecycle*/

    runBlocking {
        launch( CoroutineName("MyCoroutine")) {

            println("running coroutine name is ${coroutineContext.get(CoroutineName.Key)}")
        }
    }
}