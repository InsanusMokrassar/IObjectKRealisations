# Versions

## 1.3

* [GSON](https://github.com/google/gson) as dependency (v2.8.1)
* Add [toIObject extension](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/Extensions.kt) for all kotlin objects
* Rename and replace 'openFile' as [extension for file with name readIObject](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/Extensions.kt).

## 1.4

* Fix bug which related with getting list when iobject is jsonarray

## 1.5

* Add [toObject extension](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/Extensions.kt) for all IInputObject's.
* Add [toMap extension](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/Extensions.kt) for all IInputObject's.
* Add [toStringMap extension](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/Extensions.kt) for all IInputObject's.

## 1.6

* Add [ObservableCommonIObject](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/ObservableIObject.kt).
Usage example in [Example class](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/Example.kt).

## 1.7

* ObservableCommonIObject replaced and available in [his own repository](https://github.com/InsanusMokrassar/IObjectKObservable).

## 1.7.1

* Update dependencies
* Update IObject to be compatible with v1.5 version

## 1.8

* Update version of kotlin
* Update version of IObject
* Override `val size` in [JSONIObject](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/JSONIObject.kt) 
and [PropertiesIObject](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/PropertiesIObject.kt).

## 1.8.1

* Now you can read IObject from any InputStream
([InputStream.readIObject()](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/Extensions.kt))

## 1.8.2

* Add method [`doUsingDefaultGSON`](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/Extensions.kt) and
rewrite methods which using GSON.

## 1.9.0

* Update kotlin 1.2.0 -> 1.2.20
* Update IObjectK 1.6 -> 1.8

## 1.9.1

* Update IObjectK 1.8 -> 1.9

## 1.9.2

* Add extensions for `GSONBuilder` for implementation correct [IInputObject](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/Extensions.kt#72)
