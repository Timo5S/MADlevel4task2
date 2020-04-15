package com.example.madlevel4task2.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "game_table")
data class Game(
    @ColumnInfo(name = "date")
    var date: Date,

    @ColumnInfo(name = "playerMove")
    var playerMove: Move,

    @ColumnInfo(name = "botMove")
    var botMove: Move,

    @ColumnInfo(name = "matchResult")
    var matchResult: MatchResult? = null,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
) : Parcelable