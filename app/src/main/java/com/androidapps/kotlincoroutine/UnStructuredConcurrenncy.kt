package com.androidapps.kotlincoroutine

import kotlinx.coroutines.*

/*unstructured concurrency does not guarantee to complete all the task of the suspending function before it returns.
*if we launched multiple coroutine and we are calculating result from all coroutine result ,we would get unexpected result  */

suspend fun getCountValue(): Int {

    /*if i call this function from another CoroutineScope(Main) to update UI
     it might return 0 because this function returns 0 to main thread before value updated by background thread.
     main thread don't wait and don't care about the background thread
     this is unstructured concurrency .
      if we use async builder with await()  to get this function value ,we would get expected result   */
    var count = 0
    CoroutineScope(Dispatchers.IO).launch {
        delay(1000) //simulating delay
        count = 10
    }
    //launching another coroutine using async coroutine builder
    val asyncDeferredResult = CoroutineScope(Dispatchers.IO).async {
        delay(3000)
        return@async 50 //we can return within async block
    }
    //now count value would be 0 (unexpected) and asyncDeferredResult value would be 50 (expected)
    //expected result is 60 but we would get (0+50=50)
    //this is also unstructured concurrency
    return count + asyncDeferredResult.await()
}