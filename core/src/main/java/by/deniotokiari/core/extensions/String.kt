package by.deniotokiari.core.extensions

import android.text.Html

fun String.stripHtml(): String {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        Html.fromHtml(this).toString()
    }
            .replace('\n', ' ')
            .replace(65532.toChar(), ' ')
            .replace(Regex("\\s+"), " ")
            .trim()
}