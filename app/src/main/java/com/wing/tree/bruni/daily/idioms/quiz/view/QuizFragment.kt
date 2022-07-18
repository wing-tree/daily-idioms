package com.wing.tree.bruni.daily.idioms.quiz.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wing.tree.bruni.daily.idioms.popBackStack
import com.wing.tree.bruni.daily.idioms.quiz.state.QuizState
import com.wing.tree.bruni.daily.idioms.quiz.viewmodel.QuizViewModel
import com.wing.tree.bruni.daily.idioms.ui.theme.DailyIdiomsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : Fragment() {
    private val viewModel by viewModels<QuizViewModel>()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireActivity()).apply {
            setContent {
                val state by viewModel.state.collectAsState()

                DailyIdiomsTheme {
                    AnimatedContent(
                        targetState = state,
                        transitionSpec = {
                            val towards =  AnimatedContentScope.SlideDirection.Left

                            when(state) {
                                is QuizState.Result ->  slideIntoContainer(
                                    towards = towards,
                                    animationSpec = tween()
                                ) with slideOutOfContainer(
                                    towards = towards,
                                    animationSpec = tween()
                                )
                                is QuizState.Progress -> EnterTransition.None with ExitTransition.None
                            }
                        }
                    ) { targetState ->
                        Quiz(
                            modifier = Modifier.fillMaxSize(),
                            state = targetState,
                            onDoneClick = { viewModel.score(it) },
                            onBackPressed = { popBackStack() }
                        )
                    }
                }
            }
        }
    }
}

