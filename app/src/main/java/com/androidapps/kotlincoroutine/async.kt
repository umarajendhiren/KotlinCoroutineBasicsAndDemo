package com.androidapps.kotlincoroutine

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.random.Random


/*sometimes we may need result from coroutine.
* async is the way for get some value from coroutine.
* when we need a value ,we call await() (blocking call)
* if the value is available ,it will return immediately
* if the value is not available ,it will pause the thread until it is available*/
fun main(){
runBlocking {
    val firstDefered=async { getFirstValue() }.await()
    val secondDefered=async { getSecondtValue() }.await()



    println("first value is $firstDefered")
    println("second value is $secondDefered")

    println("Added value is ${firstDefered+secondDefered}")

}


}
suspend fun getFirstValue():Int{
    val first=Random.nextInt(100);
    delay(1000L)
    println("waiting to get first value")
    return first
}

suspend fun getSecondtValue():Int{
    val second=Random.nextInt(100);
    return second
}