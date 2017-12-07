package com.github.insanusmokrassar.IObjectKRealisations

import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.util.*
import kotlin.collections.HashSet

class PropertiesIObject : IObject<Any> {
    private val properties: Properties


    constructor(propertyFilePath: String) {
        val inputStream = FileInputStream(File(propertyFilePath))
        properties = Properties()
        properties.load(inputStream)
        properties.keys.forEach {
            keys.add(it.toString())
        }
    }

    constructor(propertiesFile: Properties) {
        this.properties = propertiesFile
        properties.keys.forEach {
            keys.add(it.toString())
        }
    }

    constructor(propertiesInputStream: InputStream) {
        properties = Properties()
        properties.load(propertiesInputStream)
        properties.keys.forEach {
            keys.add(it.toString())
        }
    }

    override val keys: MutableSet<String>
        get() = mutableSetOf(*properties.keys.map { it.toString() }.toTypedArray())

    override val entries: MutableSet<MutableMap.MutableEntry<String, Any>>
        get() {
            val set = HashSet<MutableMap.MutableEntry<String, Any>>()
            keys.forEach {
                set.add(MutableIObjectEntry(it, this))
            }
            return set
        }
    override val values: MutableCollection<Any>
        get() {
            val mutableCollection = ArrayList<Any>()

            properties.keys().iterator().forEach {
                properties[it.toString()] ?.let {
                    mutableCollection.add(it)
                }
            }

            return mutableCollection
        }

    override val size: Int
        get() = properties.size

    override fun put(key: String, value: Any) {
        synchronized(this, {
            properties.put(key, value)
            keys.add(key)
        })
    }

    override fun <T : Any> getTyped(key: String): T? {
        return properties[key] as? T
    }

    override fun putAll(from: Map<out String, Any>) {
        synchronized(this, {
            from.forEach {
                properties.put(it.key, it.value)
                keys.add(it.key)
            }
        })
    }

    override fun remove(key: String) {
        synchronized(this, {
            properties.remove(key)
            keys.remove(key)
        })
    }

    override fun containsKey(key: String): Boolean = properties.containsKey(key)

    override fun containsValue(value: Any): Boolean = properties.containsValue(value)

    override fun get(key: String): Any? = properties[key]

    override fun isEmpty(): Boolean = properties.isEmpty

    override fun clear() {
        properties.clear()
    }
}