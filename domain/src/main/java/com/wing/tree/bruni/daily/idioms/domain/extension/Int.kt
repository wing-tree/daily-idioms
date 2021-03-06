package com.wing.tree.bruni.daily.idioms.domain.extension

import com.wing.tree.bruni.daily.idioms.domain.constant.TWO
import com.wing.tree.bruni.daily.idioms.domain.constant.ZERO

val Int.isZero: Boolean get() = this == ZERO
val Int.notZero: Boolean get() = isZero.not()

val Int.half: Int get() = div(TWO)

val Int.char: Char get() = toChar()
val Int.float: Float get() = toFloat()