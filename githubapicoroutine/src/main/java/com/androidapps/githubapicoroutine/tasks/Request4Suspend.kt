package tasks

import contributors.*

suspend fun loadContributorsSuspend(service: GitHubService, req: RequestData): List<User> {
    val repos = service
        .getOrgRepos(req.org)
        .also { logRepos(req, it) }
        .bodyList()

    return repos.flatMap { repo ->
        service.getRepoContributors(req.org, repo.name)  //sends request ( for first repository) and will wait for contributors list after that sends request for second repository contributors .all request happen sequentially
            .also { logUsers(repo, it) }
            .bodyList()
    }.aggregate()
}