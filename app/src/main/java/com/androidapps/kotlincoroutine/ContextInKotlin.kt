package com.androidapps.kotlincoroutine

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    /*why we need asynchronous programming:
  * if  android smartphone has refresh rate 60Hz, activity  need to refresh screen every 16.666ms(1000ms/60)
  * in iphone ,refresh rate is 120Hz,which means it needs to refresh every 8ms(1000ms/120)
  *  android main thread has a setup to do this regular responsibilities .
  * main thread  has to parse XML,inflate view components ,Draw the screen ,listen to user(click) again and again on every refresh
  * if the execution time exceeds refresh time between two refreshes  then it will show performance error ,freeze the screen ,unpredictable behavior,ANR
  * To avoid these problem we should always implement long running task asynchronously in a separate thread
  * That's why we are using Coroutine to run task in separate thread    */

    /*structured concurrency:
    * is a set of language features and best practices introduced  for kotlin coroutine to avoid leaks and to manage them productively.
    * */
/*coroutines are software component that creates sub routines for cooperative multitasking
* coroutines are considered as a light weight thread
* we can execute many coroutine in a single thread
* A coroutine can switch between threads which means A coroutine suspend from one thread and resume from another thread
* Multithreading done with below methods :
* kotlin coroutine
* Rxjava
* AsyncTask
* Executors
* HandlerThreads
* IntentService
* Coroutine API allow us to write asynchronous code in a sequential manner.it avoids unnecessary boilerplate code comes with callbacks
* and makes our code more readable and more maintainable  */


    /*A Context is a set of Data that relates to the coroutine.
    * All Coroutine have an associated context
    * important element of a context
    * Dispatcher-which thread the coroutine is run on
    * job-handle on the coroutine lifecycle*/


    /*builder:*/
    /*launch is the extension function of coroutine scope use to launch new coroutine
    launch is the coroutine builder
    * four coroutine builders:
    * launch       result->job             scope->coroutineScope      ->launch coroutine that does not have any result
    * async        result->Deferred        scope->coroutineScope      ->return single value with future result
    * produce      result->ReceiveChannel  scope->ProduceScope        ->produce stream of element
    * runBlocking  result->T               scope ->CoroutineScope     ->block the thread while the coroutine runs

    launch:launch new coroutine without blocking current thread .
    return a instance of job ,which can be used as a reference to the coroutine
    we can use that job to keep track of coroutine and cancel the job
    we use this builder for coroutines that does not have any result as the return value

    async:launches new coroutine ,without blocking the current thread
    returns an instance of Deferred<T> .
    to get value from deferred object we need to invoke await() to get the value
    we use this builder for coroutine that does not have a result as the return value

    produce: produce builder is for coroutines which produce stream of element
    return an instance of received channel
    the coroutine we create using this thread will block the thread while the coroutine is execution
    returns a result of type T*/

    runBlocking {
        launch(CoroutineName("MyCoroutine")) {

            println("running coroutine name is ${coroutineContext.get(CoroutineName.Key)}")
        }
    }
}