package com.wing.tree.bruni.daily.idioms.data.di

import com.wing.tree.bruni.daily.idioms.data.datasource.IdiomDataSource
import com.wing.tree.bruni.daily.idioms.data.datasource.IdiomDataSourceImpl
import com.wing.tree.bruni.daily.idioms.data.datasource.QuestionDataSource
import com.wing.tree.bruni.daily.idioms.data.datasource.QuestionDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindsIdiomDataSource(dataSource: IdiomDataSourceImpl): IdiomDataSource

    @Binds
    @Singleton
    abstract fun bindsQuizDataSource(dataSource: QuestionDataSourceImpl): QuestionDataSource
}
