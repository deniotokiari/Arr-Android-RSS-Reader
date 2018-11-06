package by.deniotokiari.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

val ui: CoroutineDispatcher = Dispatchers.Main

val bg: CoroutineDispatcher = Dispatchers.IO