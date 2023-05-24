# Squircle Shape
> An Android Jetpack Compose library providing customizable Squircle shapes for UI components.

## Table of Contents
* [Why Squircle?](#why-squircle)
* [Requirements](#requirements)
* [Setup (Gradle Kotlin DSL)](#setup--gradle-kotlin-dsl-)
* [Setup (Gradle Groovy)](#setup--gradle-groovy-)
* [Usage](#usage)
* [License](#license)
* [Contact](#contact)

## Why Squircle?
- The squircle is an intermediate shape between a square and a circle, present in digital and real products as well.
- Whereas the corners of a rounded square remain at 90 degree angle, the edges of a squircle curve smoothly outwards from each corner, forming an arc, thus creating an optically pleasing shape.
- Currently, there aren't any squircle shapes in Android out of the box. That's why this project was created, with the main goal being to provide an easy implementation of a squircle shape for UI components built with Jetpack Compose.

## Requirements
- Project Min SDK version - `23`
- Jetpack Compose version - `1.4.3`
- Jetpack Compose Compiler version - `1.4.7`
- Kotlin version - `1.8.21`

## Setup (Gradle Kotlin DSL)
#### Step 1
* Add the Jitpack maven repository in your project (root) level `build.gradle.kts` file.

```kotlin
buildscript {

    repositories {
        maven(url = "https://jitpack.io")
    }

}
```

#### Step 2
* Add the Squircle Shape dependency in your module `build.gradle.kts` file.
* [![](https://jitpack.io/v/stoyan-vuchev/squircle-shape.svg)](https://jitpack.io/#stoyan-vuchev/squircle-shape)

```kotlin
implementation("com.github.stoyan-vuchev:squircle-shape:1.0.0")
```

#### Step 3
* Sync and rebuild the project.

<br/>

## Setup (Gradle Groovy)
#### Step 1
* Add the Jitpack maven repository in your project (root) level `build.gradle` file.

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

#### Step 2
* Add the Squircle Shape dependency in your module `build.gradle` file.
* [![](https://jitpack.io/v/stoyan-vuchev/squircle-shape.svg)](https://jitpack.io/#stoyan-vuchev/squircle-shape)

```groovy
implementation 'com.github.stoyan-vuchev:squircle-shape:1.0.0'
```

#### Step 3
* Sync and rebuild the project.

<br/>

## Usage
* Here is an example use of a SquircleShape for a Button component:
```kotlin
Button(
    onClick = { /* Action */ },
    shape = SquircleShape() // Fully rounded squircle
) {
    Text(text = "Full Squircle")
}
```
* You can customize the shape with the `radius` and `cornerSmoothing` parameters.
* There is support for a single or multiple corner radius values, defined as percent (Int), dp (Dp) or pixels (Float).

<br/>

## License
This project is open source and available under the [MIT License](./LICENSE).

## Contact
Created by [@stoyan-vuchev](https://github.com/stoyan-vuchev/) - feel free to contact me! <br/>
E-mail - [contact.stoyan.vuchev@gmail.com](mailto://contact.stoyan.vuchev@gmail.com)