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

* Add extensions for `GSONBuilder` for implementation correct [IInputObject](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/Extensions.kt)

## 1.9.3

* Remove redundant `keys` from [PropertiesIObject](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/PropertiesIObject.kt)
* Fix constructors work in [PropertiesIObject](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/PropertiesIObject.kt)

## 1.9.4

* Update dependencies
* Fix [IInputString<String, in Any>#toObject](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/Extensions.kt#25)

## 1.10

* Added [XMLIObject](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/XMLIObject.kt) which represent SimpleIObject but must be built from XML.

## 1.10.1

* Added possibility to wrap `text` of [XMLIObject](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/XMLIObject.kt)

## 1.10.2

* Added [load(String)](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/Utils.kt) which return IObject from
file from resources or file system
* Added [String#readIObject](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/Extensions.kt) which return IObject 

## 1.10.3

* Update work with `#toIObject` extension in [Extensions](src/main/kotlin/com/github/insanusmokrassar/IObjectKRealisations/Extensions.kt)

## 1.11

* Update version of IObjectK and optimize some operations

## 1.11.1

* Add operations to convert directly JSON into object and object into JSON.
* Remove `org.json` dependency and `JSONIObject`. Now for creating IObject from string just use `String#readIObject`
or `String#toIObject`

## 1.11.2

* Simplify and fix library built-in `Gson` adapters
