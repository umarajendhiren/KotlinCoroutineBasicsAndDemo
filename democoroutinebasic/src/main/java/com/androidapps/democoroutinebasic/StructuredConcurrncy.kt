package com.androidapps.democoroutinebasic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
/*recommended way :if we have more than one coroutine ,we always start with  CoroutineScope(Dispatchers.Main) interface then we need to create child coroutineScope suspending fun */

/*structured concurrency:
* structured concurrency guarantees to complete all the work started by coroutines within the child scope before the return of the suspending function
* we can handle exception easily in structured concurrency
* we can also cancel the coroutine using structured concurrency when we needed */

class StructuredConcurrncy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnGetCount1.setOnClickListener {

            //parent coroutine
            CoroutineScope(Dispatchers.Main).launch {
                tvUpdatedCount1.text = getCountValue().toString()
            }
        }


    }


    suspend fun getCountValue(): Int {
        var count = 0
        lateinit var asyncDeferredResult: Deferred<Int>
        //"CoroutineScope" is the interface
        //this  "coroutineScope" is suspending function ,launch  child coroutine within main coroutine
        //child coroutine under the control of parent coroutine
        //if we don't mention Dispatcher type within launch ,this coroutine  will launch in parent coroutine dispatcher
        //in our cash Main dispatcher is Dispatcher.Main


        //child coroutine
        coroutineScope {
            launch(Dispatchers.IO) {
                delay(1000)
                count = 10
            }
            //launching another coroutine using async coroutine builder within child coroutine
            asyncDeferredResult = CoroutineScope(Dispatchers.IO).async {
                delay(3000)
                return@async 50 //we can return within async block
            }

        }  //child coroutine scope end


        //here the child coroutine  guaranties ,the completion of all task within child coroutine scope  before return the value .
        //  we will get expected result (10+50=60)

        return count + asyncDeferredResult.await()


    }
}