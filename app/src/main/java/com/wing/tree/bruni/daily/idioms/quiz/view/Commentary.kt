package com.wing.tree.bruni.daily.idioms.quiz.view

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.wing.tree.bruni.daily.idioms.R
import com.wing.tree.bruni.daily.idioms.domain.extension.float
import com.wing.tree.bruni.daily.idioms.domain.extension.half
import com.wing.tree.bruni.daily.idioms.quiz.model.Question
import com.wing.tree.bruni.daily.idioms.quiz.state.CommentaryState
import com.wing.tree.bruni.daily.idioms.quiz.state.QuizState

@Composable
internal fun Commentary(
    modifier: Modifier,
    commentary: QuizState.Commentary,
    onBackPressed: () -> Unit,
    onHomeClick: () -> Unit
) {
    when(commentary) {
        is QuizState.Commentary.Loading -> Unit
        is QuizState.Commentary.Content -> CommentaryContent(
            modifier = modifier,
            content = commentary,
            onBackPressed = onBackPressed,
            onHomeClick = onHomeClick
        )
        is QuizState.Commentary.Error -> Unit
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
private fun CommentaryContent(
    modifier: Modifier,
    content: QuizState.Commentary.Content,
    onBackPressed: () -> Unit,
    onHomeClick: () -> Unit
) {
    val commentaryState = remember(content.currentIndex) {
        content.currentCommentaryState
    }

    Scaffold(
        topBar = {
            TopAppBar(
                content = content,
                onBackPressed = onBackPressed
            )
        },
        modifier = modifier,
        content = { innerPadding ->
            AnimatedContent(
                targetState = commentaryState,
                transitionSpec = {
                    val towards =
                        if (targetState.index > initialState.index) {
                            AnimatedContentScope.SlideDirection.Left
                        } else {
                            AnimatedContentScope.SlideDirection.Right
                        }
                    slideIntoContainer(
                        towards = towards,
                        animationSpec = tween()
                    ) with slideOutOfContainer(
                        towards = towards,
                        animationSpec = tween()
                    )
                }
            ) { targetState ->
                Question(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    question = targetState.question
                )
            }
        },
        bottomBar = {
            BottomBar(
                modifier = Modifier.fillMaxWidth(),
                commentaryState = commentaryState,
                onPreviousClick = { content.currentIndex-- },
                onNextClick = { content.currentIndex++ },
                onHomeClick = onHomeClick
            )
        }
    )
}

@Composable
private fun TopAppBar(
    content: QuizState.Commentary.Content,
    onBackPressed: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val progress by animateFloatAsState(
            targetValue = content.currentIndex.inc() / content.count.float,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        )

        LinearProgressIndicator(progress = progress)
    }
}

@Composable
private fun Question(
    modifier: Modifier,
    question: Question
) {
    Column(modifier = modifier) {
        Text(text = question.text)

        question.options.forEachIndexed { index, option ->
            Option(
                modifier = Modifier,
                option = option,
                selected = question.answer == index
            )
        }
    }
}

@Composable
private fun Option(
    modifier: Modifier,
    option: String,
    selected: Boolean
) {
    val color = if (selected) Color.Blue else Color.Gray

    Surface(
        modifier = modifier,
        color = color
    ) {
        Text(text = option)
    }
}

@Composable
private fun BottomBar(
    modifier: Modifier,
    commentaryState: CommentaryState,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onHomeClick: () -> Unit
) {
    Surface(modifier = modifier) {
        val localDensity = LocalDensity.current
        var size by remember { mutableStateOf(IntSize.Zero) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged { size = it }
        ) {
            val width = with(localDensity) { size.width.half.toDp() }

            AnimatedVisibility(visible = commentaryState.isPreviousVisible) {
                Row(modifier = Modifier.width(width)) {
                    OutlinedButton(
                        modifier = Modifier.weight(1.0F).height(48.dp),
                        onClick = onPreviousClick
                    ) {
                        Text(text = stringResource(id = R.string.previous))
                    }
                }
            }

            if (commentaryState.isHomeVisible) {
                Button(
                    modifier = Modifier
                        .weight(1.0F)
                        .height(48.dp),
                    onClick = onHomeClick
                ) {
                    Text(text = stringResource(id = R.string.home))
                }
            } else {
                Button(
                    modifier = Modifier
                        .weight(1.0F)
                        .height(48.dp),
                    onClick = onNextClick
                ) {
                    Text(text = stringResource(id = R.string.next))
                }
            }
        }
    }
}