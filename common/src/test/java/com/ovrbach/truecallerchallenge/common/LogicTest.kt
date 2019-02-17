package com.ovrbach.truecallerchallenge.common

import com.ovrbach.truecallerchallenge.common.logic.ElementGetter
import com.ovrbach.truecallerchallenge.common.logic.WordsOccurrenceCounter
import org.junit.Assert.assertTrue
import org.junit.Test

//todo slovak characters
class LogicTest {

    private val textNormal: String by lazy {
        "Material is the metaphor\n" +
                "Material Design is inspired by the physical world and its textures, including how they reflect light and cast shadows. Material surfaces reimagine the mediums of paper and ink.\n" +
                "Bold, graphic, intentional\n" +
                "Material Design is guided by print design methods - typography, grids, space, scale, color, and imagery - to create hierarchy, meaning, and focus that immerse viewers in the experience.\n" +
                "Motion provides meaning\n" +
                "Motion focuses attention and maintains continuity, through subtle feedback and coherent transitions. As elements appear on screen, they transform and reorganize the environment, with interactions generating new transformations.\n" +
                "Flexible foundation\n" +
                "The Material Design system is designed to enable brand expression. It's integrated with a custom code base that allows the seamless implementation of components, plug-ins, and design elements.\n" +
                "Cross-platform\n" +
                "Material Design maintains the same UI across platforms, using shared components across Android, iOS, Flutter, and the web."
    }

    private val textDiacritical by lazy {
        "samohlásky \t- krátke a, e, i, o, u, ä\n" +
                "\t\t\t- dlhé á, é, í, ó, ú \n" +
                "\t\t\t- dvojhlásky ia, ie, iu, ô\n" +
                "spoluhlásky - tvrdé d, t, n, l, ch, h, g, k\n" +
                "\t\t\t- mäkké ď, ť, ň, ľ, c, č, ž, š, dz, dž, j\n" +
                "\t\t\t- obojaké m, b, p, v, z, s, f, r\n" +
                "Slovenčina má 46 písmen."
    }
    private val textShort: String by lazy {
        "ABCDEFGHIJKLMNOUPQRSTUVWXYZ"
    }

    private val textShortWords: String by lazy {
        "A B C D E F    G H I \nJ K L M N O U P Q R S T U V W X Y Z"
    }

    private val textEmpty: String by lazy {
        ""
    }

    @Test
    fun getEvery10thCharacterTest() {
        val resultEmpty = ElementGetter.getEvery10thCharacter(textEmpty)

        val resultShort = ElementGetter.getEvery10thCharacter(textShort)

        val resultNormal = ElementGetter.getEvery10thCharacter(textNormal)
    }

    val expectedShort = charArrayOf(
            'A',
            'B',
            'C',
            'D',
            'E',
            'F',
            'G',
            'H',
            'I',
            'J',
            'K',
            'L',
            'M',
            'N',
            'O',
            'U',
            'P',
            'Q',
            'R',
            'S',
            'T',
            'U',
            'V',
            'W',
            'X',
            'Y',
            'Z'
    )
    val expectedNormal = charArrayOf('i', 'k', ' ', 't', 'u', ' ', ' ', 'a', 'r', 't')

    @Test
    fun getEveryNthCharacterFromStringTest() {
        val resultEmpty = ElementGetter.getEveryNthCharacter(textEmpty, 2)
        assertTrue(resultEmpty.isEmpty())

        val resultShort = ElementGetter.getEveryNthCharacter(textShort, 1)
        assertTrue(
                expectedShort.size == resultShort.size
        )
        expectedShort.forEachIndexed { index, c ->
            assertTrue(expectedShort[index] == resultShort[index])
        }

        val resultNormal = ElementGetter.getEveryNthCharacter(textNormal, 100)
        assertTrue(
                expectedNormal.size == resultNormal.size
        )
        expectedNormal.forEachIndexed { index, c ->
            assertTrue(expectedNormal[index] == resultNormal[index])
        }

        val exceptionThrown = try {
            ElementGetter.getEveryNthCharacter(textEmpty, 0)
            false
        } catch (e: IllegalArgumentException) {
            true
        }
        assertTrue(exceptionThrown)
    }

    @Test
    fun getEveryNthCharacterTestFromStream() {
        val resultEmpty = ElementGetter.getEveryNthCharacter(textEmpty.byteInputStream(), 2)
        assertTrue(resultEmpty.isEmpty())

        val resultShort = ElementGetter.getEveryNthCharacter(textShort.byteInputStream(), 1)
        assertTrue(
                expectedShort.size == resultShort.size
        )
        expectedNormal.forEachIndexed { index, c ->
            assertTrue(expectedShort[index] == resultShort[index])
        }

        val resultNormal = ElementGetter.getEveryNthCharacter(textNormal.byteInputStream(), 100)
        assertTrue(
                expectedNormal.size == resultNormal.size
        )

        expectedNormal.forEachIndexed { index, c ->
            assertTrue(expectedNormal[index] == resultNormal[index])
        }

        val exceptionThrown = try {
            ElementGetter.getEveryNthCharacter(textEmpty.byteInputStream(), -1)
            false
        } catch (e: Exception) {
            true
        }
        assertTrue(exceptionThrown)
    }


    @Test
    fun get10thCharacterFromStringTest() {
        val resultEmpty = ElementGetter.get10thCharacter(textEmpty)
        assertTrue(resultEmpty == null)

        val resultNormal = ElementGetter.get10thCharacter(textNormal)
        assertTrue(resultNormal == 'i')

        val resultShort = ElementGetter.get10thCharacter(textShort)
        assertTrue(resultShort == 'J')
    }

    @Test
    fun get10thCharacterFromStreamTest() {
        val exceptionThrown = try {
            ElementGetter.get10thCharacter(textEmpty.byteInputStream())
            false
        } catch (e: Exception) {
            true
        }
        assertTrue(exceptionThrown)

        val resultNormal = ElementGetter.get10thCharacter(textNormal.byteInputStream())
        assertTrue(resultNormal == 'i')

        val resultShort = ElementGetter.get10thCharacter(textShort.byteInputStream())
        assertTrue(resultShort == 'J')
    }

    @Test
    fun getWordOccurrenceCountFromStringTest() {
        val resultEmpty = WordsOccurrenceCounter.getWordsOccurrenceCount(textEmpty)
        assertTrue(resultEmpty.isEmpty())

        val resultShort = WordsOccurrenceCounter.getWordsOccurrenceCount(textShortWords)
        assertTrue(resultShort.size == 27)
        assertTrue(resultShort["a"] == 1)
        assertTrue(resultShort["z"] == 1)
        assertTrue(resultShort["xd"] == null)

        val resultNormal = WordsOccurrenceCounter.getWordsOccurrenceCount(textNormal)
        assertTrue(resultNormal["design"] == 6)
        assertTrue(resultNormal["feedback"] == 1)
        assertTrue(resultNormal["material"] == 6)
        assertTrue(resultNormal["the"] == 9)
        assertTrue(resultNormal["Ahoj"] == null)
    }

    @Test
    fun getWordOccurrenceCountFromStreamTest() {
        val resultEmpty = WordsOccurrenceCounter.getWordsOccurrenceCount(textEmpty.byteInputStream())
        assertTrue(resultEmpty.isEmpty())

        val resultShort = WordsOccurrenceCounter.getWordsOccurrenceCount(textShortWords.byteInputStream())
        assertTrue(resultShort.size == 27)
        assertTrue(resultShort["a"] == 1)
        assertTrue(resultShort["z"] == 1)
        assertTrue(resultShort["xd"] == null)

        val resultNormal = WordsOccurrenceCounter.getWordsOccurrenceCount(textNormal.byteInputStream())
        assertTrue(resultNormal["design"] == 6)
        assertTrue(resultNormal["feedback"] == 1)
        assertTrue(resultNormal["material"] == 6)
        assertTrue(resultNormal["the"] == 9)
        assertTrue(resultNormal["Ahoj"] == null)
    }
}
