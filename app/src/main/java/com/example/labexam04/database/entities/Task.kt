package com.example.labexam04.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.util.Date

@Entity
data class Task(
    var item: String?,
    var itemDescription: String?,
    var itemDate: String?,
    var itemTime: String?
){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
