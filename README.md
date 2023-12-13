# Cat Fact Client

[release-badge]: https://github.com/yonatankarp/cat-fact-client/actions/workflows/release.yml/badge.svg
[release-state]: https://github.com/yonatankarp/cat-fact-client/actions/workflows/release.yml
[gradle-upgrade-badge]: https://github.com/yonatankarp/cat-fact-client/actions/workflows/update-gradle-wrapper.yml/badge.svg
[gradle-upgrade-state]: https://github.com/yonatankarp/cat-fact-client/actions/workflows/update-gradle-wrapper.yml
[ci-badge]: https://github.com/yonatankarp/cat-fact-client/actions/workflows/ci.yml/badge.svg
[ci-state]: https://github.com/yonatankarp/cat-fact-client/actions/workflows/ci.yml


| **Type**     | **Status**                                                                                                                              |
|--------------|-----------------------------------------------------------------------------------------------------------------------------------------|
| CI pipelines | [![Release][release-badge]][release-state] [![Gradle Upgrade][gradle-upgrade-badge]][gradle-upgrade-state] [![CI][ci-badge]][ci-state]  |

A Client library in Kotlin for `https://catfact.ninja/fact`

This is the example client library as described in the article [link to article]().

The library would do a best effort to supply the required number of facts, but
it is not guaranteed that the number of facts returned would be the same as the
number requested, but only limited by it.

## Usage

The library can be used as follows:

```kotlin
val client = CatFactFactory.getInstabce(API, objectMapper)
val facts = client.getFacts(3)
```

Alternatively, you can use the mock version of the library that does not require
any network calls:

```kotlin
val client = CatFactFactory.getInstabce(MOCK, objectMapper)
val facts = client.getFacts(3)
```

## Adding the library to your project

The library is available on [GitHub Packages](https://github.com/yonatankarp/cat-fact-client/packages),
and can be added to your project as follows:


Add to your `build.gradle.kts`:

```kotlin
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/yonatankarp/cat-fact-client")
        credentials {
            username = project.findProperty("gpr.user")?.toString() ?: System.getenv("GITHUB_ACTOR")
            password = project.findProperty("gpr.key")?.toString() ?: System.getenv("GITHUB_TOKEN")
        }
    }
}
```

You can then add the library as a dependency:

```kotlin
dependencies {
    implementation("com.yonatankarp:cat-fact-client:0.2.0")
}
```

## Authors

- **Yonatan Karp-Rudin** - *Initial work* - [yonatankarp](https://github.com/yonatankarp)
