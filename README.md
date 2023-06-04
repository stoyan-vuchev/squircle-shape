# Squircle Shape [![](https://jitpack.io/v/stoyan-vuchev/squircle-shape.svg)](https://jitpack.io/#stoyan-vuchev/squircle-shape)

> An Android Jetpack Compose library providing customizable Squircle shapes for UI components.

## Table of Contents

* [Why Squircle?](#why-squircle)
* [Requirements](#requirements)
* [Gradle Kotlin DSL Setup](#gradle-kotlin-dsl-setup)
* [Gradle Groovy Setup](#gradle-groovy-setup)
* [Usage](#usage)
* [License](#license)
* [Contact](#contact)

## Why Squircle?

- The squircle is an intermediate shape between a square and a circle, present in digital and real
  products as well.
- Whereas the corners of a rounded square remain at 90 degree angle, the edges of a squircle curve
  smoothly outwards from each corner, forming an arc, thus creating an optically pleasing shape.
- Currently, there aren't any squircle shapes in Android out of the box. That's why this project was
  created, with the main goal being to provide an easy implementation of a squircle shape for UI
  components built with Jetpack Compose.

## Requirements

- Project minSdk version - `23`
- Jetpack Compose version - `1.4.3`
- Jetpack Compose Compiler version - `1.4.7`
- Kotlin version - `1.8.21`

## Gradle Kotlin DSL Setup

#### Step 1

* Add the Jitpack maven repository in your `settings.gradle.kts` file.

```kotlin
repositories {
    maven(url = "https://jitpack.io")
}
```

#### Step 2

* Add the Squircle Shape dependency in your module `build.gradle.kts` file.
* Latest version: [![](https://jitpack.io/v/stoyan-vuchev/squircle-shape.svg)](https://jitpack.io/#stoyan-vuchev/squircle-shape)

```kotlin
implementation("com.github.stoyan-vuchev:squircle-shape:<version>")
```

* Or if you're using a version catalog (e.g. `libs.versions.toml`), declare it there.

```toml
[versions]
squircle-shape = "<version>"

[libraries]
squircle-shape = { group = "com.github.stoyan-vuchev", name = "squircle-shape", version.ref = "squircle-shape" }
```

* Then include the dependency in your module `build.gradle.kts` file.

```kotlin
implementation(libs.squircle.shape)
```

#### Step 3

* Sync and rebuild the project.

<br/>

## Gradle Groovy Setup

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
* Latest version: [![](https://jitpack.io/v/stoyan-vuchev/squircle-shape.svg)](https://jitpack.io/#stoyan-vuchev/squircle-shape)

```groovy
implementation 'com.github.stoyan-vuchev:squircle-shape:<version>'
```

#### Step 3

* Sync and rebuild the project.

<br/>

## Usage

* For components, use `SquircleShape()`, which comes in multiple variants for multiple use cases, so
  different variants have different parameters (percent: Float, radius: Dp, etc.).
* However, there is a single common parameter in all variants - `cornerSmoothing: Float`.
* The `cornerSmoothing` as you may have or may have not guessed, is responsible for adjusting the
  smoothness of the corners, the foundation of every squircle shape.


* Take a look at the [SquircleShape.kt](/squircle-shape/src/main/kotlin/sv/lib/squircleshape/SquircleShape.kt) and [CornerSmoothing.kt](/squircle-shape/src/main/kotlin/sv/lib/squircleshape/CornerSmoothing.kt) files for more information.


* For a single percent based corner radius, use the variant of `SquircleShape()` with `precent: Int`
  and `cornerSmoothing: Float` parameters.

```kotlin
SquircleShape(
    percent = 100,
    cornerSmoothing = CornerSmoothing.Medium
)
```

* There are also a pixel (Float) and dp (Dp) based variants.


* For a multiple percent based corner radii, use the variant of `SquircleShape()`
  with `topStart: Int`, `topEnd: Int`, `bottomStart: Int`, `bottomEnd: Int`,
  and `cornerSmoothing: Float` parameters.

```kotlin
SquircleShape(
    topStart = 50,
    topEnd = 10,
    bottomStart = 50,
    bottomEnd = 10,
    cornerSmoothing = CornerSmoothing.Medium
)
```

* There are also a pixel (Float) and dp (Dp) based variants.


* Here is an example use of a Button using the default `SquircleShape()`.

```kotlin
Button(
    onClick = { /* Action */ },
    shape = SquircleShape() // Fully rounded squircle shape.
) {
    Text(text = "Full Squircle")
}
```

![Button with Full Squircle shape.](./readme_images/full_squircle.png)

* This is an example of Image clipped to the default `SquircleShape()`.

```kotlin
Image(
    modifier = Modifier
        .size(128.dp)
        .clip(shape = SquircleShape()), // Clipped to a fully rounded squircle shape.
    painter = painterResource(R.drawable.mlbb_novaria),
    contentDescription = "An image of Novaria.",
    contentScale = ContentScale.Crop
)
```

![A portrait image of Novaria from MLBB clipped to a Squircle shape.](./readme_images/mlbb_novaria.png)

* There is also a support for drawing squircle shapes in Canvas - `drawSquircle()`.
* All methods for creating a squircle shape use the `squircleShapePath()`. You can find it inside
  the [SquircleShapePath.kt](/squircle-shape/src/main/kotlin/sv/lib/squircleshape/SquircleShapePath.kt)
  file.

<br/>

## License

This project is open source and available under the [MIT License](./LICENSE).

## Contact

Created by [@stoyan-vuchev](https://github.com/stoyan-vuchev/) - feel free to contact me! <br/>
E-mail - [contact.stoyan.vuchev@gmail.com](mailto://contact.stoyan.vuchev@gmail.com)