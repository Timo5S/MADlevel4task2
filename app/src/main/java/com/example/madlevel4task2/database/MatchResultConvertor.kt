package com.example.madlevel4task2.database

import androidx.room.TypeConverter

class MatchResultConverter {
    @TypeConverter
    fun matchResultToInt(value: MatchResult): Int = value.ordinal

    @TypeConverter
    fun intToMatchResult(value: Int) = value.toEnum<MatchResult>()

    private inline fun <reified T : Enum<T>> Int.toEnum(): T = enumValues<T>()[this]
}