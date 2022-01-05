package com.androidapps.kotlincoroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
//        mapOperator()
//        filterOperator()
        transformOperator()
    }
}

/*we can emit any value at any point using transform operator*/
suspend fun transformOperator() {

    (1..10).asFlow()
        .transform {
            emit("Emitting  value $it")
            emit("two times of $it is ${it * 2}")
        }
        .collect {
            println(it)
        }
}

/*this filter operator only gives even value.
* if the condition is true it will emit value otherwise filter will ignore that value*/
suspend fun filterOperator() {
    (1..10).asFlow()
        .filter {
            it % 2 == 0
        }
        .collect {
            println(it)
        }
}

/*Here map  operator ,takes the input parameter that flow gives and
transform it someway here adding some delay  and emit the value as output*/
suspend fun mapOperator() {
    (1..10).asFlow()
        .map {
            delay(500L)
            "mapping $it"
        }
        .collect {
            println(it)
        }
}