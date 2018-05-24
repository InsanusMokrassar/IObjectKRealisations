# `IObject`KRealisations

## Available versions

* 1.3
* 1.4
* 1.5
* 1.6
* 1.7
    * 1.7.1
* 1.8
    * 1.8.1
    * 1.8.2
* 1.9.0
    * 1.9.1
    * 1.9.2
    * 1.9.3
    * 1.9.4
* 1.10
    * 1.10.1
    * 1.10.2
    * 1.10.3
* 1.11
    * 1.11.1

## Description

This library was developed as realisations library for project [IObjectK](https://github.com/InsanusMokrassar/IObjectK).

Here you can found realisations for

* SimpleIObject from JSON (use `String#toIObject`)
* [PropertiesIObject](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/PropertiesIObject.kt)
* [XMLIObject](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/XMLIObject.kt)
(if be honest, it is just converter xml-to-simpleiobject)

Besides, in this library you can find useful utils for work with `IObject`s such as

* [Common extensions](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/Extensions.kt)
    * Converters from/to `IObject` from
        * String
        * InputStream
    * `GSON` adapters for correct reading JSON objects
* [Utils](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/Utils.kt)
    * Loading `IObject` from resources/files (first of all from resources).

In [Example](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/Example.kt)
you can found examples of using different types of realisations of `IObject`

## Implementation

### Maven

```xml
<dependency>
    <groupId>com.github.insanusmokrassar</groupId>
    <artifactId>IObjectKRealisations</artifactId>
    <version>1.11.1</version>
</dependency>
```

### Gradle

```
compile 'com.github.insanusmokrassar:IObjectKRealisations:1.11.1'
```

#### Gradle (newest version)

```
implementation 'com.github.insanusmokrassar:IObjectKRealisations:1.11.1'
```
