package com.androidapps.kotlincoroutine


import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking


/*we can mix two different flow together and can create new flow using zip() and combine()*/

fun main() {
    runBlocking {
//        zip()
        combine()
    }
}

/*combine () ,combine the latest value of one flow with latest value of another flow*/
suspend fun combine() {
    val numbers = (1..5).asFlow().onEach { delay(300L) }
    val values = flowOf("One", "Two", "Three", "Four", "Five")
        .onEach { delay(400L) }
    numbers.combine(values) { a, b ->
        "$a -> $b"
    }
        .collect { println(it) }
}


/*zip ,match corresponding value of two flows
* ex:zip mix  first value of first flow with first value of second flow and allow us to perform some operation */
suspend fun zip() {
    val english = flowOf("One", "Two", "Three")
    val french = flowOf("Un", "Deux", "Troix")
    english.zip(french) { a, b -> "'$a' in French is '$b'" }
        .collect {
            println(it)
        }
}