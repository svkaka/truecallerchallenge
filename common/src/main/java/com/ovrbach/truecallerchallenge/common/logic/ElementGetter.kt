package com.ovrbach.truecallerchallenge.common.logic

import java.io.InputStream

class ElementGetter {
    companion object {
        fun get10thCharacter(string: String): Char? {
            return string.getTrueElement(10)
        }

        fun get10thCharacter(inputStream: InputStream): Char? {
            return inputStream.getTrueElement(10)
        }

        fun getEvery10thCharacter(string: String): CharArray? {
            if (string.isEmpty()) return null
            return getEveryNthCharacter(string, 10)
        }

        fun getEvery10thCharacter(stream: InputStream): MutableList<Char> {
            return getEveryNthCharacter(stream, 10)
        }

        fun getEveryNthCharacter(string: String, n: Int = 10): CharArray {
            if (n < 1) throw IllegalArgumentException("0 and less than 0 not supported")

            val numberOfOccurrences = string.length / n
            val array = CharArray(numberOfOccurrences)
            for (i in 1..numberOfOccurrences) {
                array[i - 1] = string.getTrueElement(i * n)!!
            }
            return array
        }

        fun getEveryNthCharacter(inputStream: InputStream, n: Int = 10): MutableList<Char> {
            if (n < 1) throw IllegalArgumentException("0 and less than 0 not supported")

            val humanIndex = n.toHumanIndex()

            var read: Int = -1
            val items = mutableListOf<Char>()

            if (humanIndex == 0) {
                while ({ read = inputStream.read(); read }() != -1) {
                    items.add(read.toChar())
                }
            } else {
                var firstIteration = true
                while ({ read = inputStream.read(); read }() != -1) {
                    if (firstIteration) {
                        inputStream.skip((humanIndex - 1).toLong())
                        firstIteration = false
                    } else {
                        items.add(read.toChar())
                        inputStream.skip((humanIndex).toLong())
                    }
                }
            }
            return items
        }
    }
}

/**
 * Returns elements as people would do
 * Example :
 * `Hello world`.get(1) == H
 * `Hello world`.getTrueElement(1) == e
 */
private fun String.getTrueElement(index: Int): Char? {
    val humanIndex = index.toHumanIndex()
    if (humanIndex < 0 || humanIndex > length) return null
    return this[humanIndex]
}

/**
 * Returns elements as people would do
 * Example :
 * `Hello world`.get(1) == H
 * `Hello world`.getTrueElement(1) == e
 */
private fun InputStream.getTrueElement(index: Int): Char? {
    val humanIndex = index.toHumanIndex()
    if (humanIndex < 0 || humanIndex > available()) throw Exception("You are trying to access unavailable")
    if (skip(humanIndex.toLong()) != -1L) {
        return read().toChar()
    }
    return null
}

private fun Int.toHumanIndex(): Int = this - 1