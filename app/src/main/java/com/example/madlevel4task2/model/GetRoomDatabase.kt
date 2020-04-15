package com.example.madlevel4task2.model

import android.content.Context
import androidx.room.*
import com.example.madlevel4task2.database.DateConverter
import com.example.madlevel4task2.database.Game
import com.example.madlevel4task2.database.MatchResultConverter
import com.example.madlevel4task2.database.MoveConverter


@Database(entities = [Game::class], version = 1, exportSchema = false)

@TypeConverters(DateConverter::class, MoveConverter::class, MatchResultConverter::class)

abstract class GameRoomDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
    companion object {
        private const val DATABASE_NAME = "RPS_GAME_DATABASE"
        @Volatile
        private var gameRoomDatabaseInstance: GameRoomDatabase? = null

        fun getDatabase(context: Context): GameRoomDatabase? {
            if (gameRoomDatabaseInstance != null) return gameRoomDatabaseInstance

            synchronized(GameRoomDatabase::class.java) {
                if (gameRoomDatabaseInstance == null) {
                    gameRoomDatabaseInstance = Room.databaseBuilder(
                        context.applicationContext,
                        GameRoomDatabase::class.java,
                        DATABASE_NAME
                    )
                        .build()
                }
            }
            return gameRoomDatabaseInstance
        }
    }
}