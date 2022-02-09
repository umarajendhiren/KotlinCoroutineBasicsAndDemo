package com.androidapps.kotlincoroutine

import kotlinx.coroutines.*

/*Dispatcher determines which thread or thread pool the coroutine runs on
* different dispatcher are available depending on the task specificity
*
* Dispatcher.Default for CPU intensive task like sorting a large list, data processing,image processing ,network request
*
* Dispatcher.Main for update  UI related task
* Dispatcher.Main will run coroutine on main thread
* if i create 10 coroutine in the context of Dispatcher.Main, all the coroutine will run on main thread
* we only use this dispatcher for light weight task like call to UI function ,call to suspend fun,to get updates from  live data (post value)
* recommended way to launch coroutine is ,always starting coroutine  from main thread and later switch to background thread
*
* Dispatcher.Io  for network communication or reading or writing files 0r communicate with local database
* in this coroutine will run on background thread from shared pool of on-demand created threads
*
* Dispatcher.Unconfined ,if i starts new coroutine in Main dispatcher then if i start another coroutine in Unconfined dispatcher  ,then new coroutine launched by Main dispatcher .so the system decides dispatcher for us.
* Coroutine will run in the current thread ,but if it is suspended and resumed it will run on  suspending function's thread
* this one is not recommended.we only use Dispatcher.Main and Dispatcher.IO
*
* we can also create our own dispatcher like retrofit, ROOM have been using their own custom dispatcher to execute operation in separate thread
*
* newSingleThreadContext("MyThread")->it forces creation of thread.it is not recommended   */

fun main() {

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