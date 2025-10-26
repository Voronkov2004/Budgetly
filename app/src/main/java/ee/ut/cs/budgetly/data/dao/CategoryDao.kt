package ee.ut.cs.budgetly.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ee.ut.cs.budgetly.data.entity.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert suspend fun insert(category: Category): Long
    @Query("SELECT * FROM category") fun getAll(): Flow<List<Category>>

    @Query("SELECT * FROM category")
    suspend fun getAllOnce(): List<Category>
}