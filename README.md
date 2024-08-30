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

