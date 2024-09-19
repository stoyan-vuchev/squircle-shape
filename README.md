# Squircle Shape

![Maven Central Version](https://img.shields.io/maven-central/v/io.github.stoyan-vuchev/squircle-shape)
[![API](https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=23)
<a href="https://github.com/stoyan-vuchev/squircle-shape/commits/master"><img src="https://img.shields.io/github/last-commit/stoyan-vuchev/squircle-shape.svg?style=flat&logo=github&logoColor=white" alt="GitHub last commit"></a>
<a href="https://github.com/stoyan-vuchev/squircle-shape/issues"><img src="https://img.shields.io/github/issues-raw/stoyan-vuchev/squircle-shape.svg?style=flat&logo=github&logoColor=white" alt="GitHub issues"></a>
<a href="https://jetc.dev/issues/168.html"><img src="https://img.shields.io/badge/As_Seen_In-jetc.dev_Newsletter_Issue_%23168-blue?logo=Jetpack+Compose&amp;logoColor=white" alt="As Seen In - jetc.dev Newsletter Issue #168"></a>

> A Compose Multiplatform library providing customizable Squircle shapes for UI components.

---

## Table of Contents

* [Why Squircle?](#why-squircle)
* [Requirements](#requirements)
* [Gradle Kotlin DSL Setup (For Multiplatform projects).](#gradle-kotlin-dsl-setup-for-multiplatform-projects)
* [Gradle Kotlin DSL Setup (For Android-only projects).](#gradle-kotlin-dsl-setup-for-android-only-projects)
* [Gradle Groovy Setup (For Android-only projects).](#gradle-groovy-setup-for-android-only-projects)
* [Usage](#usage)
* [License](#license)
* [Contact](#contact)

---

## Why Squircle?

- The squircle is an intermediate shape between a square and a circle, present in digital and real
  products as well.
- While the corners of a rounded square remain at 90-degree angle, the edges of a squircle curve
  smoothly outwards from each corner, therefore creating an optically pleasing shape.
- The human brain perceives rounded corners (especially the smoother ones) easier and faster than sharp corners.

---

## Requirements
<br/>

##### Base requirements (For Multiplatform projects):

- Kotlin version - `2.0.10`
- Compose version - `1.7.0-beta02`

<br/>

---

##### Base requirements (For Android-only projects):

- Kotlin version - `2.0.10`
- Jetpack Compose version - `1.7.2`
- Project `minSdk` version - `23`
- Project `compileSdk` version - `35`

<br/>

---

## Gradle Kotlin DSL Setup (For Multiplatform projects).

##### Step 1

* Add the Squircle Shape dependency in your common module `build.gradle.kts` file.
* Latest version: ![Maven Central Version](https://img.shields.io/maven-central/v/io.github.stoyan-vuchev/squircle-shape)

```kotlin
sourceSets {
    
    val commonMain by getting {
        
        dependencies {
            
            // ...
            
            implementation("io.github.stoyan-vuchev:squircle-shape:<version>")
          
        }
      
    }

    // ...
  
}
```

* Or if you're using a version catalog (e.g. `libs.versions.toml`), declare it in the catalog instead.

```toml
[versions]
squircle-shape = "<version>"

[libraries]
squircle-shape = { group = "io.github.stoyan-vuchev", name = "squircle-shape", version.ref = "squircle-shape" }
```

* Then include the dependency in your shared module `build.gradle.kts` file.

```kotlin
sourceSets {

  val commonMain by getting {

    dependencies {

      // ...

      implementation(libs.squircle.shape)

    }

  }

  // ...

}
```

#### Step 2

* Sync and rebuild the project. 🔄️🔨✅

---

## Gradle Kotlin DSL Setup (For Android-only projects).

##### Step 1

* Add the Squircle Shape dependency in your module `build.gradle.kts` file.
* Latest version: ![Maven Central Version](https://img.shields.io/maven-central/v/io.github.stoyan-vuchev/squircle-shape)

```kotlin
dependencies {
            
    // ...
            
    // Legacy Dependency (Lib. Version < 2.0.0).
    // implementation("com.github.stoyan-vuchev:squircle-shape:<version>")
            
    // New Dependency (Lib. Version >= 2.0.0).
    implementation("io.github.stoyan-vuchev:squircle-shape-android:<version>")
  
}
```

* Or if you're using a version catalog (e.g. `libs.versions.toml`), declare it in the catalog instead.

```toml
[versions]
squircle-shape = "<version>"

[libraries]

# Legacy Dependency (Lib. Version < 2.0.0).
# squircle-shape = { group = "com.github.stoyan-vuchev", name = "squircle-shape", version.ref = "squircle-shape" }

# New Dependency (Lib. Version >= 2.0.0)
squircle-shape = { group = "io.github.stoyan-vuchev", name = "squircle-shape-android", version.ref = "squircle-shape" }
```

* Then include the dependency in your module `build.gradle.kts` file.

```kotlin
dependencies {

  // ...

  implementation(libs.squircle.shape)

}
```

#### Step 2

* Sync and rebuild the project. 🔄️🔨✅

---

## Gradle Groovy Setup (For Android-only projects).

##### Step 1

* Add the Squircle Shape dependency in your module `build.gradle` file.
* Latest version: ![Maven Central Version](https://img.shields.io/maven-central/v/io.github.stoyan-vuchev/squircle-shape)

```groovy
dependencies {
            
    // ...

    // Legacy Dependency (Lib. Version < 2.0.0).
    // implementation "com.github.stoyan-vuchev:squircle-shape:<version>"
            
    // New Dependency (Lib. Version >= 2.0.0).
    implementation "io.github.stoyan-vuchev:squircle-shape-android:<version>"
  
}
```

#### Step 2

* Sync and rebuild the project. 🔄️🔨✅

---

## Usage

* For components, use `SquircleShape()`, which comes in multiple variants for multiple use cases, so
  different variants have different parameters (percent: Float, radius: Dp, etc.).
* However, there is a single common parameter in all variants - `cornerSmoothing: Float`.
* The `cornerSmoothing` as you may have or may have not guessed, is responsible for adjusting the
  smoothness of the corners, the foundation of every squircle shape.


* Take a look at
  the [SquircleShape.kt](/library/src/commonMain/kotlin/sv/lib/squircleshape/SquircleShape.kt)
  and [CornerSmoothing.kt](/library/src/commonMain/kotlin/sv/lib/squircleshape/CornerSmoothing.kt)
  files for more information.


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
  the [SquircleShapePath.kt](/library/src/commonMain/kotlin/sv/lib/squircleshape/SquircleShapePath.kt)
  file.

### Demo app coming soon! 📱💻✨

---

## License

This project is open source and available under the [MIT License](./LICENSE).

```
MIT License

Copyright (c) 2023 Stoyan Vuchev

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

## Contact

Created by [@stoyan-vuchev](https://github.com/stoyan-vuchev/) - feel free to contact me! <br/>
E-mail - [contact@stoyanvuchev.com](mailto://contact@stoyanvuchev.com)