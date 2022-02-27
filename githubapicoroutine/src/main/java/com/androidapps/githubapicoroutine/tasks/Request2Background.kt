package tasks

import contributors.GitHubService
import contributors.RequestData
import contributors.User
import kotlin.concurrent.thread


/*Calling loadContributors in the background thread:

Let's first move the whole computation to a different thread.
We use the thread function to start a new thread:

Now that all the loading has been moved to a separate thread,
the main thread is free and can be occupied with different tasks

The signature of the loadContributors function changes,
it takes a updateResults callback as a last argument to call it after all the loading completes:

Now when the loadContributorsBackground is called,
the updateResults call goes in the callback, not immediately afterwards as it did before:*/

fun loadContributorsBackground(service: GitHubService, req: RequestData, updateResults: (List<User>) -> Unit) {
    thread {
        updateResults(loadContributorsBlocking(service, req))
    }
}