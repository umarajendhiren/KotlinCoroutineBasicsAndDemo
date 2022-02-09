package com.androidapps.kotlincoroutine

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.random.Random


/*sometimes we may need result from coroutine.
* async is the way for get some value from coroutine.
* when we need a value ,we call await() (blocking call)
* if the value is available ,it will return immediately
* if the value is not available ,it will pause the thread until it is available*/
fun main() {
    runBlocking {
        val firstDefered = async { getFirstValue() }.await()
        val secondDefered = async { getSecondtValue() }.await()



        println("first value is $firstDefered")
        println("second value is $secondDefered")

        println("Added value is ${firstDefered + secondDefered}")


        /*if i did not use async
        //wait for 1 sec then gets first value
        //after getFirstValue() executed ,getSecondtValue() called
        * //after got all above values, this "added" line will be executed .this is not parallel execution .this is sequential execution.will take more time*/


        /*waiting to get first value
        getting second value
       first value is 64
       second value is 0
       Added value is 64*/

        /*in async method(parallel decomposition)*/
        /* getting second value(first available value )
        waiting to get first value
       first value is 64
       second value is 0
        Added value is 64*/

    }


}

suspend fun getFirstValue(): Int {
    val first = Random.nextInt(100);
    delay(1000L)
    println("waiting to get first value")
    return first
}

suspend fun getSecondtValue(): Int {

    val second = Random.nextInt(100);
    println(" getting second value")
    return second
}


/*if i want to get result from 5 different task and i wand to combine  those result to create new result .it will take more time
* task1->5sec
* task2->15sec
* task3->10sec
* task5->8sec
* so user will get result after 5+15+10+8=38sec.it is more waiting time
* To avoid that ,we can run task parallel and combine the result at the and is called parallel decomposition
* parallel decomposition is not easy .it is more complex code and difficult to read and difficult tom maintain
* but in kotlin coroutine ,parallel decomposition is very easy
* we can create parallel decomposition using async{}*/

