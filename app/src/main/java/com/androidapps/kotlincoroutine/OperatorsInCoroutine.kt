package com.androidapps.kotlincoroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        //        mapOperator()
        //        filterOperator()
        //      transformOperator()

        //takeOperator()

        //reduceOperator()

        flowOnOperator()

        //collect operator:it starts the flow and flow emits the value then collect() receives the transmitted value from flow
        //toList() operator:converts flow to list
        //toMap() operator :convert flow to list.but if flow emits duplicate value toMap() operator ignore that value
    }
}

/*which is very useful,if we need the emitted value in background thread and do some process in different thread
* then we can receive that value from background thread into main thread for updating the UI*/
suspend fun flowOnOperator() {
    (1..10).asFlow()
        .flowOn(Dispatchers.IO)  //starts flow in IO thread
        .collect { println(it) }  //receives the emitted value in main thread
}

/*reduce operator ,iterate value  and do some process and store that value in accumulator for the next iteration
* it is terminal flow operator like collect().
* it starts the flow and receives the value.*/

suspend fun reduceOperator() {
    val size = 10
    val factorial = (1..size).asFlow()  //here flow is cold
        .reduce { accumulator, value -> accumulator * value }//here flow start to emit value
    println("factorial of $size is $factorial ")
}

/*use only a number of values and disregard the rest*/
suspend fun takeOperator() {
    (1..10).asFlow()
        .take(2)
        .collect { println(it) }
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

/*filter flow values

ex:i am creating flow from 1 to 10 and wants to emit only even number for that use case i need to go to filter operator.
this filter operator only gives even value.
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

/*map a flow to another flow.
Here map  operator ,takes the input parameter that flow gives and
transform it into someway. here adding some delay  and emit the value as output*/
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