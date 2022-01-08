package com.androidapps.kotlincoroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

fun main() {

    sharedStateVariableProblem()

    accessSharedStateUsingAtomic()

    incrementCounterUsingThreadContext()

/*this lock system better than access shared state through single thread context but not better that atomic integer*/
    threadSafeUsingMutexLockUnlock()
}

fun sharedStateVariableProblem() {
    //normal Int counter
    runBlocking {
        var counter = 0
        withContext(Dispatchers.Default) {
            massiveRun { counter++ }
        }
        println("non atomic Counter = $counter")
    }
}

fun accessSharedStateUsingAtomic() {
    //atomic counter
    /*it works well for primitive type line integer ,boolean,long and collection.
     * but if we are using custom class ,it is difficult to make that object as atomic.
    * we need to provide implementation for that object .*/

    runBlocking {
        var atomicCounter = AtomicInteger(0)  //atomic integer to avoid shared state pbm
        withContext(Dispatchers.Default) {
            massiveRun { atomicCounter.incrementAndGet() }
        }
        println("atomic counter = $atomicCounter")
    }
}


fun incrementCounterUsingThreadContext() {

    /*thread confinement:
    * All access to shared data is done through(confined  to a) single thread
    * two types of thread confinement:
    * Fine-grained:each individual increment switches context .so much slower.run in synchronously.
    * Coarse-grained:the whole function run on a single thread.no context switching .much faster.
    * but Coarse-grained will not run in synchronously(parallel).will run serially */


    //Fine-grained:
    // every time counter is accessed through this context.will take more time
    val counterContext = newSingleThreadContext("counterContext")

    runBlocking {
        var counter = 0
        withContext(Dispatchers.Default) {
            massiveRun {
                /*here each time we are changing context from default to counterContext to update counter value in synchronously.
                * this method is thread safe because we are using single thread to update counter value.
                * we don't lose any value but changing context will take more time */

                withContext(counterContext) {

                    counter++
                }
            }
        }
        println("thread confined:fine grained= $counter")
    }


    //Coarse-grained:
    //whole function run through the single thread context .so will run faster but not in parallel.

    runBlocking {
        var counter = 0
        withContext(counterContext) {
            massiveRun {
                counter++
            }
        }
        println("thread confined:coarse grained = $counter")
    }


}


fun threadSafeUsingMutexLockUnlock() {
    /*if one thread is going to access shared state variable it will lock ,update and unlock .so the next thread will access that same variable*/
    runBlocking {

        val mutex = Mutex()
        var counter = 0
        withContext(Dispatchers.Default) {
            massiveRun {
                mutex.lock { counter++ }
            }
            println("lock counter = $counter")
        }
    }
}


/*in this this function,100 coroutine increment the counter value 1000 times simultaneously .
if so,we should get 100000 as counter value.but we we will get half of that.
that is the shared state problem.
if multiple coroutine try to access same value at same time some value may be lost.
for this case to avoid this problem we can use atomic integer*/
suspend fun massiveRun(action: suspend () -> Unit) {
    val n = 100
    val k = 1000
    val time = measureTimeMillis {
        coroutineScope {
            repeat(n) {//launching coroutine 100 times
                launch {
                    repeat(k) {  //each coroutine increment the value of counter 1000 times.
                        action()
                    }
                }
            }
        }
    }
    println("Completed ${n * k} actions in $time ms")
}

