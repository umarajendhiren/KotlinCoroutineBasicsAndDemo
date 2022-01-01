package com.androidapps.kotlincoroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    /*A.launch() call returns a job and the job allow us to manipulate the coroutine lifecycle
    * using job we can access lifecycle variable and methods like cancel() ,join()
    * if job is cancelled ,all its parents and children will be cancelled too*/


    /*here job1 is parent,job2 and job3 is child .
    * when i cancel job1 ,job2 and job3 will be cancelled.*/

    runBlocking {
        val job1 = launch {
            //delay(3000L)
            println("job1 launched")
            val job2 = launch {
                println("job2 launched")
                delay(3000L)
                println("job2 is finishing")

            }
            job2.invokeOnCompletion { println("job2 completed") }

            val job3 = launch {
                println("job3 launched")
                delay(3000L)
                println("job3 is finishing")

            }
            job2.invokeOnCompletion { println("job3 completed") }
        }
        job1.invokeOnCompletion { println("job1 completed") }
        delay(500L)
        println("job will be cancelled")
        job1.cancel()
    }

}