package com.androidapps.kotlincoroutine

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(){
/*if i start bare thread which is java thread to print dots for 1 lake times ,all running thread occupy system resources and makes the app unresponsive.
* but if i start coroutine to do that ,it will only take 2-3 seconds to print, because coroutines are light weight.
* which means kotlin coroutine only use small system resources. */
runBlocking {
    repeat(1_00_000){
        launch {
            print(".")
        }
    }
}
}