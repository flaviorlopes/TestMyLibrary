# TestMyLibraryExample
 
This project was created to show how to use MyLibrary lib.

MyLibrary is a lib to make easy the calls to mobiWeb api services. MyLibrary encapsulates all logic and concerns about api calls and handle with all details about the business logic.

## How to install MyLibrary ##

To install MyLibrary in your project you need to create a reference to jitPack.io repository in the settings.gradle file:

``` kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url 'https://jitpack.io'
        }
    }
}
```

and create the dependency in the build.gradle file (module: app)

``` kotlin
dependencies {
    implementation "com.github.flaviorlopes:myLibrary:1.0.0"
}
```

If you're using catalog reference with libs.versions.toml file:

``` kotlin
dependencies {
    implementation libs.mylibrary
}
```

libs.versions.toml
``` kotlin
[versions]
mylibraryVersion = "1.0.0"

[libraries]
mylibrary = { module = "com.github.flaviorlopes:myLibrary", version.ref = "mylibraryVersion" }
``` 

## Api Calls ##

To take advantage of the myLibrary facilities, your app must use a singleton called "Service". This singleton provides all methods needed to access the api calls.

Example:
``` Kotlin
CoroutineScope(Dispatchers.IO).launch {
   val requestData = SaveStringRequestData(myString = textToSave.value)
   Service.saveString(requestData)
}
```

Because of the good practices to use coroutines to make network calls, all methods of Service must be inside a CoroutineScope and should use the Dispatcher.IO.

Other important information about the code above is the use of a requestData (SaveStringRequestData). To each method that need a requestData you can find all information in the "Api calls details section"

The response will be a Result class of Success or Error and the atributes are success: Boolean, resultMessage: String and errorMessage: String. So you can check the subcalss of Result and define your code according to it.

``` kotlin
    val result = Service.saveString(requestData)

    when(result) {
       is Result.Error -> {
          // your error code goes here
          dialogText.value = result.errorMessage.toString()
       }
       is Result.Success -> {
          // your success code goes here
          dialogText.value = result.resultMessage.toString()
       }
    }
```

## Api calls details ##

Currently the api calls below are available:

<details>
 
 <summary>saveString</summary>

 Used to save a string in our database. As a POST http call, must be send the data that want to be saved in the body section.

 Example:
  ``` kotlin
      val requestData = SaveStringRequestData(myString = textToSave.value)
      Service.saveString(requestData)
  ```

 Request Data
 ``` kotlin
 data class SaveStringRequestData(
    val myString: String
)
 ```

</details>
