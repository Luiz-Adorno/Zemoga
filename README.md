# Zemoga app

An android app that will display a list of post. For that, the [jsonplaceholder](https://jsonplaceholder.typicode.com/) API will be used. All information (user, comments, and posts) is saved in the local database to be later fetched by the app in the local database and displayed on the screen, providing an offline solution.

# Screenshots

- Main Screen

The Main Screen consists of display a list of post title with dynamic height, and have 4 action mechanism, "Favorite and Unfavorite posts", "Delete unfavorites posts", "Reload data from API" and "Refresh list to sort the elements". 
Each layout initialization, a check function it's called to verify if already exists data saved in the local database (Room), if not, the app will fetch data from API and save in the local database. If exists, the app will get the data from database and display in the screen.


<img src="https://user-images.githubusercontent.com/41413741/229391570-47eb1505-87aa-4eee-857b-9d17321e0b9f.png" width="250" />


- Post Detail Screen
 
 This screen will appear when the user selects a post from the main screen. The post details screen contain 1 Action, "Delete post", and 3 information: The post title and description, the author information and the list of comment.
 
 <img src="https://user-images.githubusercontent.com/41413741/229385842-a4143587-540e-439b-8268-33c233240778.png" width="250" />

 
---
# Tech Stack

- [Clean Architecture](https://medium.com/android-dev-hacks/detailed-guide-on-android-clean-architecture-9eab262a9011) - Separation of code in different modules or sections with specific responsibilities making it easier for maintenance and further modification.
- [Jetpack Libraries](https://developer.android.com/jetpack/androidx/explorer?gclid=Cj0KCQjw0PWRBhDKARIsAPKHFGg1spKQZuAwQdZ1kzALkPlrRRJjWErjAqqvtRWRyduAAoosC_mTZzUaApnyEALw_wcB&gclsrc=aw.ds&case=all&hl=pt-br)
    - [Hilt](https://dagger.dev/hilt/) - Hilt provides a standard way to incorporate Dagger dependency injection into an Android application.
    - [Room](https://developer.android.com/jetpack/androidx/releases/room) - The Room persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
    - [Android KTX](https://developer.android.com/kotlin/ktx.html) - Provide concise, idiomatic Kotlin to Jetpack and Android platform APIs.
    - [AndroidX](https://developer.android.com/jetpack/androidx) - Major improvement to the original Android [Support Library](https://developer.android.com/topic/libraries/support-library/index), which is no longer maintained.
    - [MVVM](https://developer.android.com/topic/libraries/architecture/viewmodel?gclid=CjwKCAjw-rOaBhA9EiwAUkLV4uTWtmhSLWBc9oaYTl_gJJsgJiF-w2indn-p5PnLtnXKs-9elvGQlxoC1jkQAvD_BwE&gclsrc=aw.ds) - ViewModel is a class that is responsible for preparing and managing the data for an Activity or a Fragment . It also handles the communication of the Activity / Fragment with the rest of the application (e.g. calling the business logic classes).
    - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Once view binding is enabled in a module, it generates a binding class for each XML layout file present in that module. An instance of a binding class contains direct references to all views that have an ID in the corresponding layout.
- [Retrofit](https://square.github.io/retrofit/) - Type-safe http client 
and supports coroutines out of the box.
- [GSON](https://github.com/square/gson) - JSON Parser,used to parse 
requests on the data layer for Entities and understands Kotlin non-nullable 
and default parameters.
- [OkHttp Logging Interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - Logs HTTP request and response data.
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines.
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - LiveData follows the observer pattern. LiveData notifies Observer objects when underlying data changes.
- [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) - StateFlow is a state-holder observable flow that emits the current and new state updates to its collectors.
- [Junit/Mockito](https://developer.android.com/training/testing/local-tests) - Unit test with JUnit and mockito
