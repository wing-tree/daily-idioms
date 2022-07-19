package com.wing.tree.bruni.daily.idioms.quiz.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wing.tree.bruni.daily.idioms.R
import com.wing.tree.bruni.daily.idioms.quiz.state.QuizState

@Composable
internal fun Result(
    modifier: Modifier,
    result: QuizState.Result,
    onHomeClick: () -> Unit,
    onCommentaryClick: (QuizState.Result.Content) -> Unit
) {
    when(result) {
        is QuizState.Result.Loading -> Unit
        is QuizState.Result.Content -> ResultContent(
            modifier = modifier,
            content = result,
            onHomeClick = onHomeClick,
            onCommentaryClick = onCommentaryClick
        )
        is QuizState.Result.Error -> Unit
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ResultContent(
    modifier: Modifier,
    content: QuizState.Result.Content,
    onHomeClick: () -> Unit,
    onCommentaryClick: (QuizState.Result.Content) -> Unit
) {
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = "${content.score}")
                }
            )
        },
        modifier = modifier,
        content = { innerPadding ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(content.resultsState) { resultState ->
                        Text(text = "${resultState.answer}")
                    }
                }
            }
        },
        bottomBar = {
            BottomBar(
                modifier = Modifier.fillMaxWidth(),
                onHomeClick = onHomeClick,
                onCommentaryClick = { onCommentaryClick(content) }
            )
        }
    )
}

@Composable
private fun BottomBar(
    modifier: Modifier,
    onHomeClick: () -> Unit,
    onCommentaryClick: () -> Unit
) {
    Surface(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                modifier = Modifier
                    .weight(1.0F)
                    .height(48.dp),
                onClick = onHomeClick
            ) {
                Text(text = stringResource(id = R.string.home))
            }

            Button(
                modifier = Modifier
                    .weight(1.0F)
                    .height(48.dp),
                onClick = onCommentaryClick
            ) {
                Text(text = stringResource(id = R.string.commentary))
            }
        }
    }
}