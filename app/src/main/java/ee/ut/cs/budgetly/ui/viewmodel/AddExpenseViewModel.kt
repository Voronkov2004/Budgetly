package ee.ut.cs.budgetly.ui.viewmodel

import android.Manifest
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ee.ut.cs.budgetly.BuildConfig
import ee.ut.cs.budgetly.data.database.AppDatabase
import ee.ut.cs.budgetly.data.entity.Category
import ee.ut.cs.budgetly.data.entity.Expense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException

class AddExpenseViewModel(application: Application) : AndroidViewModel(application)  {
    private val expenseDao = AppDatabase.getInstance(application).expenseDao()
    private val categoryDao = AppDatabase.getInstance(application).categoryDao()
    private val client = OkHttpClient()
    private val apiKey = BuildConfig.OPENAI_API_KEY
    val categories: StateFlow<List<Category>> = categoryDao.getAll()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addExpense(name: String, amount: Double, categoryId: Int, date: Long, note: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            expenseDao.insert(Expense( name = name, amount = amount, categoryId = categoryId, date = date, note = note ))
        }
    }

    fun fetchCategoryFromApi(name: String, onResult: (String?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) @androidx.annotation.RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE) {
            if (apiKey.isNullOrBlank()) {
                Log.e("AddExpenseViewModel", "OPENAI_API_KEY is missing")
                postMain { onResult(null) }
                return@launch
            }
            if (!isOnline()) {
                Log.w("AddExpenseViewModel", "No internet connection")
                postMain { onResult(null) }
                return@launch
            }

            val categoryList = categoryDao.getAllOnce()
            val categoryNames = categoryList.map { it.name }
            val requestBody = """
                {
                  "model": "gpt-4o-mini",
                  "messages": [
                    {"role": "system", "content": "You are a financial assistant that classifies expenses into categories"},
                    {"role": "user", "content": "Classify the expense '$name' into one of the following category: ${categoryNames.joinToString(", ")}. Return only the category name exactly as in this list: ${categoryNames.joinToString(", ")}"}
                  ],
                  "temperature": 0
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
                    Log.e("AddExpenseViewModel", "Network call failed: ${e.message}")
                    postMain { onResult(null) }
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        val category = try {
                            if (!response.isSuccessful) null
                            else {
                                val body = response.body?.string().orEmpty()
                                val json = JSONObject(body)
                                json.getJSONArray("choices")
                                    .getJSONObject(0)
                                    .getJSONObject("message")
                                    .getString("content")
                                    .trim()
                            }
                        } catch (t: Throwable) {
                            Log.e("AddExpenseViewModel", "Parse error: ${t.message}")
                            null
                        }

                        postMain {
                            Log.d("AddExpenseViewModel", "Fetched category: $category")
                            onResult(category)
                        }
                    }
                }
            })
        }
    }

    private fun postMain(block: () -> Unit) =
        android.os.Handler(Looper.getMainLooper()).post(block)

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private fun isOnline(): Boolean {
        val cm = getApplication<Application>()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val net = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(net) ?: return false
        return caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }
}