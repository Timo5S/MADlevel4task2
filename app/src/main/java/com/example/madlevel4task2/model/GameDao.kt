package com.example.madlevel4task2.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.madlevel4task2.database.Game

@Dao
interface GameDao {
    @Query("SELECT * FROM game_table")
    suspend fun getAllGames(): List<Game>

    @Query("SELECT count(*) FROM game_table WHERE matchResult = 0")
    suspend fun getWins(): Int

    @Query("SELECT count(*) FROM game_table WHERE matchResult = 1")
    suspend fun getLose(): Int

    @Query("SELECT count(*) FROM game_table WHERE matchResult = 2")
    suspend fun getDraws(): Int

    @Insert
    suspend fun insertGame(game: Game)

    @Query("DELETE FROM game_table")
    suspend fun deleteAllGamesFromHistory()
}