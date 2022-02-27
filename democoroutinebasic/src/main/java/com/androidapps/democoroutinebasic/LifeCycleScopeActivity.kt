package com.androidapps.democoroutinebasic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_life_cycle_scope.*
import kotlinx.coroutines.*


/*
lifecycleScope:
Any coroutine launched within this scope will be  cancelled when the lifecycle is destroyed

we can use this scope with activity or fragment

ex:if we launch 10 courotine within activity using lifecycleScope,all 10  couroutine will be cancelled when activity is destroyed
so we no need to create job and no need to cancel each coroutine manually in onDestroyed().
All those things handle automaticaly by the ktx library */
class LifeCycleScopeActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_cycle_scope)

        /*going to show progress bar after 5 sec and hide after 5 sec */
        lifecycleScope.launch(Dispatchers.IO){
            Log.d("thread name",Thread.currentThread().name)
            delay(5000)
            withContext(Dispatchers.Main) {
                progressBar.visibility = View.VISIBLE
            }

            delay(5000)
            withContext(Dispatchers.Main) {
                progressBar.visibility = View.VISIBLE
            }


            //when we destroy activity this coroutine will be destroyed even it is doing something


        }
        //we can start coroutine after activity created ,started,resumed
        lifecycleScope.launchWhenCreated { }
        lifecycleScope.launchWhenStarted { }
        lifecycleScope.launchWhenResumed { }
    }
}

/*updated*/