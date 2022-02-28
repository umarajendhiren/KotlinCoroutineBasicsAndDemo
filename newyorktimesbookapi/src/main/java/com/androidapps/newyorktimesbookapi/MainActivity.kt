package com.androidapps.newyorktimesbookapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


/*This app shows list of top selling books and it's details and review from New york times book API.
*We will use Retrofit to perform HTTP request to Book Api.it allow us to request the list of top books for given date.
we will get Json response . Json response contains all details about the book like author name ,book name...
After getting author name ,going to use different end point to fetch review for the each book using author name ,book name,isbn
this whole API request process will block the main thread .The UI should freeze for some time and then show the list of the books.
*/
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}