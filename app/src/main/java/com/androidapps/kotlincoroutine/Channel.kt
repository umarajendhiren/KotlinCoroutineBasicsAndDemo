package com.androidapps.kotlincoroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

/*Channel
two coroutine communicate with each other through the channel
Coroutine send and receive values through the channel*/

fun main() {


    // CreateChannelUsingChannelClass()

    // CreateChannelUsingProduce()

    //CreateChannelUsingProduceExtension()

    //CreateChannelUsingPipeline()

    // fanOut()

    //fanIn()



}

fun fanIn() {
    /*multiple coroutine can send value to same  channel */

    runBlocking {
        val channel = Channel<String>()
        launch {
            sendString(
                channel,
                200L,
                "message1"
            )
        }  //this coroutine send value to channel with delay 200ms
        launch {
            sendString(
                channel,
                500L,
                "message2"
            )
        }  //this coroutine send value to same channel with delay 500ms
        repeat(6) {
            println(channel.receive())   //here channel will receive all value sent by different coroutine
        }
        coroutineContext.cancelChildren()
    }
}

suspend fun sendString(channel: SendChannel<String>, time: Long, message: String) {
    while (true) {
        delay(time)
        channel.send(message)
    }
}


/*if multiple coroutine receive work (value) from same channel then that generated work by channel equally  distributed among them
* generateNumberWithDelay()  function create channel then  generate number and send to the channel with delay of 1ms
* launchNewCoroutine(it, producer) launch new coroutine(5 time) and each receives value from channel process that value that will take 1000ms
* all the number sent to the channel distributed among the coroutine to process */



fun fanOut() {
    runBlocking {
        val producer = generateNumberWithDelay()

        repeat(5) { launchNewCoroutine(it, producer) }
        delay(1000L)
        producer.cancel()
    }

}

/*this function create channel and produce some value then that value given to another channel as input*/
fun CreateChannelUsingPipeline() {
    runBlocking {
        val numbers = generateNumber()
        val squares = produceSquaresUsingParameter(numbers)
        for (i in 1..10)
            println(squares.receive())
        println("Done")
        coroutineContext.cancelChildren()
    }


}

fun CreateChannelUsingProduceExtension() {
/*receiving data from channel produced by extension function*/
    runBlocking {
        /*for (Y in produceSquares())
            println(Y)*/

        produceSquares().consumeEach {
            println(it)
        }
    }


}


/*produce allow as to create a channel
Allow data source to create and return to a channel */
fun CreateChannelUsingProduce() {


    runBlocking {

        /*here creating channel using new  coroutineScope.produce and send Some data asynchronously */
        var channel = produce {
            for (X in 1..5)
                send(X * X)
        }

        /*receiving data from channel by blocking thread*/
        for (Y in channel)
            println(Y)
    }

}

fun CreateChannelUsingChannelClass() {
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

/*extension function*/
fun CoroutineScope.produceSquares() = produce {
    for (X in 1..5)
        send(X * X)
}

fun CoroutineScope.produceSquaresUsingParameter(number: ReceiveChannel<Int>) = produce {


    for (receivedNumber in number) {
        send(receivedNumber * receivedNumber)
    }
}

fun CoroutineScope.generateNumber() = produce {
    var number = 1
    /*infinite loop*/
    while (true)
        send(number++)
}

fun CoroutineScope.generateNumberWithDelay() = produce {
    var number = 1
    /*infinite loop*/
    while (true)
        send(number++)
    delay(100L)
}

fun CoroutineScope.launchNewCoroutine(id: Int, channel: ReceiveChannel<Int>) =
    launch {
        for (message in channel)
            println("processor $id received $message")
    }

