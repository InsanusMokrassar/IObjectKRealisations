package com.github.insanusmokrassar.IObjectKRealisations

import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import com.google.gson.Gson
import com.google.gson.GsonBuilder

private var gson: Gson? = null

fun Any.toIObject(): IObject<Any> {
    return try {
        JSONIObject(gson!!.toJson(this))
    } catch (e: NullPointerException) {
        initGSON()
        toIObject()
    }
}

private fun initGSON() {
    gson = GsonBuilder().create()
}
