package com.androidapps.democoroutinebasic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private var count = 0

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var liveDataBuilderViewModel: LiveDataBuilderViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //viewModelScope example
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getUserData()
        viewModel.userLiveData.observe(this, Observer { myUsers ->
            myUsers.forEach {
                Log.d("listOfUser: ", it.toString())
            }


        })

        //livaData builder example
        //no need to call any method  like viewModel.getUserData()
        liveDataBuilderViewModel = ViewModelProvider(this).get(LiveDataBuilderViewModel::class.java)
        liveDataBuilderViewModel.liveUseData.observe(this, Observer { myUsers ->
            myUsers.forEach {
                Log.d("listOfUser: ", it.toString())
            }
        })


        /*if i click on this button after click on download user data button ,the app won't increase count because mainThread is busy with downloadUseData() function.
        * Main thread can not do two task at same time .
        * if we want to click and increase the count when downloadUserDta() runs ,we need to run long running process (downloadUserDta()) in separate thread .for that we need to use coroutine.   */
        btnCount.setOnClickListener {
            tvCount.text = count++.toString()
        }

        //not recommended
        /* btnDownloadUserData.setOnClickListener {
             downloadUserData()
         }*/


        //withContext()
        btnDownloadUserData.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                downloadUserData()  //runs this task in background thread .Now i can increase count by click on button  while it downloads data
            }

        }


    }


    private suspend fun downloadUserData() {
        withContext(Dispatchers.Main) {
            for (i in 1..200000) {
                //Log.i("MyTag", "Downloading user $i in ${Thread.currentThread().name}")
                /*if we want to update UI within Coroutine scope we need to switch to main thread.
              * main thread only can update UI .
              * exception if we run in background thread:Only the original thread that created a view hierarchy can touch its views.
              * we need to use withContext() function to switch between thread */


                tvUserMessage.text = "Downloading user $i in ${Thread.currentThread().name}"
                delay(1000)
            }
        }
    }
}
