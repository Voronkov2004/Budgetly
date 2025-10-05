package ee.ut.cs.budgetly.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ee.ut.cs.budgetly.data.dao.CategoryDao
import ee.ut.cs.budgetly.data.dao.ExpenseDao
import ee.ut.cs.budgetly.data.entity.Category
import ee.ut.cs.budgetly.data.entity.Expense

@Database(entities = [Expense::class, Category::class], version = 10)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        private const val Database_NAME = "budgetly.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        Database_NAME
                    ).createFromAsset("budgetly.db")
                        //.fallbackToDestructiveMigration(true)
                    .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}