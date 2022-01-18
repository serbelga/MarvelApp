## Marvel App

App showing a list of Marvel characters and a detail screen giving more information.

### How to launch

Enter public and private api key in `local.properties`

```
public_api_key="..."
private_api_key="..."
```

### Tech stack

* Kotlin & Coroutines
  * Flow & StateFlow
* [Android Jetpack](https://developer.android.com/jetpack)
  * [Android KTX](https://developer.android.com/kotlin/ktx)
  * [Navigation](https://developer.android.com/guide/navigation)
  * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
  * [ViewBinding](https://developer.android.com/topic/libraries/view-binding)
  * [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
* [Dagger Hilt](https://dagger.dev/hilt/)
* [ktlint](https://ktlint.github.io/)
* [Material Components for Android](https://github.com/material-components/material-components-android)
  * [Material Theming](https://material.io/design/material-theming/overview.html)
  * [Dark Theme](https://material.io/design/color/dark-theme.html)
  * [Motion](https://material.io/develop/android/theming/motion)
* [Coil](https://coil-kt.github.io)
* [Retrofit 2](https://square.github.io/retrofit/)
* [Moshi](https://github.com/square/moshi)
* [MockK](https://mockk.io/)

### Packages

- data: Contains DataSources (Remote data source, paging data source, ...), Repositories and API Calls.
- domain: Contains domain models and Use cases.
- ui: Contains UI classes (Activities and Fragments) and ViewModels.