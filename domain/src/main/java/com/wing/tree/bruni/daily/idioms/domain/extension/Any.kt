package com.wing.tree.bruni.daily.idioms.domain.extension

val Any?.isNull: Boolean get() = this == null
val Any?.notNull: Boolean get() = isNull.not()