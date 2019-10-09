package com.example.assignment42rockpaperscissors.database

import androidx.room.*
import com.example.assignment42rockpaperscissors.model.Game

@Dao
interface GameDao {

    @Query("SELECT * FROM game ORDER BY date DESC")
    suspend fun getAllGames(): List<Game>

    @Insert
    suspend fun insertGame(game: Game)

    @Query("DELETE FROM game")
    suspend fun deleteAllGames()

    @Query("SELECT COUNT() FROM game WHERE result = 'You win!'")
    suspend fun getWins(): Int

    @Query("SELECT COUNT() FROM game WHERE result = 'Draw'")
    suspend fun getDraws(): Int

    @Query("SELECT COUNT() FROM game WHERE result = 'Computer wins!'")
    suspend fun getLosses(): Int
}