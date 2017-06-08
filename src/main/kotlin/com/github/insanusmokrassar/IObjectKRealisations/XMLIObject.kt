package com.github.insanusmokrassar.IObjectKRealisations

import com.github.insanusmokrassar.iobjectk.exceptions.ReadException
import com.github.insanusmokrassar.iobjectk.interfaces.IObject
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory



//class XMLIObject(xmlSource: InputStream): IObject<Any> {
//
//    val root: Element
//    val childs: NodeList
//    init {
//        // Создается построитель документа
//        val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
//        // Создается дерево DOM документа из файла
//        val document = documentBuilder.parse(xmlSource)
//
//        // Получаем корневой элемент
//        root = document.documentElement
//        childs = root.childNodes;
//    }
//
//    override fun put(key: String, value: Any) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun <T : Any> get(key: String): T {
//        var targetNode: Node? = null
//        for (i: Int in 0..childs.length - 1) {
//            if (childs.item(i).nodeName == key) {
//                targetNode = childs.item(i)
//                break
//            }
//        }
//        if (targetNode == null) {
//            throw ReadException("Can't find object for key ${key}")
//        } else {
//            targetNode.attributes
//        }
//    }
//
//    override fun keys(): Set<String> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun putAll(toPutMap: Map<String, Any>) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun remove(key: String) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//}