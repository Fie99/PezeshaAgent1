package com.pezesha.agent.di

import android.app.Application
import com.pezesha.agent.utils.FormValidator
import com.pezesha.agent.utils.FormValidatorImpl
import com.pezesha.agent.utils.GlobalMethods
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {

    @Singleton
    @Provides
    fun provideGlobalMethods(app: Application): GlobalMethods {
        return GlobalMethods(app)
    }

    @Singleton
    @Provides
    fun provideFormValidator(): FormValidator {
        return FormValidatorImpl()
    }
}