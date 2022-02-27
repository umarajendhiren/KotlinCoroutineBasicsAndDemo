package com.androidapps.democoroutinebasic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

class LiveDataBuilderViewModel : ViewModel() {

/*if i set value for livedata within method in viewModel ,i need to invoke that method in myActivity before observing that livedata.
so the flow is
need to define live data
need to launch new coroutine within method to run long running task
after coroutine ran ,need to set value for live data
in Activity,Before observing live data need to call the method which updating live data
then only we can see updated value  in our activity

To avoid this much boilerplate code we can use LiveData Builder
which just emits value
we no need to call any method which updates livedata

for that we need to launch coroutine in liveDataScope and we need to call our suspending function from our repo
then we need call emit*/
    var userRepo = UserRepository()
    var liveUseData = liveData(Dispatchers.IO) {
       val listOfUser = userRepo.getUser()
        emit(listOfUser)
    }
}