package com.example.madlevel4task2.model

import android.content.Context
import com.example.madlevel4task2.database.Game

class GameRepository(context: Context) {
    private val gameDao: GameDao?

    init {
        val database =
            GameRoomDatabase.getDatabase(
                context
            )
        gameDao = database?.gameDao()
    }

    suspend fun getAllGames(): List<Game> = gameDao?.getAllGames() ?: emptyList()
    suspend fun getWins(): Int? = gameDao?.getWins()
    suspend fun getLose(): Int? = gameDao?.getLose()
    suspend fun getDraws(): Int? = gameDao?.getDraws()
    suspend fun insertGame(game: Game) = gameDao?.insertGame(game)
    suspend fun deleteAllGamesFromHistory() = gameDao?.deleteAllGamesFromHistory()

}