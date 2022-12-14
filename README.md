<h1 align="center">CurrentNewsApp</h1>
This application has been developed in accordance with the Android Architectural Architecture using the Android jetpack libraries.

## App Screenshots
<p align="center">
<img src="/previews/onboarding.png" width="20%"/>
<img src="/previews/main.png" width="20%"/>
<img src="/previews/search.png" width="20%"/>
<img src="/previews/favorite.png" width="20%"/>
<img src="/previews/detail_page.png" width="20%"/>
<img src="/previews/webview.png" width="20%"/>
<img src="/previews/setting.png" width="20%"/>

</p>

## Tech stack & Open-source Libraries
- Minimum SDK level 26
- 100% [Kotlin](https://kotlinlang.org/) based 
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
    -  A single-activity architecture, using the [Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started) to manage fragment operations.
    - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
    - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform an action when lifecycle state changes
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
    - [Repository](https://developer.android.com/topic/architecture/data-layer) - Located in data layer that contains application data and business logic.
- [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
- [Android Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Dependency Injection Library
- [Retrofit](https://square.github.io/retrofit/) A type-safe HTTP client for Android and Java
- [Room](https://developer.android.com/training/data-storage/room) Local and listing of data
- [Gson](https://github.com/google/gson) Gson is a Java library that can be used to convert Java Objects into their JSON representation. It can also be used to convert a JSON string to an equivalent Java object
- [Picasso](https://square.github.io/picasso/) An image loading library for Android backed by Kotlin Coroutines
- Material Design


## Package Structure

    salihkinali.currentnewsapp  # Root Package
    .
    ????????? data                    # Data layer
    ???   ????????? local               # ArticleDao,Converters and NewsDatabase     
    ???   ????????? model               # Article, News and Source Data classes
    ???   ????????? repository          # MainRepository
    ???   ????????? service             # ApiHelper and ApiService
    |
    ????????? di                      # Dependency Injection              
    ???   ????????? database            # Database Module
    ???   ????????? network             # NetworkModule
    ???
    ????????? ui                      # Activity/View(Ui) layer
    ???   ????????? base                # Base Adapter and Base ViewHolder
    ???   ????????? detail              # NewsDetail page and viewmodel
    |   ????????? favorite            # Favorite Page and Room Database
    |   ????????? home                # Home Page 
    |   ????????? onboarding          # Introduction of the application
    |   ????????? search              # Search news part of the application
    |   ????????? setting             # Sharing codes and getting feedback
    |   ????????? webview             # Referral to the original source of the news
    |   
    ????????? util                    # Util Classes / Kotlin extensions / Helper Classes 



