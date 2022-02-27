package tasks

import contributors.*
import kotlinx.coroutines.*

/*When starting each "contributors" request in a new coroutine, all the requests are started asynchronously.
A new request can be sent before the result for the previous one is received:

That brings us approximately the same total loading time as the CALLBACKS version before. But without needing any callbacks.
 What's more, async explicitly emphasizes in the code which parts run concurrently.

 in retrofit enqueue call back , request will be send concurrently but updateUi function won't wait for all the  result
  before retrofit fill the result list ,UpdateUI function will be executed  ,so that is not we expect*/
suspend fun loadContributorsConcurrent(service: GitHubService, req: RequestData): List<User> =
    coroutineScope {
        // we can only start a new coroutine(async) inside a coroutine scope
        //all the coroutines still run on the main UI thread.
        val repos = service
            .getOrgRepos(req.org)
            .also { logRepos(req, it) }
            .bodyList()

        val deferreds: List<Deferred<List<User>>> = repos.map { repo ->
            async {  //starts new coroutine asynchronously
                service.getRepoContributors(req.org, repo.name)
                    .also { logUsers(repo, it) }
                    .bodyList()
            }
        }
        deferreds.awaitAll().flatten().aggregate()
//await() suspend main coroutine until result available in second coroutine

        //when the second coroutine returns result, it wakes up main coroutine which was suspended and was  waiting for the result

        //If there is a list of deferred objects, it is possible to call awaitAll to await the results of all of them
    }