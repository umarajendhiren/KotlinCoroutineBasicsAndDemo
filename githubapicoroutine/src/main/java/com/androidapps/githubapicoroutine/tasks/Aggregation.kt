package tasks

import contributors.User

/*
TODO: Write aggregation code.

 In the initial list each user is present several times, once for each
 repository he or she contributed to.
 Merge duplications: each user should be present only once in the resulting list
 with the total value of contributions for all the repositories.
 Users should be sorted in a descending order by their contributions.

 The corresponding test can be found in test/tasks/AggregationKtTest.kt.
 You can use 'Navigate | Test' menu action (note the shortcut) to navigate to the test.
*/


/*we group users by their login.
groupBy returns a map from login to all occurrences of the user with this login in different repositories.
Then for each map entry, we count the total number of contributions for each user and create a new instance of User class by the given name and sum of contributions.
At the end, we sort the resulting list in a descending order.
An alternative is to use the function groupingBy instead of groupBy.*/
fun List<User>.aggregate(): List<User> =
    groupBy { it.login }
        .map { (login, group) -> User(login, group.sumOf { it.contributions }) }
        .sortedByDescending { it.contributions }