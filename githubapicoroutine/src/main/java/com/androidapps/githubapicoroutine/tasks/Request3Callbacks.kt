package tasks

import contributors.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.CountDownLatch

/*Using callbacks
The previous solution works, but blocks the thread and therefore freezes the UI.
 A traditional approach to avoiding this is to use callbacks.
 Instead of calling the code that should be invoked after the operation is completed straightaway,
  we extract it into a separate callback, often a lambda, and pass this lambda to the caller in order for it to be called later.

 To make the UI responsive, we can either move the whole computation to a separate thread or switch to Retrofit API and start using callbacks instead of blocking calls.*/


/*Using Retrofit callback API
We've moved the whole loading logic to the background thread,
 but it's still not the best use of resources.
 All the loading requests go sequentially one after another,
 and while waiting for the loading result, the thread(bg) is blocked,
  but it could be occupied with some other tasks.
Specifically, it could start another loading, so that the whole result is received earlier!
Handling the data for each repository should be then divided into two parts: first the loading, then processing the resulting response.
 The second "processing" part should be extracted into a callback. The loading for each repository can then be started before the result for the previous repository is received (and the corresponding callback is called):

 The Retrofit callback API can help to achieve that. We'll use the Call.enqueue function that starts a HTTP request and takes a callback as an argument.
 In this callback, we need to specify what needs to be done after each request.

 For convenience, we use the onResponse extension function declared in the same file.
  It takes a lambda as an argument rather than an object expression.*/
fun loadContributorsCallbacks(
    service: GitHubService,
    req: RequestData,
    updateResults: (List<User>) -> Unit
) {
    service.getOrgReposCall(req.org).onResponse { responseRepos ->
        logRepos(req, responseRepos)
        val repos = responseRepos.bodyList()
        val allUsers = Collections.synchronizedList(mutableListOf<User>())
        val countDownLatch = CountDownLatch(repos.size)
        for (repo in repos) {
            service.getRepoContributorsCall(req.org, repo.name).onResponse { responseUsers ->
                logUsers(repo, responseUsers)
                val users = responseUsers.bodyList()
                allUsers += users
                countDownLatch.countDown()
            }
        }
        countDownLatch.await()
        updateResults(allUsers.aggregate())
    }
}


//extension function
inline fun <T> Call<T>.onResponse(crossinline callback: (Response<T>) -> Unit) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            callback(response)
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            log.error("Call failed", t)
        }
    })
}
