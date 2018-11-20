package by.deniotokiari.arr.extentsion

import org.xmlpull.v1.XmlPullParser

fun XmlPullParser.getAttribute(title: String): String? {
    for (i in 0..(attributeCount - 1)) {
        if (getAttributeName(i) == title) {
            return getAttributeValue(i)
        }
    }

    return null
}