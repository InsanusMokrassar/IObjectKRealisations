package com.github.insanusmokrassar.IObjectKRealisations

import com.github.insanusmokrassar.IObjectK.extensions.toJsonString
import com.github.insanusmokrassar.IObjectK.interfaces.CommonIObject
import com.github.insanusmokrassar.IObjectK.interfaces.IInputObject
import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import com.github.insanusmokrassar.IObjectK.interfaces.IOutputObject
import com.github.insanusmokrassar.IObjectK.realisations.SimpleIObject
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import java.io.InputStream
import java.io.InputStreamReader

private var gson: Gson? = null

fun <K: Any, V: Any> IInputObject<K, V>.toStringMap(): Map<String, String> {
    return mapOf(
        *map {
            it.first.toString() to it.second.toString()
        }.toTypedArray()
    )
}

fun <T> IInputObject<String, in Any>.toObject(targetClass: Class<T>): T {
    return doUsingDefaultGSON {
        it.fromJson(this.toJsonString(), targetClass)
    }
}

fun String.toIObject(): IObject<Any> = readIObject()

fun <K, V: Any> IInputObject<K, V>.toIObject(): IObject<Any> {
    return SimpleIObject().also {
        resultObject ->
        forEach {
            resultObject[it.first.toString()] = it.second
        }
    }
}

fun Any.toIObject(): IObject<Any> {
    return doUsingDefaultGSON {
        JSONIObject(it.toJson(this))
    }
}

@Throws(ReadIObjectException::class)
fun String.readIObject(): IObject<Any> {
    return byteInputStream().readIObject()
}

@Throws(ReadIObjectException::class)
fun InputStream.readIObject(): IObject<Any> {
    try {
        var resultExceptions = mutableListOf<Exception>()
        val streamText = InputStreamReader(this).readText()
        try {
            return JSONIObject(streamText)
        } catch (e: Exception) {
            resultExceptions.add(Exception("Input stream $this can't be read as json: ${e.message}\n${e.stackTrace}", e))
        }
        try {
            return XMLIObject(streamText)
        } catch (e: Exception) {
            resultExceptions.add(Exception("Input stream $this can't be read as xml: ${e.message}\n${e.stackTrace}"))
        }
        try {
            return PropertiesIObject(streamText)
        } catch (e: Exception) {
            resultExceptions.add(Exception("Input stream $this can't be read as properties: ${e.message}\n${e.stackTrace}"))
        }
        throw ReadIObjectException(resultExceptions)
    } catch (e: Exception) {
        throw IllegalArgumentException("For some of reason can't read input stream $this", e)
    }
}

fun <R> doUsingDefaultGSON(callback: (Gson) -> R) : R {
    return gson ?.let (callback) ?: {
        gson = initGSON()
        doUsingDefaultGSON(callback)
    }()
}

fun initGSON(): Gson {
    return GsonBuilder().run {
        implementIInputObjectAdapter()
        implementIOutputObjectAdapter()
        implementCommonIObjectAdapter()
        create()
    }
}

fun GsonBuilder.implementIInputObjectAdapter() {
    registerTypeHierarchyAdapter(
            IInputObject::class.java,
            JsonDeserializer<IInputObject<String, Any>> {
                json, _, _ ->
                JSONIObject(json.asJsonObject.toString())
            }
    )
}

fun GsonBuilder.implementIOutputObjectAdapter() {
    registerTypeHierarchyAdapter(
            IInputObject::class.java,
            JsonDeserializer<IOutputObject<String, Any>> {
                json, _, _ ->
                JSONIObject(json.asJsonObject.toString())
            }
    )
}

fun GsonBuilder.implementCommonIObjectAdapter() {
    registerTypeHierarchyAdapter(
            IInputObject::class.java,
            JsonDeserializer<CommonIObject<String, Any>> {
                json, _, _ ->
                JSONIObject(json.asJsonObject.toString())
            }
    )
}
