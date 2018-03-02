package com.github.insanusmokrassar.IObjectKRealisations

import com.github.insanusmokrassar.IObjectK.exceptions.ReadException
import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.util.*

class PropertiesIObject : IObject<Any> {

    private val properties: Properties

    override val size: Int
        get() = properties.size

    constructor(propertyFilePath: String) {
        val inputStream = FileInputStream(File(propertyFilePath))
        properties = Properties()
        properties.load(inputStream)
    }

    constructor(propertiesFile: Properties) {
        this.properties = propertiesFile
    }

    constructor(propertiesInputStream: InputStream) {
        properties = Properties()
        properties.load(propertiesInputStream)
    }

    override fun set(key: String, value: Any) {
        synchronized(this, {
            properties[key] = value
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
            return properties.keys.map { it.toString() }.toSet()
        })
    }

    override fun putAll(toPutMap: Map<String, Any>) {
        synchronized(this, {
            toPutMap.forEach {
                properties[it.key] = it.value
            }
        })
    }

    override fun remove(key: String) {
        synchronized(this, {
            properties.remove(key)
        })
    }
}
