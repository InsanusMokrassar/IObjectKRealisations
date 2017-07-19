package com.github.insanusmokrassar.IObjectKRealisations

import com.github.insanusmokrassar.iobjectk.exceptions.ReadException
import com.github.insanusmokrassar.iobjectk.interfaces.IObject
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.util.*
import kotlin.NoSuchElementException

class JSONIObject(val root: JSONObject) : IObject<Any> {

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
        val value = root[key]
        try {
            when (value) {
                is JSONObject -> return JSONIObject(value) as T
                is JSONArray -> {
                    val result = ArrayList<Any>()

                    for (i: Int in 0..value.length() - 1) {
                        val current = value.get(i)
                        if (current is JSONObject) {
                            result.add(JSONIObject(current))
                            continue
                        }
                        result.add(current)
                    }

                    return result as T
                }
            }
            return value as T
        } catch (e: ClassCastException) {
            throw ReadException("Can't cast object to awaited type", e)
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