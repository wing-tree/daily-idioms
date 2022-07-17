package com.wing.tree.bruni.daily.idioms.quiz.delegate

import com.wing.tree.bruni.daily.idioms.domain.model.Idiom
import com.wing.tree.bruni.daily.idioms.domain.model.Question

interface QuestionGenerator {
    suspend fun generateQuestions(idioms: List<Idiom>, questionCount: Int): List<Question>
}