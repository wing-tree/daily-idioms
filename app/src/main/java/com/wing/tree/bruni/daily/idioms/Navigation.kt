package com.wing.tree.bruni.daily.idioms

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

internal fun Fragment.popBackStack() = findNavController().popBackStack()