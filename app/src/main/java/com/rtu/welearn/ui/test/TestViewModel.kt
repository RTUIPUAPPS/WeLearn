package com.rtu.welearn.ui.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rtu.welearn.data.test_data_source.TestDataSource
import kotlinx.coroutines.launch

class TestViewModel(
    private val testDataSource: TestDataSource
) : ViewModel() {

    val testQuestions = testDataSource.getAllQuestions()

    fun insertQuestionsToDB() {
        viewModelScope.launch {
            testDataSource.insertQuestion(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
            )
        }
    }

    inline fun <T> safeCall(action: () -> Resource<T>): Resource<T> {
        return try {
            action()
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown Error Occurred")
        }
    }

    sealed class Resource<T>(val data: T? = null, val message: String? = null) {
        class Success<T>(data: T) : Resource<T>(data)
        class Loading<T>(data: T? = null) : Resource<T>(data)
        class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    }
}