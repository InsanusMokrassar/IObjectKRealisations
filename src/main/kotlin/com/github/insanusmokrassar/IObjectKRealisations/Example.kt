package com.github.insanusmokrassar.IObjectKRealisations

import com.github.insanusmokrassar.iobjectk.interfaces.IObject
import org.json.JSONObject

fun main(args: Array<String>) {
    val jsonObjectExample = JSONObject()
    jsonObjectExample.put("Hello", "World")
    jsonObjectExample.put("InnerObject", JSONObject("{\"Inner hello\":\"World\"}"))
    val jsonIObject : IObject<Any> = JSONIObject(jsonObjectExample);
    println(jsonIObject.toString())
    println(jsonIObject.get<IObject<Any>>("InnerObject").toString())
}
