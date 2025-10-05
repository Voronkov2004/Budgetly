package ee.ut.cs.budgetly.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category (

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val color: String? = null
)