package com.example.madlevel4task2.database

import androidx.room.TypeConverter

class MoveConverter {
    @TypeConverter
    fun moveToInt(value: Move): Int = value.ordinal

    @TypeConverter
    fun intToMove(value: Int) = value.toEnum<Move>()

    private inline fun <reified T : Enum<T>> Int.toEnum(): T = enumValues<T>()[this]
}