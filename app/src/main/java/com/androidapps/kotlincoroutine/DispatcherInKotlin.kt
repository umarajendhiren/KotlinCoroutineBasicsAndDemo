package com.androidapps.kotlincoroutine

import kotlinx.coroutines.*

/*Dispatcher determines which thread or thread pool the coroutine runs on
* different dispatcher are available depending on the task specificity
*
* Dispatcher.Default for CPU intensive task like data processing,image processing ,network request
* Dispatcher.Main for update  UI related task
* Dispatcher.Io  for network communication or reading or writing files
* Dispatcher.Unconfined ,if i starts new coroutine in Main dispatcher then if i start another coroutine in Unconfined dispatcher  ,then new coroutine launched by Main dispatcher .so the system decides dispatcher for us.
* newSingleThreadContext("MyThread")->it forces creation of thread.it is not recommended   */

fun main(){

    runBlocking {
        launch(Dispatchers.Default) {
            println("Default:${Thread.currentThread().name}")
        }

        launch(Dispatchers.IO) {
            println("IO:${Thread.currentThread().name}")
        }
      /*  launch(Dispatchers.Main) {
            println("Main:${Thread.currentThread().name}")
        }*/
        launch(Dispatchers.Unconfined) {
            println("Unconfine1:${Thread.currentThread().name}")
            delay(300L)
            println("Unconfine2:${Thread.currentThread().name}")
        }

        launch(newSingleThreadContext("MyThread")) {
            println("newSingleThreadContext:${Thread.currentThread().name}")

        }

    }
}