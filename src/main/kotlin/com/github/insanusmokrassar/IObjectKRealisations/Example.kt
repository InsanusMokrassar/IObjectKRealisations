package com.github.insanusmokrassar.IObjectKRealisations

import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import org.json.JSONObject

fun main(args: Array<String>) {
    val jsonObjectExample = JSONObject()
    jsonObjectExample.put("Hello", "World")
    jsonObjectExample.put("InnerObject", JSONObject("{\"Inner hello\":\"World\"}"))
    val jsonIObject = JSONIObject(jsonObjectExample)
    println(jsonIObject.toString())
    println(jsonIObject.get<IObject<Any>>("InnerObject").toString())

    jsonIObject.putAll(
            mapOf(
                    Pair("Example", "Example"),
                    Pair("Example", "Example2"),
                    Pair("Example", "Example3")
            )
    )

    jsonIObject.put("Example", "Example")
    jsonIObject.put("Example", 2)
    jsonIObject.put("Example", true)

    jsonIObject.remove("Example")
}
