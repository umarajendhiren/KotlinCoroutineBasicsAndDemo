package com.androidapps.kotlincoroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

var functionCount = 0;
fun main() {

    /*in kotlin ,whenever a coroutine is suspended .the current stack from of the function is copied and saved in the memory

when the function resumes after completing its task ,the stack frame is copied back from where it was saved and starts running again

suspending function:
withContext
withTomeOut
withTimeOutOrNulll
join
delay
await
supervisorScope
coroutineScope

if we are going to call this above suspending function in our function  ,we need to mark our function as suspending function using suspend modifier


suspend modifier add label that this function has long running process

A coroutine can invoke suspending and non suspending function

suspending function can be invoked from a coroutine block or from an another suspending function only


*/


    /*How Suspending Functions Work

A suspending function doesn't block a thread,

but only suspends the coroutine itself. (one thread can have more coroutines)

The thread is returned to the pool while the coroutine is waiting,

and when the waiting is done,

the coroutine resumes on a free thread in the pool.*/


    /*suspending function is a function that can be run in a coroutine
    * if we can non suspending function from coroutine we will get compiler error like "make this function as suspending function "*/


    /*local variable functioncount in main thread updated by two background thread by concurrently */
    GlobalScope.launch { completeMessage() }
    GlobalScope.launch { improvedMessage() }
    print("hello")
    Thread.sleep(2000L)
    println("total fuction calls $functionCount")

}

suspend fun completeMessage() {
    delay(500L)
    println("world!")
    functionCount++
}

suspend fun improvedMessage() {
    delay(1000L)
    println("suspend functions are cool!")
    functionCount++
}