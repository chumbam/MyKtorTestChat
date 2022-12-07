package com.isaev.myktortestchat.common

sealed class Progress<T>(val data: T? = null, val message: String? = null) {
    class Start<T>(data: T? = null) : Progress<T>(data)
    class Complete<T>(data: T?): Progress<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}
