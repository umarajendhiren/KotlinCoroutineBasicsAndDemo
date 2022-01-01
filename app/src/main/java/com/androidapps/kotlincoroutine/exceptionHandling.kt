package com.androidapps.kotlincoroutine

import kotlinx.coroutines.*

/*exception behavior depends on the coroutine builder
launch

* propagate through the parent child hierarchy
* exception will be thrown immediately and job will fail
* use try catch or exception handler

  async:*/

/*launch
* launch creates job hierarchy .so the exception  instantly propagate through the parent child hierarchy
* exception will be thrown immediately and job will fail
* use try catch or exception handler
* launch generates exception automatically ,that causes all the job to fail in the hierarchy (all child)
* we will get exception, when we try to use that job from coroutine launch
*
* async:
* Exception are deferred until the result is consumed
* if the result is not consumed ,the exception is never thrown
* try-catch in the coroutine or in the await() call
* we will get exception when we call await() in async */

fun main() {
    runBlocking {
        val myHandler = CoroutineExceptionHandler {coroutineContext, throwable ->
            println("Exception handled: ${throwable.localizedMessage}")
        }

        val job = GlobalScope.launch(myHandler) {
            println("Throwing exception from job")
            throw IndexOutOfBoundsException("exception in coroutine")
        }
        job.join()

        val deferred = GlobalScope.async {
            println("Throwing exception from async")
            throw ArithmeticException("exception from async")
        }

        try {
            deferred.await()
        } catch (e: java.lang.ArithmeticException) {
            println("Caught ArithmeticException ${e.localizedMessage}")
        }
    }
}