package com.androidapps.kotlincoroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

var functionCount=0;
fun main(){
    /*suspending function is a function that can be run in a coroutine
    * if we can non suspending function from coroutine we will get compiler error like "make this function as suspending function "*/


    /*local variable functioncount in main thread updated by two background thread by concurrently */
      GlobalScope.launch { completeMessage() }
      GlobalScope.launch { improvedMessage() }
    print("hello")
    Thread.sleep(2000L)
    println("total fuction calls $functionCount")

}

suspend fun completeMessage(){
    delay(500L)
    println("world!")
    functionCount++
}

suspend fun improvedMessage(){
    delay(1000L)
    println("suspend functions are cool!")
    functionCount++
}