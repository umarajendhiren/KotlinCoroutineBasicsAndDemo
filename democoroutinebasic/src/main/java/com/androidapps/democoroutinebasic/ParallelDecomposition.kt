package com.androidapps.democoroutinebasic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
//parallel decomposition ,will execute all tasks parallel
class ParallelDecomposition : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

btnStockCount.setOnClickListener {
    /*CoroutineScope(Dispatchers.IO).launch {
        //this is not parallel decomposition .this is sequential decomposition
        //first getFirstStock() will be invoked will wait for 10 sec then getSecondStock() will be invoked and wauit for another 8 sec
        //for total it will take 18sec


        //1
        *//*   var total = getFirstStock() + getSecondStock()
               Log.d("stock", "total is $total")*//*

        *//*2022-02-09 11:34:09.934 10512-10537/com.anushka.coroutinesdemo1 D/stock: first stock returned
             2022-02-09 11:34:17.943 10512-10537/com.anushka.coroutinesdemo1 D/stock: second stock returned
             2022-02-09 11:34:17.943 10512-10537/com.anushka.coroutinesdemo1 D/stock: total is 30
             *//*


        //2
        *//*  //parallel decomposition ,will execute all tasks parallel
              var first = async { getFirstStock() }  //will get value after 2 second
              var second = async { getSecondStock() }  //will get result first after 8sec

              //total will take 10 sec
              Log.d( "stock","calculating..")
              var totalOfAsync = first .await()+ second.await()
              Log.d("stock", "total is $totalOfAsync")*//*

        *//*2022-02-09 11:47:00.110 10967-10992/com.anushka.coroutinesdemo1 D/stock: calculating..
              2022-02-09 11:47:08.133 10967-10993/com.anushka.coroutinesdemo1 D/stock: second stock returned
              2022-02-09 11:47:10.142 10967-10993/com.anushka.coroutinesdemo1 D/stock: first stock returned
             2022-02-09 11:47:10.151 10967-10993/com.anushka.coroutinesdemo1 D/stock: total is 30*//*


    }*/

    // 3rd another way
    CoroutineScope(Dispatchers.Main).launch {
        //Log.d("stock", "calculating..")

        val first =
            async(Dispatchers.IO) { getFirstStock() }  //run in separate background thread
        val second =
            async(Dispatchers.IO) { getSecondStock() }  //run in separate background thread

        val total = first.await() + second.await()

        //here we no need to switch to main thread withContext(Dispatchers.Main) because Main CoroutineScope already in main thread
        tvStockCount.text = "tatal stock is $total"
        //Log.d("stock","total stock is $total")
    }
}

        /*2022-02-09 11:59:17.160 11565-11590/com.anushka.coroutinesdemo1 D/stock: second stock returned
         2022-02-09 11:59:19.153 11565-11590/com.anushka.coroutinesdemo1 D/stock: first stock returned*/

    }

    private suspend fun getFirstStock(): Int {
        delay(10000)//10sec
        Log.d("stock", "first stock returned")
        return 12
    }

    private suspend fun getSecondStock(): Int {
        delay(8000)//8sec
        Log.d("stock", "second stock returned")
        return 18
    }
}