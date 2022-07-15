package com.wing.tree.bruni.daily.idioms.quiz.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wing.tree.bruni.daily.idioms.constant.Key
import com.wing.tree.bruni.daily.idioms.constant.OPTION_COUNT
import com.wing.tree.bruni.daily.idioms.constant.ZERO
import com.wing.tree.bruni.daily.idioms.domain.extension.notZero
import com.wing.tree.bruni.daily.idioms.domain.model.Idiom
import com.wing.tree.bruni.daily.idioms.domain.repository.IdiomRepository
import com.wing.tree.bruni.daily.idioms.domain.repository.QuestionRepository
import com.wing.tree.bruni.daily.idioms.enum.Category
import com.wing.tree.bruni.daily.idioms.quiz.model.Question
import com.wing.tree.bruni.daily.idioms.quiz.state.QuestionState
import com.wing.tree.bruni.daily.idioms.quiz.state.QuizState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import com.wing.tree.bruni.daily.idioms.data.entity.Question as Entity
import com.wing.tree.bruni.daily.idioms.domain.model.Question as DomainModel

@HiltViewModel
class QuizViewModel @Inject constructor(
    idiomRepository: IdiomRepository,
    private val questionRepository: QuestionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val defaultDispatcher = Dispatchers.Default
    private val ioDispatcher = Dispatchers.IO

    private val category = savedStateHandle.get<Category>(Key.CATEGORY)
    private val count = savedStateHandle.get<Int>(Key.COUNT) ?: ZERO

    private val idioms = when(category) {
        Category.All -> idiomRepository.allIdioms()
        Category.CivilServiceExamination -> idiomRepository.civilServiceExaminationIdioms()
        Category.My -> idiomRepository.myIdioms()
        Category.Sat -> idiomRepository.satIdioms()
        else -> throw IllegalArgumentException("category :$category")
    }

    val state: StateFlow<QuizState> = flow {
        withContext(ioDispatcher) {
            idioms.firstOrNull()?.let { idioms ->
                questionRepository.deleteAndInsert(generateQuestions(idioms))
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
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = QuizState.Progress.Loading
    )

    private suspend fun generateQuestions(idioms: List<Idiom>): List<DomainModel> =
        withContext(defaultDispatcher) {
            require(count > ZERO)

            val entities = mutableListOf<Entity>()

            idioms.shuffled().take(count).forEachIndexed { index, idiom ->
                val options = idioms
                    .shuffled()
                    .filterNot { it == idiom }
                    .take(OPTION_COUNT.dec())
                    .map { "${it.koreanCharacters} ${it.chineseCharacters}" }
                    .toMutableList()

                val correctAnswer = Random().nextInt(OPTION_COUNT)

                options.add(correctAnswer, "${idiom.koreanCharacters} ${idiom.chineseCharacters}")

                val entity = Entity(
                    index = index,
                    answer = null,
                    correctAnswer = correctAnswer,
                    isSolved = false,
                    options = options,
                    text = idiom.description
                )

                entities.add(entity)
            }

            entities.also {
                withContext(ioDispatcher) {
                    questionRepository.deleteAndInsert(it)
                }
            }
        }
}