package com.wing.tree.bruni.daily.idioms.idiom.view

import androidx.compose.animation.VectorConverter
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wing.tree.bruni.daily.idioms.R
import com.wing.tree.bruni.daily.idioms.constant.NEWLINE
import com.wing.tree.bruni.daily.idioms.domain.model.Idiom
import com.wing.tree.bruni.daily.idioms.idiom.HangulInitialConsonant.hangulInitialConsonant
import com.wing.tree.bruni.daily.idioms.idiom.HangulInitialConsonant.isHangulConsonant
import com.wing.tree.bruni.daily.idioms.idiom.state.IdiomState

@Composable
internal fun Idioms(
    modifier: Modifier,
    state: IdiomState,
    queryText: String,
    onIconButtonClick: (Idiom) -> Unit
) {
    when(state) {
        is IdiomState.Loading -> Unit
        is IdiomState.Content -> {
            IdiomContent(
                modifier = modifier,
                idioms = filter(state.idioms, queryText),
                onIconButtonClick = onIconButtonClick
            )
        }
        is IdiomState.Error -> Unit
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun IdiomContent(
    modifier: Modifier,
    idioms: List<Idiom>,
    onIconButtonClick: (Idiom) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(idioms) { idiom ->
            Idiom(
                modifier = Modifier.animateItemPlacement(),
                idiom = idiom,
                onIconButtonClick = onIconButtonClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Idiom(
    modifier: Modifier,
    idiom: Idiom,
    onIconButtonClick: (Idiom) -> Unit
) {
    val chineseMeaningsText = buildString {
        with(idiom) {
            val lastIndex = chineseMeanings.lastIndex

            chineseMeanings.forEachIndexed { index, chineseMeaning ->
                append("${chineseCharacters[index]} : $chineseMeaning")

                if (index < lastIndex) {
                    append(NEWLINE)
                }
            }
        }
    }

    val tint = remember {
        val initialValue = if (idiom.my) {
            Color.Cyan
        } else {
            Color.Gray
        }

        Animatable(
            initialValue,
            Color.VectorConverter(ColorSpaces.LinearSrgb)
        )
    }

    LaunchedEffect(idiom.my) {
        val targetValue = if (idiom.my) {
            Color.Cyan
        } else {
            Color.Gray
        }

        tint.animateTo(targetValue, tween(200))
    }

    Card(
        modifier = modifier,
        onClick = {  }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier) {
                Text(text = idiom.koreanCharacters)
                Text(text = idiom.chineseCharacters)
            }

            IconButton(
                onClick = {
                    onIconButtonClick(idiom)
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_round_star_24),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = tint.value
                )
            }
        }

        Text(text = idiom.description)
        Text(text = chineseMeaningsText)
    }
}

private fun filter(idioms: List<Idiom>, queryText: String) = when {
    queryText.isNotBlank() -> {
        if (queryText.all { it.isHangulConsonant }) {
            idioms.filter {
                it.koreanCharacters
                    .hangulInitialConsonant
                    .contains(queryText.hangulInitialConsonant)
            }.sortedBy {
                it.koreanCharacters
                    .hangulInitialConsonant
                    .indexOf(queryText.hangulInitialConsonant)
            }
        } else {
            idioms.filter {
                it.koreanCharacters.contains(queryText)
            }.sortedBy {
                it.koreanCharacters.indexOf(queryText)
            }
        }
    }
    else -> idioms
}