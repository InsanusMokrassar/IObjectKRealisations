package com.github.insanusmokrassar.IObjectKRealisations

import com.github.insanusmokrassar.IObjectK.exceptions.ReadException
import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.util.*
import kotlin.collections.HashSet

class PropertiesIObject : IObject<Any> {

    protected val properties: Properties
    protected val keys: MutableSet<String> = HashSet()

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

    override fun put(key: String, value: Any) {
        synchronized(this, {
            properties.put(key, value)
            keys.add(key)
        })
    }

    override fun <T : Any> get(key: String): T {
        try {
            return properties[key] as T
        } catch (e: ClassCastException) {
            throw ReadException("Can't cast object to awaited type", e)
        }
    }

    override fun keys(): Set<String> {
        synchronized(this, {
            return keys
        })
    }

    override fun putAll(toPutMap: Map<String, Any>) {
        synchronized(this, {
            toPutMap.forEach {
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
}