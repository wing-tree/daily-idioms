package com.wing.tree.bruni.daily.idioms.quiz.view

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.wing.tree.bruni.daily.idioms.R
import com.wing.tree.bruni.daily.idioms.constant.NEWLINE
import com.wing.tree.bruni.daily.idioms.domain.extension.float
import com.wing.tree.bruni.daily.idioms.domain.extension.half
import com.wing.tree.bruni.daily.idioms.domain.model.Idiom
import com.wing.tree.bruni.daily.idioms.domain.model.Option
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
            ExpandableCard(
                option,
                { option.expanded = option.expanded.not() },
                option.expanded
            )
//            Option(
//                modifier = Modifier,
//                option = option,
//                selected = question.answer == index
//            )
        }
    }
}

@Composable
private fun Option(
    modifier: Modifier,
    option: Idiom,
    selected: Boolean
) {
    val color = if (selected) Color.Blue else Color.Gray
    val chineseMeaningsText = buildString {
        with(option) {
            val lastIndex = chineseMeanings.lastIndex

            chineseMeanings.forEachIndexed { index, chineseMeaning ->
                append("${chineseCharacters[index]}: $chineseMeaning")

                if (index < lastIndex) {
                    append(NEWLINE)
                }
            }
        }
    }

    Surface(
        modifier = modifier,
        color = color
    ) {
        Column {
            Text(text = "${option.koreanCharacters} (${option.chineseCharacters})")
            Text(text = option.description)
        }
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
                        modifier = Modifier
                            .weight(1.0F)
                            .height(48.dp),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableCard(
    option: Option,
    onCardArrowClick: () -> Unit,
    expanded: Boolean,
) {
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = expanded.not()
        }
    }

    val transition = updateTransition(transitionState, label = null)

    val cardBgColor by transition.animateColor({
        tween(durationMillis = 300)
    }, label = "bgColorTransition") {
        if (expanded) Color.Yellow else Color.Green
    }
    val cardPaddingHorizontal by transition.animateDp({
        tween(durationMillis = 300)
    }, label = "paddingTransition") {
        if (expanded) 48.dp else 24.dp
    }
    val cardElevation by transition.animateDp({
        tween(durationMillis = 300)
    }, label = "elevationTransition") {
        if (expanded) 24.dp else 4.dp
    }
    val cardRoundedCorners by transition.animateDp({
        tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        )
    }, label = "cornersTransition") {
        if (expanded) 0.dp else 16.dp
    }
    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = 300)
    }, label = "rotationDegreeTransition") {
        if (expanded) 0f else 180f
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = cardBgColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = cardElevation
        ),
        shape = RoundedCornerShape(cardRoundedCorners),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = cardPaddingHorizontal,
                vertical = 8.dp
            )
    ) {
        Column {
            Box {
                CardArrow(
                    degrees = arrowRotationDegree,
                    onClick = onCardArrowClick
                )
                CardTitle(title = option.koreanCharacters)
            }
            ExpandableContent(visible = expanded)
        }
    }
}

@Composable
fun CardArrow(
    degrees: Float,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        content = {
            Icon(
                imageVector = Icons.Rounded.ArrowDropDown,
                contentDescription = "Expandable Arrow",
                modifier = Modifier.rotate(degrees),
            )
        }
    )
}

@Composable
fun CardTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        textAlign = TextAlign.Center,
    )

}

@Composable
fun ExpandableContent(
    visible: Boolean = true,
) {
    val enterFadeIn = remember {
        fadeIn(
            animationSpec = TweenSpec(
                easing = FastOutLinearInEasing
            )
        )
    }
    val enterExpand = remember {
        expandVertically(animationSpec = tween())
    }
    val exitFadeOut = remember {
        fadeOut(
            animationSpec = TweenSpec(
                easing = LinearOutSlowInEasing
            )
        )
    }
    val exitCollapse = remember {
        shrinkVertically(animationSpec = tween())
    }
    AnimatedVisibility(
        visible = visible,
        enter = enterExpand + enterFadeIn,
        exit = exitCollapse + exitFadeOut
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Spacer(modifier = Modifier.heightIn(100.dp))
            Text(
                text = "Expandable content here",
                textAlign = TextAlign.Center
            )
        }
    }
}