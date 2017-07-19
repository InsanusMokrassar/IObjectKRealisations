package com.github.insanusmokrassar.IObjectKRealisations

import com.github.insanusmokrassar.iobjectk.interfaces.IObject
import org.json.JSONObject
import java.io.FileInputStream
import java.util.logging.Logger

fun main(args: Array<String>) {
    val jsonObjectExample = JSONObject()
    jsonObjectExample.put("Hello", "World")
    jsonObjectExample.put("InnerObject", JSONObject("{\"Inner hello\":\"World\"}"))
    val jsonIObject : IObject<Any> = JSONIObject(jsonObjectExample);
    println(jsonIObject.toString())
    println(jsonIObject.get<IObject<Any>>("InnerObject").toString())
}

@Throws(IllegalArgumentException::class)
fun openFile(filePath: String): IObject<Any> {
    try {
        val file = FileInputStream(filePath)
        var resultException: Exception
        try {
            return JSONIObject(file)
        } catch (e: Exception) {
            resultException = Exception("File $filePath can't be read as json file: ${e.message}\n${e.stackTrace}", e)
        }
        try {
            return PropertiesIObject(file)
        } catch (e: Exception) {
            resultException = Exception("File $filePath can't be read as properties file: ${e.message}\n${e.stackTrace}", resultException)
        }
        throw resultException
    } catch (e: Exception) {
        throw IllegalArgumentException("For some of reason can't read file", e)
    }
}
