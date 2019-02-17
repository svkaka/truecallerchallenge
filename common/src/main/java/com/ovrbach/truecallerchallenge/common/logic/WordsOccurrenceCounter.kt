package com.ovrbach.truecallerchallenge.common.logic

import java.io.InputStream

class WordsOccurrenceCounter {
    companion object {
        fun getWordsOccurrenceCount(string: String): Map<String, Int> {
            if (string.isEmpty()) return mapOf()
            val words = string.split("\\s".toRegex())
            return words.groupingBy { it.toLowerCase() }.eachCount()
        }

        fun getWordsOccurrenceCount(inputStream: InputStream): Map<String, Int> {
            val string = inputStream.bufferedReader().use { it.readText() }
            return getWordsOccurrenceCount(string)
        }
    }
}