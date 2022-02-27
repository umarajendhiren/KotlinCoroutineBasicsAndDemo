package com.androidapps.democoroutinebasic

import kotlinx.coroutines.delay

class UserRepository {

    suspend fun getUser(): List<User> {
        delay(5000) //simulating delay to mimic server process
        return listOf(
            User("udhf", 1),
            User("dhf", 2),
            User("hf", 3),
            User("f", 4)
        )
    }
}