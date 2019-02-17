package com.ovrbach.truecallerchallenge.common

sealed class RequestStatus<out T : Any?> {
    object Cancelled : RequestStatus<Nothing>()
    object Requesting : RequestStatus<Nothing>()
    data class Success<out T : Any?>(val data: T) : RequestStatus<T>()
    data class Failure(val error: Throwable) : RequestStatus<Nothing>()

}