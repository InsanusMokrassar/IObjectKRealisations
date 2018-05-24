package com.github.insanusmokrassar.IObjectKRealisations

import com.github.insanusmokrassar.IObjectK.extensions.toJsonString
import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import com.github.insanusmokrassar.IObjectK.realisations.SimpleIObject
import com.github.insanusmokrassar.IObjectK.utils.plus

fun main(args: Array<String>) {
    val jsonIObject = ClassLoader.getSystemResourceAsStream("example.json").readIObject()
    println(jsonIObject.toString())
    println(jsonIObject.get<IObject<Any>>("exampleInner").toString())

    jsonIObject.putAll(
            mapOf(
                    Pair("Example", true),
                    Pair("Example2", 2),
                    Pair("Example3", "3")
            )
    )

    jsonIObject["Example"] = "Example"
    jsonIObject["Example"] = 2
    jsonIObject["Example"] = true

    jsonIObject.remove("Example")

    (jsonIObject + SimpleIObject()).apply {
        this["example"] = "exampleValue"
        this["array"] = listOf(
                "it",
                "is",
                "array",
                4,
                true,
                Any()
        )
    }.apply {
        println(this)
        println(this.toJsonString())
    }
    println(jsonIObject.toJsonString())

    ClassLoader.getSystemResourceAsStream("example.xml").readIObject().let {
        println(it)
        println(it.toJsonString())
    }
}
