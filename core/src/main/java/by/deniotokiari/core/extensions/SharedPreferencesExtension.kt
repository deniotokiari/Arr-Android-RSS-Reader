package by.deniotokiari.core.extensions

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

inline fun <T> SharedPreferences.delegate(
    defaultValue: T,
    key: String?,
    crossinline getter: SharedPreferences.(String, T) -> T,
    crossinline setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor
): ReadWriteProperty<Any, T> {
    return object : ReadWriteProperty<Any, T> {

        override fun getValue(thisRef: Any, property: KProperty<*>): T = getter(key ?: property.name, defaultValue)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) = edit().setter(key ?: property.name, value).apply()

    }
}

inline fun <reified T : Any> SharedPreferences.preference(defaultValue: T? = null, key: String? = null): ReadWriteProperty<Any, T> {
    return when (T::class) {
        String::class -> delegate(defaultValue as? String ?: "", key, SharedPreferences::getString, SharedPreferences.Editor::putString) as ReadWriteProperty<Any, T>
        Int::class -> delegate(defaultValue as? Int ?: -1, key, SharedPreferences::getInt, SharedPreferences.Editor::putInt) as ReadWriteProperty<Any, T>
        Boolean::class -> delegate(defaultValue as? Boolean ?: false, key, SharedPreferences::getBoolean, SharedPreferences.Editor::putBoolean) as ReadWriteProperty<Any, T>
        Float::class -> delegate(defaultValue as? Float ?: -1F, key, SharedPreferences::getFloat, SharedPreferences.Editor::putFloat) as ReadWriteProperty<Any, T>
        Long::class -> delegate(defaultValue as? Long ?: -1L, key, SharedPreferences::getLong, SharedPreferences.Editor::putLong) as ReadWriteProperty<Any, T>

        else -> throw UnsupportedOperationException()
    }
}

private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor: SharedPreferences.Editor = edit()

    operation(editor)

    editor.apply()
}

operator fun SharedPreferences.set(key: String, value: Any?) {
    when (value) {
        is String? -> edit { it.putString(key, value as String) }
        is Int? -> edit { it.putInt(key, value as Int) }
        is Boolean? -> edit { it.putBoolean(key, value as Boolean) }
        is Float? -> edit { it.putFloat(key, value as Float) }
        is Long? -> edit { it.putLong(key, value as Long) }

        else -> throw UnsupportedOperationException()
    }
}

inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T = when (T::class) {
    String::class -> getString(key, defaultValue as? String ?: "") as T
    Int::class -> getInt(key, defaultValue as? Int ?: -1) as T
    Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T
    Float::class -> getFloat(key, defaultValue as? Float ?: -1F) as T
    Long::class -> getLong(key, defaultValue as? Long ?: -1L) as T

    else -> throw UnsupportedOperationException()
}