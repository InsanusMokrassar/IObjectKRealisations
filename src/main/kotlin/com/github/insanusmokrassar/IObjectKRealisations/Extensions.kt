package com.github.insanusmokrassar.IObjectKRealisations

import com.github.insanusmokrassar.IObjectK.extensions.toJsonString
import com.github.insanusmokrassar.IObjectK.interfaces.CommonIObject
import com.github.insanusmokrassar.IObjectK.interfaces.IInputObject
import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import com.github.insanusmokrassar.IObjectK.interfaces.IOutputObject
import com.github.insanusmokrassar.IObjectK.realisations.SimpleCommonIObject
import com.github.insanusmokrassar.IObjectK.realisations.SimpleIObject
import com.google.gson.*
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
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

fun <T> String.toObject(toClass: Class<T>): T = doUsingDefaultGSON {
    it.fromJson(this, toClass)
}

fun Any.toJson(): String = when (this) {
    is IInputObject<*, *> -> (this as? IInputObject<String, Any>) ?.toJsonString()
    else -> null
} ?: doUsingDefaultGSON {
    it.toJson(this)
}

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
        it.fromJson(it.toJson(this), SimpleIObject::class.java)
    }
}

@Throws(ReadIObjectException::class)
fun String.readIObject(): IObject<Any> {
    val resultExceptions = mutableListOf<Exception>()
    try {
        return doUsingDefaultGSON {
            it.fromJson(this, SimpleIObject::class.java)
        }
    } catch (e: Exception) {
        resultExceptions.add(Exception("Input stream $this can't be read as json: ${e.message}\n${e.stackTrace}", e))
    }
    try {
        return XMLIObject(this)
    } catch (e: Exception) {
        resultExceptions.add(Exception("Input stream $this can't be read as xml: ${e.message}\n${e.stackTrace}"))
    }
    try {
        return PropertiesIObject(this)
    } catch (e: Exception) {
        resultExceptions.add(Exception("Input stream $this can't be read as properties: ${e.message}\n${e.stackTrace}"))
    }
    throw ReadIObjectException(resultExceptions)
}

@Throws(ReadIObjectException::class)
fun InputStream.readIObject(): IObject<Any> {
    try {
        return InputStreamReader(this).readText().readIObject()
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
        implementIInputObjectOutputAdapter()
        create()
    }
}

fun GsonBuilder.implementIInputObjectOutputAdapter() {
    registerTypeHierarchyAdapter(
        IInputObject::class.java,
        JsonSerializer<IInputObject<*, *>> {
            src, _, context ->
            JsonObject().apply {
                src.forEach {
                    val nnPair = (it.first ?: return@forEach).toString() to (it.second ?: return@forEach)
                    add(nnPair.first, context.serialize(nnPair.second))
                }
            }
        }
    )
}

fun GsonBuilder.implementIInputObjectAdapter() {
    registerTypeHierarchyAdapter(
        IInputObject::class.java,
        JsonDeserializer<CommonIObject<*, *>> {
            json, _, _ ->
            json.asJsonObject.toSimpleIObject()
        }
    )
}

private fun JsonObject.toSimpleIObject(): SimpleIObject {
    return SimpleIObject().apply {
        entrySet().forEach {
            val value: Any? = if (it.value.isJsonPrimitive) {
                val primitive = it.value.asJsonPrimitive
                if (primitive.isBoolean) {
                    primitive.asBoolean
                } else {
                    if (primitive.isNumber) {
                        primitive.asNumber
                    } else {
                        primitive.asString
                    }
                }
            } else {
                if (it.value.isJsonArray) {
                    it.value.asJsonArray.toList()
                } else {
                    if (it.value.isJsonObject) {
                        it.value.asJsonObject.toSimpleIObject()
                    } else {
                        null
                    }
                }
            }
            value ?.let {
                resultValue ->
                this[it.key] = resultValue
            }
        }
    }

}

private fun JsonArray.toList(): List<Any> {
    return mapNotNull {
        val value: Any? = if (it.isJsonPrimitive) {
            val primitive = it.asJsonPrimitive
            if (primitive.isBoolean) {
                primitive.asBoolean
            } else {
                if (primitive.isNumber) {
                    primitive.asNumber
                } else {
                    primitive.asString
                }
            }
        } else {
            if (it.isJsonArray) {
                it.asJsonArray.toList()
            } else {
                if (it.isJsonObject) {
                    it.asJsonObject.toSimpleIObject()
                } else {
                    null
                }
            }
        }
        value
    }
}
