package com.github.insanusmokrassar.IObjectKRealisations

import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import java.io.FileInputStream

fun load(filename: String) : IObject<Any> {
    return (ClassLoader.getSystemResourceAsStream(filename) ?: FileInputStream(filename)).readIObject()
}
