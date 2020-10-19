package com.example.madlevel4task2.model

import androidx.room.*

@Entity(tableName = "gamesTable")
data class Game(
    var computerChoice: String,
    var personChoice: String,
    var gameDate: String,
    var gameResult: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
)