package com.github.insanusmokrassar.IObjectKRealisations

import com.github.insanusmokrassar.IObjectK.interfaces.CommonIObject
import com.github.insanusmokrassar.IObjectK.realisations.SimpleCommonIObject
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*

private val cache = WeakHashMap<CommonIObject<out Any, out Any>, ObservableCommonIObject<out Any, out Any>>()

fun <K: Any, V: Any> CommonIObject<K, V>.observable(): ObservableCommonIObject<K, V> {
    cache[this] ?. let {
        (it as? ObservableCommonIObject<K, V>) ?. let {
            return it
        }
    }
    cache.put(this, ObservableCommonIObject(this))
    return observable()
}

class ObservableCommonIObject<K: Any, V: Any> internal constructor(
        private val delegate: CommonIObject<K, V>
) :
        CommonIObject<K, V> by delegate {

    private val subject = PublishSubject.create<Pair<K, V?>>()

    val observable: Observable<Pair<K, V?>>
        get() = subject

    constructor(from: Map<K, V>): this() {
        putAll(from)
    }

    constructor(): this(SimpleCommonIObject())

    override fun put(key: K, value: V) {
        delegate.put(key, value)
        subject.onNext(Pair(key, value))
    }

    override fun putAll(toPutMap: Map<K, V>) {
        delegate.putAll(toPutMap)
        toPutMap.forEach {
            subject.onNext(Pair(it.key, it.value))
        }
    }

    override fun remove(key: K) {
        delegate.remove(key)
        subject.onNext(Pair(key, null))
    }
}
