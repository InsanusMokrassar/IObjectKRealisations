package com.github.insanusmokrassar.IObjectKRealisations

import com.github.insanusmokrassar.IObjectK.exceptions.ReadException
import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import com.github.insanusmokrassar.IObjectK.interfaces.has
import com.github.insanusmokrassar.IObjectK.realisations.SimpleIObject
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler
import java.io.InputStream
import java.util.*
import javax.xml.parsers.SAXParserFactory

private class SAXXmlParser : DefaultHandler() {

    private val statesStack = Stack<IObject<Any>>()
    private var root: IObject<Any>? = null

    var lastResult: IObject<Any>? = null
        private set

    override fun startDocument() {
        super.startDocument()
        root = SimpleIObject()
        statesStack.clear()
        statesStack.push(root)
    }

    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
        super.startElement(uri, localName, qName, attributes)
        val currentObject = SimpleIObject()
        val key = qName
        key ?: return
        attributes ?: return
        statesStack.peek().let {
            if (it.has(key)) {
                val currentValue = try {
                    it.get<List<Any>>(key)
                } catch(e: ClassCastException) {
                    val currentValue = it.get<Any>(key)
                    listOf(currentValue)
                }
                it[key] = currentValue.plus(currentObject)
            } else {
                it[key] = currentObject
            }
        }
        (0 until attributes.length).forEach {
            currentObject[attributes.getQName(it)] = attributes.getValue(it)
        }
        statesStack.push(currentObject)
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
        super.characters(ch, start, length)
        statesStack.peek().apply {
            ch ?: return
            val newText = String(ch).substring(start until (start + length))
            if (has("text")) {
                set("text", "${get<String>("text")}$newText")
            } else {
                set("text", newText)
            }
        }
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        super.endElement(uri, localName, qName)
        statesStack.pop()
    }

    override fun endDocument() {
        super.endDocument()
        statesStack.pop()
        lastResult = root
        root = null
    }
}

class XMLIObject(inputStream: InputStream) : SimpleIObject(
        SAXParserFactory.newInstance().run {
            val parser = SAXXmlParser()
            newSAXParser().parse(
                    inputStream,
                    parser
            )
            parser.lastResult ?: throw IllegalArgumentException("Can't parse this xml")
        }
) {
    constructor(xmlText: String): this(xmlText.byteInputStream())
}
