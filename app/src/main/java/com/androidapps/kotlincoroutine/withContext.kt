package com.androidapps.kotlincoroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/*allow us to easily change context
* allow us to switch between dispatcher */

fun main(){

    runBlocking {
        launch(Dispatchers.Default) {
            println("First context:$coroutineContext")
            withContext(Dispatchers.IO){
                println("second context:$coroutineContext")
            }
            println("third context:$coroutineContext") //first and third will be same
        }
    }
}