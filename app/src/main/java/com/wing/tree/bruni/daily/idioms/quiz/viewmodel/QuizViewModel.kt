package com.wing.tree.bruni.daily.idioms.quiz.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wing.tree.bruni.daily.idioms.constant.Category
import com.wing.tree.bruni.daily.idioms.constant.Key
import com.wing.tree.bruni.daily.idioms.constant.OPTION_COUNT
import com.wing.tree.bruni.daily.idioms.constant.ZERO
import com.wing.tree.bruni.daily.idioms.domain.extension.notZero
import com.wing.tree.bruni.daily.idioms.domain.model.Idiom
import com.wing.tree.bruni.daily.idioms.domain.repository.IdiomRepository
import com.wing.tree.bruni.daily.idioms.domain.repository.QuestionRepository
import com.wing.tree.bruni.daily.idioms.quiz.delegate.QuestionGenerator
import com.wing.tree.bruni.daily.idioms.quiz.delegate.QuestionGeneratorImpl
import com.wing.tree.bruni.daily.idioms.quiz.model.Question
import com.wing.tree.bruni.daily.idioms.quiz.state.QuestionState
import com.wing.tree.bruni.daily.idioms.quiz.state.QuizState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.*
import javax.inject.Inject
import com.wing.tree.bruni.daily.idioms.data.entity.Question as Entity
import com.wing.tree.bruni.daily.idioms.domain.model.Question as DomainModel

@HiltViewModel
class QuizViewModel @Inject constructor(
    idiomRepository: IdiomRepository,
    private val questionRepository: QuestionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(), QuestionGenerator by QuestionGeneratorImpl() {
    private val ioDispatcher = Dispatchers.IO

    private val category = savedStateHandle.get<Category>(Key.CATEGORY)
    private val questionCount = savedStateHandle.get<Int>(Key.QUESTION_COUNT) ?: ZERO

    private val idioms = when(category) {
        Category.All -> idiomRepository.allIdioms()
        Category.CivilServiceExamination -> idiomRepository.civilServiceExaminationIdioms()
        Category.My -> idiomRepository.myIdioms()
        Category.Sat -> idiomRepository.satIdioms()
        else -> throw IllegalArgumentException("category :$category")
    }

    private val progress = flow {
        try {
            withContext(ioDispatcher) {
                idioms.firstOrNull()?.let { idioms ->
                    questionRepository.deleteAndInsert(generateQuestions(idioms, questionCount))
                }
            }

            val questionsState = with(questionRepository.allQuestions()) {
                map {
                    val question = Question.from(it)
                    val isPreviousVisible = it.index.notZero
                    val isDoneVisible = it.index == lastIndex

                    QuestionState(
                        index = question.index,
                        question = question,
                        isPreviousVisible = isPreviousVisible,
                        isDoneVisible = isDoneVisible
                    )
                }
            }

            emit(QuizState.Progress.Content(questionsState))
        } catch (ioException: IOException) {
            emit(QuizState.Progress.Error(ioException))
        }
    }

    private val result = MutableStateFlow<QuizState.Result>(QuizState.Result.Loading)

    val state: StateFlow<QuizState> = combine(progress, result) { progress, result ->
        when {
            result is QuizState.Result.Content -> result
            result is QuizState.Result.Error -> result
            progress is QuizState.Progress.Content -> progress
            progress is QuizState.Progress.Error -> progress
            else -> QuizState.Progress.Loading
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = QuizState.Progress.Loading
    )

    fun score(content: QuizState.Progress.Content) {
        val questionsState = content.questionsState.map {
            val question = it.question.copy(answer = it.answer)

            it.copy(question = question)
        }

        val score = content.count

        result.value = QuizState.Result.Content(questionsState)
    }

    companion object {
        private const val PERFECT_SCORE = 100
    }
}