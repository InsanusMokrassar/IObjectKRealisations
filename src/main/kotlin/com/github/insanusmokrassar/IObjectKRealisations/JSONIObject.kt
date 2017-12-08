package com.github.insanusmokrassar.IObjectKRealisations

import com.github.insanusmokrassar.IObjectK.exceptions.ReadException
import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.InputStream
import java.util.*
import kotlin.NoSuchElementException

class JSONIObject(val root: JSONObject) : IObject<Any> {

    override val size: Int
        get() = root.length()

    constructor(json: String) : this(JSONObject(json))

    constructor(inputStream: InputStream) : this({
        val scanner = Scanner(inputStream)
        val builder = StringBuilder()
        try {
            while (true) {
                builder.append("${scanner.nextLine()}\n")
            }
        } catch (e: NoSuchElementException) {}
        builder.toString()
    }())

    override fun put(key: String, value: Any) {
        root.put(key, value)
    }

    override fun <T : Any> get(key: String): T {
        try {
            val value = root[key]
            when (value) {
                is JSONObject -> return JSONIObject(value) as T
                is JSONArray -> return value.toList() as T
            }
            return value as T
        } catch (e: ClassCastException) {
            throw ReadException("Can't cast object to awaited type", e)
        } catch (e: JSONException) {
            throw ReadException("Can't find value for $key", e)
        }
    }

    override fun keys(): Set<String> {
        val result = HashSet<String>()
        root.keys().forEach {
            result.add(it as String)
        }
        return result
    }

    override fun putAll(toPutMap: Map<String, Any>) {
        toPutMap.keys
                .filter { toPutMap[it] != null }
                .forEach { put(it, toPutMap[it]!!) }
    }

    override fun remove(key: String) {
        root.remove(key)
    }

    override fun toString(): String {
        return root.toString()
    }
}

fun JSONArray.toList(): List<Any> {
    val result = ArrayList<Any>()

    for (i: Int in 0 until length()) {
        val current = get(i)
        when (current) {
            is JSONObject -> result.add(JSONIObject(current))
            is JSONArray -> result.add(current.toList())
            else -> result.add(current)
        }
    }

    return result
}
