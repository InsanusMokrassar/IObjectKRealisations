package com.github.insanusmokrassar.IObjectKRealisations

import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream

fun main(args: Array<String>) {
    val jsonObjectExample = JSONObject()
    jsonObjectExample.put("Hello", "World")
    jsonObjectExample.put("InnerObject", JSONObject("{\"Inner hello\":\"World\"}"))
    val jsonIObject : IObject<Any> = JSONIObject(jsonObjectExample);
    println(jsonIObject.toString())
    println(jsonIObject.get<IObject<Any>>("InnerObject").toString())
}
