package com.androidapps.kotlincoroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

/*Dispatchers and threads:
* Coroutine uses threads for its execution
* The coroutine dispatchers determines the thread for coroutine execution
* The coroutine dispatchers can give the coroutine execution to specific thread or dispatch the work to a thread pool or let it run unConfined
* All coroutine builders like launch and async accept an optional CoroutineContext.
* The coroutine context includes a coroutine dispatcher.
*
* When launch { ... } is used without parameters, it inherits the context (and thus dispatcher) from the CoroutineScope it is being launched from.
* In this case, it inherits the context of the main runBlocking coroutine which runs in the main thread.

*Dispatchers.Unconfined is a special dispatcher that also appears to run in the main thread,
* but it is, in fact, a different mechanism that is explained later.

*The default dispatcher is used when no other dispatcher is explicitly specified in the scope.
* It is represented by Dispatchers.Default and uses a shared background pool of threads.

newSingleThreadContext creates a thread for the coroutine to run. A dedicated thread is a very expensive resource.
* In a real application it must be either released, when no longer needed, using the close function, or stored in a top-level variable and reused throughout the application.  */


/*fun main() = runBlocking<Unit> {
    launch { // context of the parent, main runBlocking coroutine
        println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
        println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Default) { // will get dispatched to DefaultDispatcher
        println("Default               : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(newSingleThreadContext("MyOwnThread")) { // will get its own new thread
        println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
    }
}*/

/*Unconfined            : I'm working in thread main
Default               : I'm working in thread DefaultDispatcher-worker-1
main runBlocking      : I'm working in thread main
newSingleThreadContext: I'm working in thread MyOwnThread*/


/*Unconfined vs confined dispatcher:
* The Dispatchers.Unconfined coroutine dispatcher starts a coroutine in the caller thread, but only until the first suspension point.
* After suspension it resumes the coroutine in the thread that is fully determined by the suspending function that was invoked.
*  The unconfined dispatcher is appropriate for coroutines which neither consume CPU time nor update any shared data (like UI) confined to a specific thread.
*
* On the other side, the dispatcher is inherited from the outer CoroutineScope by default.
* The default dispatcher for the runBlocking coroutine, in particular, is confined to the invoker thread,
* so inheriting it has the effect of confining execution to this thread with predictable FIFO scheduling.*/

/*fun main() = runBlocking<Unit> {
    launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
        println("Unconfined      : I'm working in thread ${Thread.currentThread().name}")
        delay(500)
        println("Unconfined      : After delay in thread ${Thread.currentThread().name}")
    }
    launch { // context of the parent, main runBlocking coroutine
        println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
        delay(1000)
        println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
    }
}*/

/*So, the coroutine with the context inherited from runBlocking {...} continues to execute in the main thread,
 while the unconfined one resumes in the default executor thread that the delay function is using.*/

/*Unconfined      : I'm working in thread main
main runBlocking: I'm working in thread main
Unconfined      : After delay in thread kotlinx.coroutines.DefaultExecutor
main runBlocking: After delay in thread main*/


/*Debugging coroutines and threads:
* Coroutines can suspend on one thread and resume on another thread. Even with a single-threaded dispatcher it might be hard to figure out what the coroutine was doing, where
* for that we can use coroutine tab under debug tool or we can debug using logging
* for that we need to configure vm option -Dkotlinx.coroutines.debug
*
* There are three coroutines. The main coroutine (#1) inside runBlocking and two coroutines computing the deferred values a (#2) and b (#3).
* They are all executing in the context of runBlocking and are confined to the main thread.
* The log function prints the name of the thread in square brackets*/

/*fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun main() = runBlocking<Unit> {
    val a = async {
        log("I'm computing a piece of the answer")
        6
    }
    val b = async {
        log("I'm computing another piece of the answer")
        7
    }
    log("The answer is ${a.await() * b.await()}")
}*/

/*[main @coroutine#2] I'm computing a piece of the answer
[main @coroutine#3] I'm computing another piece of the answer
[main @coroutine#1] The answer is 42*/


/*Jumping between threads:
* using the withContext function to change the context of a coroutine while still staying in the same coroutine*/
/*fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun main() {
    newSingleThreadContext("Ctx1").use { ctx1 ->
        newSingleThreadContext("Ctx2").use { ctx2 ->
            runBlocking(ctx1) {
                log("Started in ctx1")
                withContext(ctx2) {
                    log("Working in ctx2")
                }
                log("Back to ctx1")
            }
        }
    }
}*/
/*[Ctx1 @coroutine#1] Started in ctx1
[Ctx2 @coroutine#1] Working in ctx2
[Ctx1 @coroutine#1] Back to ctx1*/

/*Job in the context:
The coroutine's Job is part of its context, and can be retrieved from it using the coroutineContext[Job] expression:*/

/*fun main() = runBlocking<Unit> {
    println("My job is ${coroutineContext[Job]}")
}*/

/*My job is "coroutine#1":BlockingCoroutine{Active}@6d311334

Note that isActive in CoroutineScope is just a convenient shortcut for coroutineContext[Job]?.isActive == true.*/


/*Children of a coroutine:
* When a coroutine is launched in the CoroutineScope of another coroutine,
* it inherits its context via CoroutineScope.coroutineContext and the Job of the new coroutine becomes a child of the parent coroutine's job.
*  When the parent coroutine is cancelled, all its children are recursively cancelled, too.*/
/*

fun main() = runBlocking<Unit> {
    // launch a coroutine to process some kind of incoming request
    val request = launch {
        // it spawns two other jobs
        launch(Job()) {
            println("job1: I run in my own Job and execute independently!")
            delay(1000)
            println("job1: I am not affected by cancellation of the request")
        }
        // and the other inherits the parent context
        launch {
            delay(100)
            println("job2: I am a child of the request coroutine")
            delay(1000)
            println("job2: I will not execute this line if my parent request is cancelled")
        }
    }
    delay(500)
    request.cancel() // cancel processing of the request
    delay(1000) // delay a second to see what happens
    println("main: Who has survived request cancellation?")
}*/

/*job1: I run in my own Job and execute independently!
job2: I am a child of the request coroutine
job1: I am not affected by cancellation of the request
main: Who has survived request cancellation?*/


/*Parental responsibilities:
A parent coroutine always waits for completion of all its children.
A parent does not have to explicitly track all the children it launches,
and it does not have to use Job.join to wait for them at the end:*/


/*fun main() = runBlocking<Unit> {
    // launch a coroutine to process some kind of incoming request
    val request = launch {
        repeat(3) { i -> // launch a few children jobs
            launch  {
                delay((i + 1) * 200L) // variable delay 200ms, 400ms, 600ms
                println("Coroutine $i is done")
            }
        }
        println("request: I'm done and I don't explicitly join my children that are still active")
    }
    request.join() // wait for completion of the request, including all its children
    println("Now processing of the request is complete")
}*/

/*request: I'm done and I don't explicitly join my children that are still active
Coroutine 0 is done
Coroutine 1 is done
Coroutine 2 is done
Now processing of the request is complete*/


/*join() example:*/

/*fun main() = runBlocking<Unit> {
    println("Started..")
    val firstWord = launch {
        delay(2000)
        print("Hello ")
    }
    firstWord.join()  //parent coroutine waits for its child execution to complete
    println("world!")  //if i don't call join() output will be world! Hello
}*/

/*Started..
Hello world!*/

/*Naming coroutines for debugging:*/

/*fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")*/

/*fun main() = runBlocking(CoroutineName("main")) {
    log("Started main coroutine")
    // run two background value computations
    val v1 = async(CoroutineName("v1coroutine")) {
        delay(500)
        log("Computing v1")
        252
    }
    val v2 = async(CoroutineName("v2coroutine")) {
        delay(1000)
        log("Computing v2")
        6
    }
    log("The answer for v1 / v2 = ${v1.await() / v2.await()}")
}*/

/*[main @main#1] Started main coroutine
[main @v1coroutine#2] Computing v1
[main @v2coroutine#3] Computing v2
[main @main#1] The answer for v1 / v2 = 42*/

/*Combining context elements:*/

/*
fun main() = runBlocking<Unit> {
    launch(Dispatchers.Default + CoroutineName("test")) {
        println("I'm working in thread ${Thread.currentThread().name}")
    }
}*/


/*Coroutine scope:
* A CoroutineScope instance can be created by the CoroutineScope() or MainScope() factory functions
* For example, we are writing an Android application and launch various coroutines in the context of an Android activity
* to perform asynchronous operations to fetch and update data, do animations, etc.
* All of these coroutines must be cancelled when the activity is destroyed to avoid memory leaks
*
* if we don't have coroutine scope ,if we launched 100 coroutine ,manually we need to cancel all 100 coroutine when onDestroy using job.cancel().
* but using coroutine scope all coroutine  will be cancelled at once  */

/*class Activity {
    private val mainScope = CoroutineScope(Dispatchers.Default) // use Default for test purposes

    fun destroy() {
        mainScope.cancel()
    }

    fun doSomething() {
        // launch ten coroutines for a demo, each working for a different time
        repeat(10) { i ->
            mainScope.launch {
                delay((i + 1) * 200L) // variable delay 200ms, 400ms, ... etc
                println("Coroutine $i is done")
            }
        }
    }
} // class Activity ends

fun main() = runBlocking<Unit> {
    val activity = Activity()
    activity.doSomething() // run test function
    println("Launched coroutines")
    delay(500L) // delay for half a second
    println("Destroying activity!")
    activity.destroy() // cancels all coroutines
    delay(1000) // visually confirm that they don't work
}*/

/*Coroutine 0 is done
Coroutine 1 is done
Destroying activity!
Coroutine 3 is done
Coroutine 4 is done*/


/*Asynchronous flow:
* A suspending function asynchronously returns a single value.
* if wants to return multiple value (list) we can use kotlin flow*/


//regular function with list

/*fun simple(): List<Int> = listOf(1, 2, 3)

fun main() {
    simple().forEach { value -> println(value) }
}*/

/*If we are computing the numbers with some CPU-consuming blocking code (each computation taking 1sec),
then we can represent the numbers using a Sequence
This code outputs the same numbers, but it waits 1sec before printing each one.
this computation blocks the main thread .to avoid we can make function suspendable using suspend keyword
now the suspend function can perform its work without blocking and return the result as a list*/

/*fun simple(): Sequence<Int> = sequence { // sequence builder  ,Builds a Sequence lazily yielding values one by one.
    for (i in 1..3) {
        Thread.sleep(1000) // pretend we are computing it
        yield(i) // yield next value
    }
}

fun main() {
    simple().forEach { value -> println(value) }
}*/

/*suspend function:
* Suspend function 'simple' should be called only from a coroutine or another suspend function
* Now suspending function won't block invoking thread(main) when it gets suspended .So user can interact with UI when computing long running process
* This code prints the numbers after waiting for a second.*/

/*
suspend fun simple(): List<Int> {
    delay(1000) // pretend we are doing something asynchronous here ,delay is also suspending function
    return listOf(1, 2, 3)
}

fun main() = runBlocking<Unit> {
    simple().forEach { value -> println(value) }
}
*/


/*Flow:
Using the List<Int> result type, means we can only return all the values at once.
 To represent the stream of values that are being asynchronously computed,
 we can use a Flow<Int> type just like we would use the Sequence<Int> type for synchronously computed values:

 Flow<>:A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
 flow{} Creates a flow from the given suspendable block.

 A builder function for Flow type is called flow.

Code inside the flow { ... } builder block can suspend.

The simple function is no longer marked with suspend modifier.

Values are emitted from the flow using emit function.

Values are collected from the flow using collect function.

We can replace delay with Thread.sleep in the body of simple's flow { ... } and see that the main thread is blocked in this case.
*/

/*fun simple(): Flow<Int> = flow { // flow builder
    for (i in 1..3) {
        delay(100) // pretend we are doing something useful here (won't block main thread)
        emit(i) // emit next value  (no need to add each element  to list to return at once )
    }
}


fun main() = runBlocking<Unit> {
    // Launch a concurrent coroutine to check if the main thread is blocked
    launch {
        for (k in 1..3) {
            println("I'm not blocked $k")
            delay(100)
        }

    }
    // Collect the flow.if we call collect when flow is computing value to emit ,collect will be suspended until next available value
    simple().collect { value -> println(value) }
}*/


/*I'm not blocked 1
1
I'm not blocked 2
2
I'm not blocked 3
3*/


/*Flows are cold:
Flows are cold streams similar to sequences — the code inside a flow builder does not run until the flow is collected.
 This becomes clear in the following example:

 This is a key reason the simple function (which returns a flow) is not marked with suspend modifier.
 By itself, simple() call returns quickly and does not wait for anything.
 The flow starts every time it is collected, that is why we see "Flow started" when we call collect again.*/


/*fun simple(): Flow<Int> = flow {
    println("Flow started")
    for (i in 1..3) {
        delay(100)
        emit(i)
    }
}

fun main() = runBlocking<Unit> {
    println("Calling simple function...")
    val flow = simple()
    println("Calling collect...")
    flow.collect { value -> println(value) }
    println("Calling collect again...")
    flow.collect { value -> println(value) }
}*/

/*Calling simple function...
Calling collect...
Flow started
1
2
3
Calling collect again...
Flow started
1
2
3*/


/*Flow cancellation basics:*/

/*fun simple(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100)
        println("Emitting $i")
        emit(i)
    }
}

fun main() = runBlocking<Unit> {
    withTimeoutOrNull(250) { // Timeout after 250ms
        simple().collect { value -> println(value) }
    }
    println("Done")
}*/

/*Emitting 1
1
Emitting 2
2
Done*/


/*Flow builders:
* flowOf builder that defines a flow emitting a fixed set of values.
* Various collections and sequences can be converted to flows using .asFlow() extension functions.*/

/*fun main() = runBlocking<Unit> {
    // Convert an integer range to a flow
    (1..3).asFlow().collect { value -> println(value) }
    flowOf(1,4,6).collect { value -> println(value)  }
}*/


/*Intermediate flow operators:
* flow can be transformed with operator
* Intermediate operators(map) are applied to an upstream flow and return a downstream flow
*  These operators are cold, just like flows are
* A call to such an operator is not a suspending function itself.
*  It works quickly, returning the definition of a new transformed flow
* The basic operators have familiar names like map and filter.
* The important difference to sequences is that blocks of code inside these operators can call suspending functions.
*
* For example, a flow of incoming requests can be mapped to the results with the map operator,
*  even when performing a request is a long-running operation that is implemented by a suspending function:*/

/*suspend fun performRequest(request: Int): String {
    delay(1000) // imitate long-running asynchronous work
    return "response $request"
}

fun main() = runBlocking<Unit> {
    (1..3).asFlow() // a flow of requests
        .map { request -> performRequest(request) }
        .collect { response -> println(response) }
}*/

/*response 1
response 2
response 3*/


/*Transform operator:
Among the flow transformation operators, the most general one is called transform.
It can be used to imitate simple transformations like map and filter, as well as implement more complex transformations.
 Using the transform operator, we can emit arbitrary values an arbitrary number of times.

For example, using transform we can emit a string before performing a long-running asynchronous request and follow it with a response:*/

/*
suspend fun performRequest(request: Int): String {
    delay(1000) // imitate long-running asynchronous work
    return "response $request"
}

fun main() = runBlocking<Unit> {
    (1..3).asFlow() // a flow of requests
        .transform { request ->
            emit("Making request $request")
            emit(performRequest(request))
           // emit("got response $request")
        }
        .collect { response -> println(response) }
}*/

/*Making request 1
response 1
Making request 2
response 2
Making request 3
response 3*/


/*Size-limiting operators:
Size-limiting intermediate operators like take cancel the execution of the flow when the corresponding limit is reached.
Cancellation in coroutines is always performed by throwing an exception,
so that all the resource-management functions (like try { ... } finally { ... } blocks) operate normally in case of cancellation:
The output of this code clearly shows that the execution of the flow { ... } body in the numbers() function stopped after emitting the second number:*/


/*fun numbers(): Flow<Int> = flow {
    try {
        emit(1)
        emit(2)
        println("This line will not execute")
        emit(3)
    } finally {
        println("Finally in numbers")
    }
}

fun main() = runBlocking<Unit> {
    numbers()
        .take(2) // take only the first two
        .collect { value -> println(value) }
}*/


/*Terminal flow operators:
Terminal operators on flows are suspending functions that start a collection of the flow. The collect operator is the most basic one, but there are other terminal operators, which can make it easier:

Conversion to various collections like toList and toSet.

Operators to get the first value and to ensure that a flow emits a single value.

Reducing a flow to a value with reduce and fold.*/

/*fun main() = runBlocking<Unit> {

    val sum = (1..5).asFlow()
        .map { it * it } // squares of numbers from 1 to 5
    .reduce { a, b -> a + b } // sum them (terminal operator)
    //.toList()  //terminal operator
    //.toSet() //terminal operator
    // .first()  //terminal operator
    //.fold()  //terminal operator

    println(sum)
}*/
//55


/*Flows are sequential:
Each individual collection of a flow is performed sequentially unless special operators that operate on multiple flows are used. The collection works directly in the coroutine that calls a terminal operator. No new coroutines are launched by default. Each emitted value is processed by all the intermediate operators from upstream to downstream and is then delivered to the terminal operator after.

See the following example that filters the even integers and maps them to strings:*/


/*fun main() = runBlocking<Unit> {

    (1..5).asFlow()
        .filter {
            println("Filter $it")
            it % 2 == 0
        }
        .map {
            println("Map $it")
            "string $it"
        }.collect {
            println("Collect $it")
        }
}*/

/*Filter 1
Filter 2
Map 2
Collect string 2
Filter 3
Filter 4
Map 4
Collect string 4
Filter 5
*/





fun main() = runBlocking<Unit> {


    /*  //flow emits value in the context of collector which is main thread
      flowContext().collect { value -> log("Collected $value") }

      //flow emits value in the context of collector which is bg thread
      withContext(Dispatchers.Default) {
          flowContext().collect { value -> log("Collected $value") }
      }*/


    // wrongEmissionWithContext().collect { value -> println(value) }

    /* ChangeContextOfFlowUsingFlowOnOperator().collect { value ->
         log("Collected $value")

     }*/


    /* val time = measureTimeMillis {
         buffering().collect { value ->
             delay(300) // pretend we are processing it for 300 ms
             println(value)
         }
     }

     println("Collected in $time ms")*/

    /* it will take around 1200 sec to finish .flow emit value sequentially to collect .
    when flow emits second(after 100ms) value ,collector busy with transforming first collected value(takes 300ms) .
    flow can emit value only collector is available to collect .
    if we use buffer() operator with collect, flow emit value concurrently without waiting for collector to finish its transformation process  of previous collected  value.
 */

    /*It produces the same numbers just faster, as we have effectively created a processing pipeline,
    having to only wait 100 ms for the first number and then spending only 300 ms to process each number.
    This way it takes around 1000 ms to run:*/

    /*  val timeUsingBuffer = measureTimeMillis {
          buffering()
              .buffer() // buffer emissions, don't wait
              .collect { value ->
                  delay(300) // pretend we are processing it for 300 ms
                  println(value)
              }
      }
      println("Collected in $timeUsingBuffer ms")*/


    /*We see that while the first number was still being processed the second, and third were already produced,
    so the second one was conflated and only the most recent (the third one) was delivered to the collector:

1
3
Collected in 758 ms*/

    /* val time = measureTimeMillis {
         conflate()
             .conflate() // conflate emissions, don't process each one
             .collect { value ->
                 delay(300) // pretend we are processing it for 300 ms
                 println(value)
             }
     }
     println("Collected in $time ms")*/


    //zipOperator()
    combineOperator()
}


/*Flow context:
flow { ... } builder runs in the context that is provided by a collector of the corresponding flow
if we call collect() from main thread, flow builder run in the main thread emits the flow on main thread .
if we call collect() from bg thread flow emits value in bg thread */

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun flowContext(): Flow<Int> = flow {
    log("Started simple flow")
    for (i in 1..3) {
        emit(i)
    }
}


/*[main @coroutine#1] Started simple flow
[main @coroutine#1] Collected 1
[main @coroutine#1] Collected 2
[main @coroutine#1] Collected 3
[DefaultDispatcher-worker-1 @coroutine#1] Started simple flow
[DefaultDispatcher-worker-1 @coroutine#1] Collected 1
[DefaultDispatcher-worker-1 @coroutine#1] Collected 2
[DefaultDispatcher-worker-1 @coroutine#1] Collected 3*/


/*wrong emission with context
* sometimes we may like to start our flow in the context of Dispatchers.Default if we have long running CPU consuming code before emit value
* At the same time we may like to collect those value in the context Dispatchers.Main to update the UI
* if we do we will get exception :java.lang.IllegalStateException: Flow invariant is violated:
* because flow { ... } builder has not allowed to emit from a different context*/


fun wrongEmissionWithContext(): Flow<Int> = flow {
    // The WRONG way to change context for CPU-consuming code in flow builder
    withContext(Dispatchers.Default) {
        for (i in 1..3) {
            delay(100)
            //Thread.sleep(100) // pretend we are computing it in CPU-consuming way
            emit(i) // emit next value
        }
    }
}


//if we wants to change flow builder context we can use flowOn() operator

fun ChangeContextOfFlowUsingFlowOnOperator(): Flow<Int> = flow {
    for (i in 1..3) {
        Thread.sleep(100) // pretend we are computing it in CPU-consuming way
        log("Emitting $i")
        emit(i) // emit next value
    }
}.flowOn(Dispatchers.Default) // RIGHT way to change context for CPU-consuming code in flow builder.now flow works in the background thread

/*[DefaultDispatcher-worker-1 @coroutine#2] Emitting 1
[main @coroutine#1] Collected 1
[DefaultDispatcher-worker-1 @coroutine#2] Emitting 2
[main @coroutine#1] Collected 2
[DefaultDispatcher-worker-1 @coroutine#2] Emitting 3
[main @coroutine#1] Collected 3*/

/*Notice how flow { ... } works in the background thread, while collection happens in the main thread:

Another thing to observe here is that the flowOn operator has changed the default sequential nature of the flow.
 Now collection happens in one coroutine ("coroutine#1") and emission happens in another coroutine ("coroutine#2") that is running in another thread concurrently with the collecting coroutine.
The flowOn operator creates another coroutine for an upstream flow when it has to change the CoroutineDispatcher in its context.*/


/*Buffering:
Running different parts of a flow in different coroutines can be helpful from the standpoint of the overall time it takes to collect the flow, especially when long-running asynchronous operations are involved.
 For example, consider a case when the emission by a simple flow is slow, taking 100 ms to produce an element;
 and collector is also slow, taking 300 ms to process an element.
Let's see how long it takes to collect such a flow with three numbers:*/

fun buffering(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100) // pretend we are asynchronously waiting 100 ms
        emit(i) // emit next value
    }
}


/*Conflation:
When a flow represents partial results of the operation or operation status updates,
it may not be necessary to process each value, but instead, only most recent ones.
In this case, the conflate operator can be used to skip intermediate values when a collector is too slow to process them. Building on the previous example:*/

fun conflate(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100) // pretend we are asynchronously waiting 100 ms
        emit(i) // emit next value
    }
}


/*Composing multiple flows:
* There are lots of ways to compose multiple flows.
* Just like the Sequence.zip extension function in the Kotlin standard library,
*  flows have a zip operator that combines the corresponding values of two flows:*/

fun zipOperator() {
    runBlocking {
        val nums = (1..3).asFlow() // numbers 1..3
        val strs = flowOf("one", "two", "three") // strings
        nums.zip(strs) { a, b -> "$a -> $b" } // compose a single string
            .collect { println(it) } // collect and print
    }
}


/*combine operator:
For example, if the numbers in the below example update every 300ms, but strings update every 400 ms,
then zipping them using the zip operator will still produce the same result,
 albeit results that are printed every 400 ms:*/

fun combineOperator() {
    runBlocking {
        val nums = (1..3).asFlow().onEach { delay(300) } // numbers 1..3 every 300 ms
        val strs = flowOf("one", "two", "three").onEach { delay(400) } // strings every 400 ms
        val startTime = System.currentTimeMillis() // remember the start time
        nums.zip(strs) { a, b -> "$a -> $b" } // compose a single string with "zip"
            .collect { value -> // collect and print
                println("$value at ${System.currentTimeMillis() - startTime} ms from start")
            }
    }



    /*We get quite a different output, where a line is printed at each emission from either nums or strs flows:*/

    runBlocking<Unit> {

        val nums = (1..3).asFlow().onEach { delay(300) } // numbers 1..3 every 300 ms
        val strs = flowOf("one", "two", "three").onEach { delay(400) } // strings every 400 ms
        val startTime = System.currentTimeMillis() // remember the start time
        nums.combine(strs) { a, b -> "$a -> $b" } // compose a single string with "combine"
            .collect { value -> // collect and print
                println("$value at ${System.currentTimeMillis() - startTime} ms from start")
            }
    }
}
