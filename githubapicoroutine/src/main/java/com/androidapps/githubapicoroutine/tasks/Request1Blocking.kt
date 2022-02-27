package tasks

import contributors.*
import retrofit2.Response


/*this function uses GitHubService API to fetch list of  contributors for the given organization
* we get a list of the repositories under the given organization and store it in the repos list.
* Then for each repository, we request the list of contributors and merge all these lists into one final list of contributors
*
* Each getOrgReposCall and getRepoContributorsCall returns an instance of Call class to us (#1). At this point, no request is sent.
* We invoke Call.execute to perform the request (#2). execute is a synchronous call which blocks the underlying thread.
*
* When we get the response, we log the result by calling the specific logRepos() and logUsers() functions (#3).
* If the HTTP response contains an error, this error will be logged here.
*
* Lastly, we need to get the body of the response, which contains the desired data.
* For simplicity in this tutorial, we'll use an empty list as a result in case there is an error, and log the corresponding error (#4).
* To avoid repeating .body() ?: listOf() over and over, we declare an extension function bodyList:
*
* logRepos and logUsers log the received information straight away. While running the code, look at the system output, it should show something like this:
1770 [AWT-EventQueue-0] INFO Contributors - kotlin: loaded 40 repos
2025 [AWT-EventQueue-0] INFO Contributors - kotlin-examples: loaded 23 contributors
2229 [AWT-EventQueue-0] INFO Contributors - kotlin-koans: loaded 45 contributors
*
* This log demonstrates that all the results were logged from the main thread. When we run the code under a BLOCKING request,
* we will find that the window will freeze and won't react to input until the loading is finished.
* All the requests are executed from the same thread as we've called loadContributorsBlocking from, which is the main UI thread (in Swing it's an AWT event dispatching thread).
* This main thread gets blocked, and that explains why the UI is frozen:
*
* After the list of contributors has loaded, the result is updated.
* If we look at how loadContributorsBlocking is called,
* we find that the updateResults goes right after the loadContributorsBlocking call:
*
* updateResults is a function that updates the UI.
*  As a result, it must always be called from the UI thread.*/

fun loadContributorsBlocking(service: GitHubService, req: RequestData): List<User> {
    val repos = service
        .getOrgReposCall(req.org)  //1 returns instance of Call-> Call<List<Repo>>
        .execute() // 2 Executes request and blocks the current thread
        .also {
            logRepos(
                req,
                it
            )
        }   //3 also log the result using  RequestData,Response<List<Repo>>
        .body() ?: listOf()  //4

    return repos.flatMap { repo ->
        service
            .getRepoContributorsCall(req.org, repo.name)   //1
            .execute() //2  Executes request and blocks the current thread
            .also { logUsers(repo, it) }  //3
            .bodyList() //4
    }.aggregate()
}

fun <T> Response<List<T>>.bodyList(): List<T> {
    return body() ?: listOf()
}