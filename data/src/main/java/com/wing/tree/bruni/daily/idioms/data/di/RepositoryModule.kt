package com.wing.tree.bruni.daily.idioms.data.di

import com.wing.tree.bruni.daily.idioms.data.repository.IdiomRepositoryImpl
import com.wing.tree.bruni.daily.idioms.data.repository.QuestionRepositoryImpl
import com.wing.tree.bruni.daily.idioms.domain.repository.IdiomRepository
import com.wing.tree.bruni.daily.idioms.domain.repository.QuestionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsIdiomRepository(repository: IdiomRepositoryImpl): IdiomRepository

    @Binds
    @Singleton
    abstract fun bindsQuizRepository(repository: QuestionRepositoryImpl): QuestionRepository
}