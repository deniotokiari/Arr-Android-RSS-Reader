package by.deniotokiari.arr.coroutines

import kotlinx.coroutines.*

fun launchBg(block: suspend CoroutineScope.() -> Unit): Job {
    return GlobalScope.launch(Dispatchers.IO, block = block)
}

fun launchUi(block: suspend CoroutineScope.() -> Unit): Job {
    return GlobalScope.launch(Dispatchers.Main, block = block)
}

suspend fun <T> withBgContext(block: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.IO, block = block)
}

suspend fun <T> withUiContext(block: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.Main, block = block)
}

fun <T> asyncBg(block: suspend CoroutineScope.() -> T): Deferred<T> {
    return GlobalScope.async(Dispatchers.IO, block = block)
}

fun <T> asyncUi(block: suspend CoroutineScope.() -> T): Deferred<T> {
    return GlobalScope.async(Dispatchers.Main, block = block)
}