package com.wing.tree.bruni.daily.idioms.quiz.delegate

import com.wing.tree.bruni.daily.idioms.constant.OPTION_COUNT
import com.wing.tree.bruni.daily.idioms.constant.ZERO
import com.wing.tree.bruni.daily.idioms.data.entity.Question
import com.wing.tree.bruni.daily.idioms.domain.model.Idiom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class QuestionGeneratorImpl : QuestionGenerator {
    private val defaultDispatcher = Dispatchers.Default

    override suspend fun generateQuestions(idioms: List<Idiom>, questionCount: Int) = withContext(defaultDispatcher) {
        require(questionCount > ZERO)

        val questions = mutableListOf<Question>()

        idioms.shuffled().take(questionCount).forEachIndexed { index, idiom ->
            val options = idioms
                .shuffled()
                .filterNot { it == idiom }
                .take(OPTION_COUNT.dec())
                .map { "${it.koreanCharacters} ${it.chineseCharacters}" }
                .toMutableList()

            val correctAnswer = Random().nextInt(OPTION_COUNT)

            options.add(correctAnswer, "${idiom.koreanCharacters} ${idiom.chineseCharacters}")

            val question = Question(
                index = index,
                answer = null,
                correctAnswer = correctAnswer,
                isSolved = false,
                options = options,
                text = idiom.description
            )

            questions.add(question)
        }

        questions
    }
}