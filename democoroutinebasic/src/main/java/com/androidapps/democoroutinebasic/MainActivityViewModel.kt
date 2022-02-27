package com.androidapps.democoroutinebasic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*


//viewModelScope example
class MainActivityViewModel : ViewModel() {
    var userRepo = UserRepository()
    var userLiveData: MutableLiveData<List<User>> =MutableLiveData()
    /* private val myJob=Job()
     private val myScope= CoroutineScope(Dispatchers.IO)*/

    fun getUserData() {
        // myScope.launch {  }

        viewModelScope.launch {
            var user: List<User>? = null
            //switch to bg thread  to perform long running process

            withContext(Dispatchers.IO) {
                user = userRepo.getUser()

                //Cannot invoke setValue on a background thread
               // userLiveData.value=user
            }
            userLiveData.value=user

        }
    }

    override fun onCleared() {
        super.onCleared()
        // myJob.cancel()

        //if we have 100 job ,we can not cancel manually
        //for that we need to use ViewModelScope ,this ViewModelScope is a CoroutineScope , bounded to viewModel lifecycle
        //this will cancel all job when viewModel cleared
        //if we use viewModelScope ,we no need coroutineScope(myScope) and job(myJob) and no need to override onCleared()
    }

}