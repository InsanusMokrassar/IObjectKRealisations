package com.github.insanusmokrassar.IObjectKRealisations

import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.InputStream
import java.util.*
import kotlin.NoSuchElementException
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class JSONIObject(private val root: JSONObject) : IObject<Any> {
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


    override val entries: MutableSet<MutableMap.MutableEntry<String, Any>>
        get() {
            val set = HashSet<MutableMap.MutableEntry<String, Any>>()
            keys.forEach {
                set.add(MutableIObjectEntry(it, this))
            }
            return set
        }

    override val keys: MutableSet<String>
        get() {
            val result = HashSet<String>()
            root.keys().forEach {
                it ?.let {
                    result.add(it.toString())
                }
            }
            return result
        }

    override val values: MutableCollection<Any>
        get() {
            val mutableCollection = ArrayList<Any>()

            root.keys().forEach {
                mutableCollection.add(root[it.toString()])
            }

            return mutableCollection
        }

    override fun put(key: String, value: Any) {
        root.put(key, value)
    }

    override fun <T : Any> getTyped(key: String): T? {
        val value = root[key]
        when (value) {
            is JSONObject -> return JSONIObject(value) as T
            is JSONArray -> return value.toList() as T
        }
        return value as? T
    }

    override fun remove(key: String) {
        root.remove(key)
    }

    override val size: Int
        get() = root.length()

    override fun containsKey(key: String): Boolean = root.has(key)

    override fun containsValue(value: Any): Boolean {
        forEach {
            if (it.value == value) {
                return true
            }
        }
        return false
    }

    override fun get(key: String): Any? {
        return try {
            root.get(key)
        } catch (e: JSONException) {
            null
        }
    }

    override fun isEmpty(): Boolean = size == 0

    override fun clear() {
        keys.forEach {
            root.remove(it)
        }
    }

    override fun putAll(from: Map<out String, Any>) {
        from.keys
                .filter { from[it] != null }
                .forEach { put(it, from[it]!!) }
    }

    override fun toString(): String {
        return root.toString()
    }
}

fun JSONArray.toList(): List<Any> {
    val result = ArrayList<Any>()

    (0 until length()).forEach {
        val current = get(it)
        when (current) {
            is JSONObject -> result.add(JSONIObject(current))
            is JSONArray -> result.add(current.toList())
            else -> result.add(current)
        }
    }

    return result
}
