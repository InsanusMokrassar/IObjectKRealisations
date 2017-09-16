package com.github.insanusmokrassar.IObjectKRealisations

import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File

private var gson: Gson? = null

fun Any.toIObject(): IObject<Any> {
    return try {
        JSONIObject(gson!!.toJson(this))
    } catch (e: NullPointerException) {
        initGSON()
        toIObject()
    }
}

@Throws(IllegalArgumentException::class)
fun File.readIObject(): IObject<Any> {
    try {
        val file = inputStream()
        var resultException: Exception
        try {
            return JSONIObject(file)
        } catch (e: Exception) {
            resultException = Exception("File $absolutePath can't be read as json file: ${e.message}\n${e.stackTrace}", e)
        }
        try {
            return PropertiesIObject(file)
        } catch (e: Exception) {
            resultException = Exception("File $absolutePath can't be read as properties file: ${e.message}\n${e.stackTrace}", resultException)
        }
        throw resultException
    } catch (e: Exception) {
        throw IllegalArgumentException("For some of reason can't read file", e)
    }
}

private fun initGSON() {
    gson = GsonBuilder().create()
}
