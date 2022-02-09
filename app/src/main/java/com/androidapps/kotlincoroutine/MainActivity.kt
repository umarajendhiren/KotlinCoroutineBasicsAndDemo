package com.androidapps.kotlincoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        CoroutineScope(Dispatchers.IO).launch {
            //this is not parallel decomposition .this is sequential decomposition
            //first getFirstStock() will be invoked will wait for 10 sec then getSecondStock() will be invoked and wauit for another 8 sec
            //for total it will take 18sec
            var total = getFirstStock() + getSecondStock()
            Log.d("tonCreate", "total is $total")


            //parallel decomposition ,will execute all tasks parallel
            var first = async { getFirstStock() }.await()   //will get value after 2 second
            var second = async { getSecondStock() }.await()  //will get result first after 8sec

            //total will take 10 sec
            var totalOfAsync =first + second
            Log.d("tonCreate", "total is $totalOfAsync")


        }
    }

    private suspend fun getFirstStock(): Int {
        delay(10000)//10sec
        Log.d("getFirstStock: ", "first stock returned")
        return 12
    }

    private suspend fun getSecondStock(): Int {
        delay(8000)//8sec
        Log.d("getSecondStock: ", "second stock returned")
        return 18
    }
}