package com.androidapps.kotlincoroutine

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*Channel
two coroutine communicate with each other through the channel
Coroutine send and receive values through the channel*/

fun main() {
    //create and instantiating channel instance of type Int
    val channel = Channel<Int>()

    //this run block  launch the new coroutine and send 5 elements through the channel in background thread
    runBlocking {

        launch {
            for (X in 1..5)
                channel.send(X * X)


            channel.close()
        }


        //using receive method to receive data from channel

        /*for(Y in 1..5){
            println(channel.receive())
        }*/

        //receive data from channel without using receive method
        for (Y in channel) {
            println(Y)
        }
    }


}
