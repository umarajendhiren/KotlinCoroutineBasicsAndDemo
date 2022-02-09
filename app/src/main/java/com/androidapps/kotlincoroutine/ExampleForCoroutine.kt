package com.androidapps.kotlincoroutine

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun main() {
    /*launch two coroutine from different thread and log the name of the running thread */

    example1()

}

fun example1() {

    /*only run within  activity*/
    CoroutineScope(Dispatchers.Main).launch {
        Log.d("Main", "current thread  ${Thread.currentThread().name}")
    }
    CoroutineScope(Dispatchers.IO).launch {
        Log.d("IO", "current thread  ${Thread.currentThread().name}")
    }


}
