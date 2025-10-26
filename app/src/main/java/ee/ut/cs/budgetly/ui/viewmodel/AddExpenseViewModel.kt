package ee.ut.cs.budgetly.ui.viewmodel

import android.app.Application
import android.os.Looper
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import ee.ut.cs.budgetly.BuildConfig
import ee.ut.cs.budgetly.data.database.AppDatabase
import ee.ut.cs.budgetly.data.entity.Category
import ee.ut.cs.budgetly.data.entity.Expense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.util.logging.Handler

class AddExpenseViewModel(application: Application) : AndroidViewModel(application)  {
    private val expenseDao = AppDatabase.getInstance(application).expenseDao()
    private val categoryDao = AppDatabase.getInstance(application).categoryDao()
    private val client = OkHttpClient()
    private val apiKey = BuildConfig.OPENAI_API_KEY
    val categories: StateFlow<List<Category>> = categoryDao.getAll()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addExpense(name: String, amount: Double, categoryId: Int, date: Long, note: String?) {

        viewModelScope.launch(Dispatchers.IO) {
            expenseDao.insert(
                Expense(
                    name = name,
                    amount = amount,
                    categoryId = categoryId,
                    date = date,
                    note = note
                )
            )
        }
    }

    fun fetchCategoryFromApi(name: String, onResult: (String?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val categoryList = categoryDao.getAllOnce()
            val categoryNames = categoryList.map { it.name }
            Log.d("AddExpenseViewModel", "CATEGORY NAMES: $categoryNames")
            val requestBody = """
        {
            "model": "gpt-4o-mini",
            "messages": [
                {"role": "system", "content": "You are a financial assistant that classifies expenses into categories"},
                {"role": "user", "content": "Classify the expense '$name' into one of the following category: ${categoryNames.joinToString (", ")}. Return only the category name exactly as in this list: ${categoryNames.joinToString (", ")}"}
            ]
        }
        """.trimIndent()

            val request = Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .addHeader("Authorization", "Bearer $apiKey")
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create("application/json".toMediaTypeOrNull(), requestBody))
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    android.os.Handler(Looper.getMainLooper()).post {
                        onResult(null)
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val category = if (!response.isSuccessful) {
                        null
                    } else {
                        val json = JSONObject(response.body!!.string())
                        json.getJSONArray("choices")
                            .getJSONObject(0)
                            .getJSONObject("message")
                            .getString("content")
                            .trim()
                    }

                    android.os.Handler(Looper.getMainLooper()).post {
                        Log.d("AddExpenseViewModel", "Fetched category: $category")
                        Log.d("AddExpenseViewModel", "Sent message: $requestBody")
                        Log.d("AddExpenseViewModel", "KEYKEY: $apiKey")
                        onResult(category)
                    }
                }
            })
        }
    }


}