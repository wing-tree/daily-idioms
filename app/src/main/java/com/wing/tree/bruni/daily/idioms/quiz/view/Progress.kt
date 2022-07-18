package com.wing.tree.bruni.daily.idioms.quiz.view

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
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
import com.wing.tree.bruni.daily.idioms.domain.model.Idiom
import com.wing.tree.bruni.daily.idioms.quiz.model.Question
import com.wing.tree.bruni.daily.idioms.quiz.state.QuestionState
import com.wing.tree.bruni.daily.idioms.quiz.state.QuizState

@Composable
internal fun Progress(
    modifier: Modifier,
    progress: QuizState.Progress,
    onBackPressed: () -> Unit,
    onDoneClick: (QuizState.Progress.Content) -> Unit
) {
    when(progress) {
        is QuizState.Progress.Loading -> Unit
        is QuizState.Progress.Content -> ProgressContent(
            modifier = modifier,
            content = progress,
            onBackPressed = onBackPressed,
            onDoneClick = { onDoneClick(progress) }
        )
        is QuizState.Progress.Error -> Unit
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
private fun ProgressContent(
    modifier: Modifier,
    content: QuizState.Progress.Content,
    onBackPressed: () -> Unit,
    onDoneClick: () -> Unit
) {
    val questionState = remember(content.currentIndex) {
        content.currentQuestionState
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
                targetState = questionState,
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
                    question = targetState.question,
                    answer = targetState.answer,
                    onOptionSelected = { which ->
                        targetState.answer = which
                    }
                )
            }
        },
        bottomBar = {
            BottomBar(
                modifier = Modifier.fillMaxWidth(),
                questionState = questionState,
                onPreviousClick = { content.currentIndex-- },
                onNextClick = { content.currentIndex++ },
                onDoneClick = onDoneClick
            )
        }
    )
}

@Composable
private fun TopAppBar(
    content: QuizState.Progress.Content,
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
    question: Question,
    answer: Int?,
    onOptionSelected: (Int) -> Unit
) {
    Column(modifier = modifier) {
        Text(text = question.text)

        question.options.forEachIndexed { index, option ->
            Option(
                modifier = Modifier,
                index = index,
                option = option,
                selected = answer == index
            ) {
                onOptionSelected(index)
            }
        }
    }
}

@Composable
private fun Option(
    modifier: Modifier,
    index: Int,
    option: Idiom,
    selected: Boolean,
    onClick: (Int) -> Unit
) {
    val color = if (selected) Color.Blue else Color.Gray

    Surface(
        modifier = modifier.clickable { onClick(index) },
        color = color
    ) {
        Text(text = "${option.koreanCharacters} ${option.chineseCharacters}")
    }
}

@Composable
private fun BottomBar(
    modifier: Modifier,
    questionState: QuestionState,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onDoneClick: () -> Unit
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

            AnimatedVisibility(visible = questionState.isPreviousVisible) {
                Row(modifier = Modifier.width(width)) {
                    OutlinedButton(
                        modifier = Modifier.weight(1.0F).height(48.dp),
                        onClick = onPreviousClick
                    ) {
                        Text(text = stringResource(id = R.string.previous))
                    }
                }
            }

            if (questionState.isDoneVisible) {
                Button(
                    modifier = Modifier
                        .weight(1.0F)
                        .height(48.dp),
                    onClick = onDoneClick,
                    enabled = questionState.isNextEnabled
                ) {
                    Text(text = stringResource(id = R.string.done))
                }
            } else {
                Button(
                    modifier = Modifier
                        .weight(1.0F)
                        .height(48.dp),
                    onClick = onNextClick,
                    enabled = questionState.isNextEnabled
                ) {
                    Text(text = stringResource(id = R.string.next))
                }
            }
        }
    }
}
