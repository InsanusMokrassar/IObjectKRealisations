package com.github.insanusmokrassar.IObjectKRealisations

import com.github.insanusmokrassar.IObjectK.interfaces.IObject

class MutableIObjectEntry<V : Any>(
        override val key: String,
        private val targetJSONIObject: IObject<Any>
) : MutableMap.MutableEntry<String, V> {

    override val value: V
        get() = targetJSONIObject.getTyped(key) ?: throw IllegalStateException("Value is absent")

    override fun setValue(newValue: V): V {
        targetJSONIObject[key] = newValue
        return newValue
    }
}
