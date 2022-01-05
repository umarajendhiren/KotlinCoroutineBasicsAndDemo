package com.androidapps.kotlincoroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis


/*if flow takes long time to emit value ,
buffer is very useful because it allow us to accumulate value and that can be processed later

ex:flow can not able to emit the next value if receiver(collect) takes long time to process the received value.
buffer is the place to accumulate flow value and put them in queue to give receiver for processing.*/




fun main() {

    //this receiver takes 300 ms to process the received value but emitter can emit faster than receiver.this receiver makes flow slow.

    //to receive and process this receiver will take 100+300+100+300+100+300=1200ms

    runBlocking {
        val time = measureTimeMillis {
            generate()
                // .buffer()  //if i add buffer it will take 100+300+300+300=1000ms(while receiver process the first emitted value,buffer store next two received value to process)
                .collect {
                    delay(300L)
                    println(it)
                }
        }
        println("Collected in $time ms")
    }
}

fun generate() = flow {
    for (i in 1..3) {
        delay(100L)  //this flow emits value with 100 ms delay.so it will send 10 value in 1sec
        emit(i)
    }
}