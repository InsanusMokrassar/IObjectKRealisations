package com.github.insanusmokrassar.IObjectKRealisations

import com.github.insanusmokrassar.IObjectK.extensions.asMap
import com.github.insanusmokrassar.IObjectK.interfaces.CommonIObject
import com.github.insanusmokrassar.IObjectK.interfaces.IInputObject
import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import com.github.insanusmokrassar.IObjectK.interfaces.IOutputObject
import com.github.insanusmokrassar.IObjectK.realisations.SimpleCommonIObject
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.json.JSONObject
import java.io.InputStream

private var gson: Gson? = null

fun <K: Any, V: Any> IInputObject<K, V>.toStringMap(): Map<String, String> {
    val result = HashMap<String, String>()
    keys().forEach {
        result[it.toString()] = get<V>(it).toString()
    }
    return result
}

fun <T> IInputObject<String, out Any>.toObject(targetClass: Class<T>): T {
    return doUsingDefaultGSON {
        it.fromJson(JSONObject(this.asMap()).toString(), targetClass)
    }
}

fun Any.toIObject(): IObject<Any> {
    return doUsingDefaultGSON {
        JSONIObject(it.toJson(this))
    }
}

@Throws(IllegalArgumentException::class)
fun InputStream.readIObject(): IObject<Any> {
    try {
        var resultException: Exception
        try {
            return JSONIObject(this)
        } catch (e: Exception) {
            resultException = Exception("Input stream $this can't be read as json: ${e.message}\n${e.stackTrace}", e)
        }
        try {
            return PropertiesIObject(this)
        } catch (e: Exception) {
            resultException = Exception("Input stream $this can't be read as properties: ${e.message}\n${e.stackTrace}", resultException)
        }
        throw resultException
    } catch (e: Exception) {
        throw IllegalArgumentException("For some of reason can't read input stream $this", e)
    }
}

fun <R> doUsingDefaultGSON(callback: (Gson) -> R) : R {
    return gson ?.let (callback) ?: {
        initGSON()
        doUsingDefaultGSON(callback)
    }()
}

private fun initGSON() {
    gson = GsonBuilder().run {
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
