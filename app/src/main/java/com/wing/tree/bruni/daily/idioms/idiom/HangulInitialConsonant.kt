package com.wing.tree.bruni.daily.idioms.idiom

object HangulInitialConsonant {
    private const val HANGUL_SYLLABLES_START = 44032
    private const val HANGUL_SYLLABLES_END = 55203

    private const val LETTER_COUNT_PER_HANGUL_INITIAL_CONSONANT = 588

    private val hangulConsonants = charArrayOf(
        'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ',
        'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ',
        'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ',
        'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ',
        'ㅌ', 'ㅍ', 'ㅎ'
    )

    private val Int.char: Char get() = toChar()
    private val String.code: IntArray get() = IntArray(length) { get(it).code }

    val Char.isHangulConsonant: Boolean get() = hangulConsonants.contains(this)

    val String.hangulInitialConsonant: String get() = buildString {
        code.forEach {
            if (it in HANGUL_SYLLABLES_START..HANGUL_SYLLABLES_END) {
                val index = it
                    .minus(HANGUL_SYLLABLES_START)
                    .div(LETTER_COUNT_PER_HANGUL_INITIAL_CONSONANT)

                append(hangulConsonants[index])
            } else {
                append(it.char)
            }
        }
    }
}