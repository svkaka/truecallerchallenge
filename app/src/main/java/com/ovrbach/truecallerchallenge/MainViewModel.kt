package com.ovrbach.truecallerchallenge

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ovrbach.truecallerchallenge.common.RequestStatus
import com.ovrbach.truecallerchallenge.common.logic.ElementGetter
import com.ovrbach.truecallerchallenge.common.logic.WordsOccurrenceCounter
import com.ovrbach.truecallerchallenge.mylibrary.BASE_URL
import com.ovrbach.truecallerchallenge.mylibrary.FetchService
import com.ovrbach.truecallerchallenge.mylibrary.ServiceProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class MainViewModel : ViewModel(), CoroutineScope {
    val api: FetchService = ServiceProvider.instance.fetchService

    var lastUrlA: String = BASE_URL
    var lastUrlB: String = BASE_URL
    var lastUrlC: String = BASE_URL

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    val resultA: MutableLiveData<RequestStatus<Char?>> = MutableLiveData()
    val resultB: MutableLiveData<RequestStatus<List<Char>>> = MutableLiveData()
    val resultC: MutableLiveData<RequestStatus<Map<String, Int>>> = MutableLiveData()

    fun runRequests(url: String = BASE_URL) {
        get10th(url)
        getEvery10th(url)
        getWordOccurrences(url)
    }

    private fun get10th(url: String) {
        resultA.postValue(RequestStatus.Requesting)
        lastUrlA = url
        launch {
            try {
                val response = api.grab10thChar(url).await()
                val result = ElementGetter.get10thCharacter(response.byteStream())
                resultA.postValue(RequestStatus.Success(result))
            } catch (e: Exception) {
                resultA.postValue(RequestStatus.Failure(e))
            }
        }
    }

    private fun getEvery10th(url: String) {
        resultB.postValue(RequestStatus.Requesting)
        lastUrlB = url
        launch {
            try {
                val response = api.grabEvery10Char(url).await()
                val result = ElementGetter.getEvery10thCharacter(response.byteStream())
                resultB.postValue(RequestStatus.Success(result))
            } catch (e: Exception) {
                resultB.postValue(RequestStatus.Failure(e))
            }
        }
    }

    private fun getWordOccurrences(url: String) {
        resultC.postValue(RequestStatus.Requesting)
        lastUrlC = url
        launch {
            try {
                val response = api.wordCountRequest(url).await()
                val result = WordsOccurrenceCounter.getWordsOccurrenceCount(response.byteStream())
                resultC.postValue(RequestStatus.Success(result))
            } catch (e: Exception) {
                resultC.postValue(RequestStatus.Failure(e))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}