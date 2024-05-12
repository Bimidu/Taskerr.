package com.example.labexam04.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    var item: String?
){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
